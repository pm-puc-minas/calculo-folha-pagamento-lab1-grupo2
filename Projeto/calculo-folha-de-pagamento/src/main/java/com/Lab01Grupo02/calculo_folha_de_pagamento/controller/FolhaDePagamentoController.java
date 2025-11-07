package com.Lab01Grupo02.calculo_folha_de_pagamento.controller;

import com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto.GerarFolhaRequest;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.ICalculadora;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.FolhaPagamentoRepository;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.ItemFolhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controller para expor a API REST de geração da Folha de Pagamento.
 * Define a rota /api/folhapagamento.
 */
@RestController
@RequestMapping("/api/folhapagamento")
public class FolhaDePagamentoController {

    // Injeção dos serviços e repositórios necessários
    private final FuncionarioService funcionarioService;
    private final ICalculadora calculadoraService;
    private final FolhaPagamentoRepository folhaPagamentoRepository;
    private final ItemFolhaRepository itemFolhaRepository;

    @Autowired
    public FolhaDePagamentoController(
            FuncionarioService funcionarioService,
            ICalculadora calculadoraService,
            FolhaPagamentoRepository folhaPagamentoRepository,
            ItemFolhaRepository itemFolhaRepository
    ) {
        this.funcionarioService = funcionarioService;
        this.calculadoraService = calculadoraService;
        this.folhaPagamentoRepository = folhaPagamentoRepository;
        this.itemFolhaRepository = itemFolhaRepository;
    }

    /**
     * Rota para GERAR uma nova folha de pagamento para um funcionário.
     * URL: POST /api/folhapagamento
     *
     * @param request O corpo (body) JSON da requisição (ver GerarFolhaRequest.java)
     * @return A FolhaDePagamento completa que foi salva no banco de dados.
     */
    @PostMapping
    @Transactional // Garante que tudo (salvar folha e itens) seja uma única transação
    public ResponseEntity<FolhaDePagamento> gerarOuAtualizarFolhaPagamento(@Valid @RequestBody GerarFolhaRequest request) {

        // 1. Buscar o funcionário
        // CORRIGIDO: request.getMatricula() -> request.matricula()
        Funcionario funcionario = funcionarioService.buscarPorMatricula(request.matricula());

        // 2. LÓGICA UPSERT: Tentar encontrar a folha ou criar uma nova
        // CORRIGIDO: request.getMatricula() -> request.matricula()
        // CORRIGIDO: request.getMesReferencia() -> request.mesReferencia()
        FolhaDePagamento folha = folhaPagamentoRepository
                .findByMatriculaAndMesReferencia(request.matricula(), request.mesReferencia())
                .orElse(new FolhaDePagamento());

        // 3. Limpar itens antigos (se for uma atualização)
        if (folha.getId_Folha() > 0 && folha.getItens() != null) {
            // APENAS ISSO:
            // Ao limpar a lista, o 'orphanRemoval=true' que você configurou
            // automaticamente instrui o Hibernate a deletar esses itens do banco.
            folha.getItens().clear();
        }

        // 4. Preencher/Atualizar os dados da folha
        folha.setMatricula(funcionario.getIdPessoa());

        // CORRIGIDO: request.getMesReferencia() -> request.mesReferencia()
        folha.setMesReferencia(request.mesReferencia());
        // CORRIGIDO: request.getDiasFalta() -> request.diasFalta()
        folha.setDiasFalta(request.diasFalta());
        folha.setSalarioBruto(funcionario.getSalarioBruto());

        // 5. Chamar o serviço de cálculo
        // CORRIGIDO: request.getDiasFalta() -> request.diasFalta()
        List<ItemFolha> itensCalculados = calculadoraService.calcularFolhaCompleta(funcionario, request.diasFalta());

        // 6. Calcular Totais
        BigDecimal totalProventos = BigDecimal.ZERO;
        BigDecimal totalDescontos = BigDecimal.ZERO;

        for (ItemFolha item : itensCalculados) {
            item.setFolhaDePagamento(folha);

            if (item.getTipo().equals("PROVENTO")) {
                totalProventos = totalProventos.add(item.getValor());
            } else if (item.getTipo().equals("DESCONTO")) {
                totalDescontos = totalDescontos.add(item.getValor());
            }
        }

        // 7. Definir os totais e o salário líquido
        folha.setTotalProvento(totalProventos);
        folha.setTotalDesconto(totalDescontos);
        folha.setSalarioLiquido(totalProventos.subtract(totalDescontos));
        folha.setItens(itensCalculados);

        // 8. Salvar (INSERT ou UPDATE)
        FolhaDePagamento folhaSalva = folhaPagamentoRepository.save(folha);

        return ResponseEntity.ok(folhaSalva);
    }
}
