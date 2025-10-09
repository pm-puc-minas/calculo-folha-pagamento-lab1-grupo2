package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.ItemFolha;

public class CalculoInsalubridade {

    private double percentual; // Percentual do grau de risco: 0.1, 0.2, 0.4
    private String descricao;  // Descrição do grau de risco: Baixo, Médio, Alto

    public CalculoInsalubridade(double percentual, String descricao) {
        this.percentual = percentual;
        this.descricao = descricao;
    }

    public ItemFolha calcularInsalubridade(BigDecimal salarioMinimo) {
        if (salarioMinimo == null) {
            salarioMinimo = BigDecimal.ZERO;
        }

        // Cálculo do adicional: salário mínimo * percentual
        BigDecimal valor = salarioMinimo
                .multiply(BigDecimal.valueOf(percentual))
                .setScale(2, RoundingMode.HALF_UP);

        // Criação do item da folha de pagamento
        ItemFolha item = new ItemFolha();
        item.setDesc(descricao);
        item.setTipo("Provento");
        item.setValor(valor);

        return item;
    }
}
