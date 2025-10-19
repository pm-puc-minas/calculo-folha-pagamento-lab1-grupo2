package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoInsalubridade implements ICalculoFolha {

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1380.60");

    @Override
    public ItemFolha calcular(Funcionario funcionario) {

        if (funcionario == null || !funcionario.isTemInsalubridade()) {
            ItemFolha itemVazio = new ItemFolha();
            itemVazio.setDesc("Adicional de Insalubridade");
            itemVazio.setTipo("Provento");
            itemVazio.setValor(BigDecimal.ZERO);
            return itemVazio;
        }

        BigDecimal percentual = obterPercentual(funcionario.getGrauInsalubridade());
        String descricao = obterDescricao(funcionario.getGrauInsalubridade());

        BigDecimal valor = SALARIO_MINIMO
                .multiply(percentual)
                .setScale(2, RoundingMode.HALF_UP);

        ItemFolha item = new ItemFolha();
        item.setDesc(descricao);
        item.setTipo("Provento");
        item.setValor(valor);

        return item;
    }

    private BigDecimal obterPercentual(String grau) {
        if (grau == null) {
            return BigDecimal.ZERO;
        }

        switch (grau.toUpperCase()) {
            case "MINIMO":
            case "MÍNIMO":
            case "BAIXO":
                return new BigDecimal("0.10");
            case "MEDIO":
            case "MÉDIO":
                return new BigDecimal("0.20");
            case "MAXIMO":
            case "MÁXIMO":
            case "ALTO":
                return new BigDecimal("0.40");
            default:
                return BigDecimal.ZERO;
        }
    }

    private String obterDescricao(String grau) {
        if (grau == null) {
            return "Adicional de Insalubridade";
        }

        switch (grau.toUpperCase()) {
            case "MINIMO":
            case "MÍNIMO":
            case "BAIXO":
                return "Adicional de Insalubridade - Grau Mínimo (10%)";
            case "MEDIO":
            case "MÉDIO":
                return "Adicional de Insalubridade - Grau Médio (20%)";
            case "MAXIMO":
            case "MÁXIMO":
            case "ALTO":
                return "Adicional de Insalubridade - Grau Máximo (40%)";
            default:
                return "Adicional de Insalubridade";
        }
    }
}
