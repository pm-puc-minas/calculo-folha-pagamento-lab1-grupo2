package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoInsalubridade implements ICalculadora {

    private BigDecimal salarioMinimo;
    private BigDecimal grauRisco;
    private String descricao;

    public CalculoInsalubridade(BigDecimal salarioMinimo, BigDecimal grauRisco, String descricao) {
        this.salarioMinimo = salarioMinimo;
        this.grauRisco = grauRisco;
        this.descricao = descricao;
    }

    @Override
    public FolhaDePagamento calcularFolha(BigDecimal salarioBruto) {
        FolhaDePagamento folha = new FolhaDePagamento();
        folha.setSalarioBruto(salarioBruto);

        BigDecimal adicional = salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);

        ItemFolha item = new ItemFolha();
        item.setFolha(folha);
        item.setDescricao(descricao);
        item.setTipo("PROVENTO");
        item.setValor(adicional);

        folha.getItens().add(item);

        folha.setTotalProvento(adicional);
        folha.setSalarioLiquido(adicional); // se não houver outros descontos

        return folha;
    }
}
