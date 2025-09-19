package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE;

import com.Lab01Grupo02.calculo_folha_de_pagamento.INTERFACE.FolhaDePagamentoInter;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RelatorioService {

    private final FolhaDePagamentoInter relatorio;

    public RelatorioService(FolhaDePagamentoInter relatorio) {
        this.relatorio = relatorio;
    }

    /**
     * Buscar os dados de uma da folha de pagamento
     *
     * @param matricula A Matricula do funcionario a ser buscada
     * @Return O objeto FolhaDePagamento com todos os dados
     * */
    public FolhaDePagamento retornarFolhaPagamento(Integer matricula) {
        // Chama o metodo findById() da FolhaDePagamentoInter usando JPA
        Optional<FolhaDePagamento> retorna = relatorio.findById(matricula);

        // Verifica se existe dados
        if(retorna.isPresent()) {
            // Se sim, ele retorna os dados
            return retorna.get();
        }else{
            // Se nao existir, lança uma exceção indicando que nao foi os dados de tal matricula
            throw new RuntimeException("Folha de pagamento da matricula: " + matricula + " não foi encontrada.");
        }
    }



}
