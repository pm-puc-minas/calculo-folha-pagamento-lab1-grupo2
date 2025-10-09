package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;

public class CalculoInsalubridade {

    public FolhaDePagamento calcularFolha(double salarioBruto, double percentual, String grau) {
        BigDecimal salario = BigDecimal.valueOf(salarioBruto);
        BigDecimal totalProvento = salario.multiply(BigDecimal.valueOf(percentual))
                .setScale(2, RoundingMode.HALF_UP);

        FolhaDePagamento folha = new FolhaDePagamento();
        folha.setTotalProvento(totalProvento);
        return folha;
    }
}
