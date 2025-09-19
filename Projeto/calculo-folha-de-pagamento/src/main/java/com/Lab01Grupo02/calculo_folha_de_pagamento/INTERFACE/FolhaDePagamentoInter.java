package com.Lab01Grupo02.calculo_folha_de_pagamento.INTERFACE;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolhaDePagamentoInter extends JpaRepository<FolhaDePagamento, Integer> {
    /*
        O Jpa já definiu todos os metodos para selecao de dados no Banco de Dados
     */
}
