package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe responsável por realizar o cálculo do adicional de periculosidade.
 * 
 * O adicional corresponde a 30% do salário base do trabalhador, sem incluir
 * gratificações, prêmios ou participações nos lucros.
 * 
 * Autor: Luan Guimarães
 */
public class CalculoPericulosidade implements ICalculadora {

    private final BigDecimal percentual;
    private final String descricao;

    /**
     * Construtor do cálculo de periculosidade.
     * 
     * @param descricao descrição do cálculo que será exibida na folha (ex: "Adicional de Periculosidade")
     */
    public CalculoPericulosidade(String descricao) {
        this.percentual = new BigDecimal("0.30"); // 30%
        this.descricao = descricao;
    }

    /**
     * Calcula o adicional de periculosidade com base no salário bruto informado.
     * 
     * @param salarioBruto salário base do trabalhador
     * @return folha de pagamento atualizada com o adicional de periculosidade
     */
    @Override
    public FolhaDePagamento calcularFolha(BigDecimal salarioBruto) {
        FolhaDePagamento folha = new FolhaDePagamento();
        folha.setSalarioBruto(salarioBruto);

        // Cálculo de 30% do salário
        BigDecimal adicional = salarioBruto
                .multiply(percentual)
                .setScale(2, RoundingMode.HALF_UP);

        // Cria o item de folha referente à periculosidade
        ItemFolha item = new ItemFolha();
        item.setFolha(folha);
        item.setDescricao(descricao);
        item.setTipo("PROVENTO");
        item.setValor(adicional);

        // Adiciona o item e atualiza os totais
        folha.getItens().add(item);
        folha.setTotalProvento(adicional);
        folha.setSalarioLiquido(salarioBruto.add(adicional));

        return folha;
    }
}
