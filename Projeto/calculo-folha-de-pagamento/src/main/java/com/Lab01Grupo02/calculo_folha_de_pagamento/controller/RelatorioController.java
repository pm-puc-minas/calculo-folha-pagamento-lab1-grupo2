package com.Lab01Grupo02.calculo_folha_de_pagamento.controller;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.RelatorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RelatorioController {

    private final RelatorioService relatorioService;

    @Autowired
    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/listarTodasAsFolhas")
    public ResponseEntity<List<FolhaDePagamento>> listarTodasAsFolhas() {
        List<FolhaDePagamento> folhas = relatorioService.buscarTodasAsFolhas();

        if (folhas != null && !folhas.isEmpty()) {
            return ResponseEntity.ok(folhas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}