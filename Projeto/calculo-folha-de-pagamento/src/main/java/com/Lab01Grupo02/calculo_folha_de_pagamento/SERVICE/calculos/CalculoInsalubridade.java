package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;

public class CalculoInsalubridade implements ICalculadora {

    private String grau; // Baixo, Médio, Alto

    public CalculoInsalubridade(String grau) {
        this.grau = grau;
    }

    @Override
    public FolhaDePagamento calcularFolha(BigDecimal salarioBruto) {
        if (salarioBruto == null) {
            salarioBruto = BigDecimal.ZERO;
        }

        double percentual = 0.0;
        switch (grau.toLowerCase()) {
            case "baixo":
                percentual = 0.10;
                break;
            case "médio":
            case "medio":
                percentual = 0.20;
                break;
            case "alto":
                percentual = 0.40;
                break;
            default:
                percentual = 0.0; // caso inválido
        }

        BigDecimal totalProvento = salarioBruto.multiply(BigDecimal.valueOf(percentual))
                .setScale(2, RoundingMode.HALF_UP);

        FolhaDePagamento folha = new FolhaDePagamento();
        folha.setTotalProvento(totalProvento);
        return folha;
    }
}
