package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.FolhaPagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    // Agora injetamos o Repository, não o DataSource
    private final FolhaPagamentoRepository folhaPagamentoRepository;

    @Autowired
    public RelatorioService(FolhaPagamentoRepository folhaPagamentoRepository) {
        this.folhaPagamentoRepository = folhaPagamentoRepository;
    }

    public List<FolhaDePagamento> buscarTodasAsFolhas() {
        System.out.println("Buscando todas as folhas usando o Repository do Spring Data JPA...");
        // Todo aquele código JDBC foi substituído por esta única linha!
        return folhaPagamentoRepository.findAll();
    }
}