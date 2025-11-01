package com.Lab01Grupo02.calculo_folha_de_pagamento.controller;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller para expor a API REST de Funcionários.
 * Esta classe define os URLs (rotas) e lida com as requisições HTTP,
 * convertendo automaticamente os retornos para JSON.
 */
@RestController // Fundamental: Diz ao Spring que esta classe retorna JSON
@RequestMapping("/api/funcionarios") // Prefixo do URL para todas as rotas neste controller
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    /**
     * Rota para buscar um funcionário pela MATRÍCULA (ID).
     * Exemplo de URL: GET /api/funcionarios/123
     */
    @GetMapping("/{matricula}")
    public ResponseEntity<Funcionario> buscarPorMatricula(@PathVariable Integer matricula) {
        // O serviço irá retornar o Funcionario ou lançar ResourceNotFoundException
        Funcionario funcionario = funcionarioService.buscarPorMatricula(matricula);

        // ResponseEntity.ok() retorna um status HTTP 200
        // O Spring/Jackson converte 'funcionario' para JSON automaticamente
        return ResponseEntity.ok(funcionario);
    }

    /**
     * Rota para buscar um funcionário pelo CPF.
     * Exemplo de URL: GET /api/funcionarios/cpf?valor=123.456.789-00
     */
    @GetMapping("/cpf")
    public ResponseEntity<Funcionario> buscarPorCpf(@RequestParam("valor") String cpf) {
        Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
        return ResponseEntity.ok(funcionario);
    }

    /**
     * Rota para buscar funcionários por NOME.
     * Exemplo de URL: GET /api/funcionarios/nome?termo=Joao
     */
    @GetMapping("/nome")
    public ResponseEntity<List<Funcionario>> buscarPorNome(@RequestParam("termo") String nome) {
        List<Funcionario> funcionarios = funcionarioService.buscarPorNome(nome);
        // Retorna a lista (que pode estar vazia), convertida para um array JSON
        return ResponseEntity.ok(funcionarios);
    }

    /**
     * NOVA ROTA: Atualizar a carga horárioa de um funcionario.
     */
    @PatchMapping("/{matricula}/carga-horaria")
    public ResponseEntity<Funcionario> atualizarCargaHoraria(@PathVariable Integer matricula, @RequestBody Map<String, Integer> body) {
        Integer novaCargaHoraria = body.get("cargaHoraria");

        if(novaCargaHoraria == null){
            // Se o Json estiver errado, retorna um erro
            return ResponseEntity.badRequest().build();
        }
        Funcionario funcionarioAtualizado = funcionarioService.atualizarCargaHoraria(matricula, novaCargaHoraria);
        return ResponseEntity.ok(funcionarioAtualizado);
    }
}