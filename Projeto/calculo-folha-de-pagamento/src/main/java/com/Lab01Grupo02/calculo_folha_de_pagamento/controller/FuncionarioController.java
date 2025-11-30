package com.Lab01Grupo02.calculo_folha_de_pagamento.controller;

// Importe o DTO que criamos
import com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto.FuncionarioRequestDTO;
// Importe a Factory que criamos
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioFactory;

import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.DuplicateCpfException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.InvalidDataException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.ResourceNotFoundException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // --- MÉTODOS DE LEITURA (GET) CONTINUAM IGUAIS ---

    @GetMapping
    public ResponseEntity<List<Funcionario>> buscarTodos() {
        List<Funcionario> funcionarios = funcionarioService.buscarTodos();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Funcionario> buscarPorMatricula(@PathVariable Integer matricula) {
        Funcionario funcionario = funcionarioService.buscarPorMatricula(matricula);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/cpf")
    public ResponseEntity<Funcionario> buscarPorCpf(@RequestParam("valor") String cpf) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping("/nome")
    public ResponseEntity<List<Funcionario>> buscarPorNome(@RequestParam("termo") String nome) {
        List<Funcionario> funcionarios = funcionarioService.buscarPorNome(nome);
        return ResponseEntity.ok(funcionarios);
    }

    // --- AQUI ESTÁ A MUDANÇA PRINCIPAL (FACTORY) ---

    /**
     * Rota para criar um novo funcionário.
     * MUDANÇA: Recebe um DTO (dados brutos) e usa a Factory para criar o objeto.
     */
    @PostMapping
    public ResponseEntity<Funcionario> criarFuncionario(@RequestBody FuncionarioRequestDTO dadosRecebidos) {

        // 1. USO DO PADRÃO FACTORY:
        // O Controller não cria mais o objeto manualmente. Ele delega para a fábrica.
        // A fábrica cuida de converter String para BigDecimal, tratar datas, definir padrões, etc.
        Funcionario novoFuncionario = FuncionarioFactory.criarDoDTO(dadosRecebidos);

        // 2. Persistência normal no Service
        Funcionario funcionarioSalvo = funcionarioService.salvarFuncionario(novoFuncionario);

        return ResponseEntity.status(201).body(funcionarioSalvo);
    }

    // --- MÉTODOS DE ATUALIZAÇÃO (PUT/PATCH) ---

    @PatchMapping("/{matricula}/carga-horaria")
    public ResponseEntity<Funcionario> atualizarCargaHoraria(@PathVariable Integer matricula, @RequestBody Map<String, Integer> body) {
        Integer novaCargaHoraria = body.get("cargaHoraria");
        if(novaCargaHoraria == null){
            return ResponseEntity.badRequest().build();
        }
        Funcionario funcionarioAtualizado = funcionarioService.atualizarCargaHoraria(matricula, novaCargaHoraria);
        return ResponseEntity.ok(funcionarioAtualizado);
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Funcionario> atualizarFuncionario(
            @PathVariable Integer matricula,
            @RequestBody Funcionario funcionario) {
        // Nota: Para atualização, você pode manter recebendo 'Funcionario' direto
        // ou criar uma lógica específica se necessário. Para o exercício do Factory,
        // focar na criação (POST) já é suficiente.
        Funcionario funcionarioAtualizado = funcionarioService.atualizarFuncionario(matricula, funcionario);
        return ResponseEntity.ok(funcionarioAtualizado);
    }
}