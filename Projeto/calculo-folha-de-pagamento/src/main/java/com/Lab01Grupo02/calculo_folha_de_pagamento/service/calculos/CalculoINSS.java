package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoINSS implements ICalculadora {

    private static final double TETO_INSS = 7507.49;

    private enum TipoINSS {
        PROGRESSIVO {
            @Override
            public BigDecimal calcular(BigDecimal salarioBruto) {

                if (salarioBruto == null || salarioBruto.compareTo(BigDecimal.ZERO) <= 0) {
                    return BigDecimal.ZERO;
                }

                double salario = salarioBruto.doubleValue();

                // Aplica o teto máximo do INSS
                salario = Math.min(salario, TETO_INSS);
                double desconto = 0.0;

                // Cálculo progressivo com as faixas de 2023
                if (salario > 3856.94) {
                    desconto += (salario - 3856.94) * 0.14;
                    salario = 3856.94;
                }
                if (salario > 2571.29) {
                    desconto += (salario - 2571.29) * 0.12;
                    salario = 2571.29;
                }
                if (salario > 1302.00) {
                    desconto += (salario - 1302.00) * 0.09;
                    salario = 1302.00;
                }

                desconto += salario * 0.075;

                return BigDecimal.valueOf(desconto).setScale(2, RoundingMode.HALF_UP);
            }
        };

        public abstract BigDecimal calcular(BigDecimal salarioBruto);
    }

    @Override
    public BigDecimal calcular(BigDecimal salarioBruto) {
        return TipoINSS.PROGRESSIVO.calcular(salarioBruto);
    }

    @Override
    public BigDecimal calcularAliquotaEfetiva(BigDecimal salarioBruto) {

        if (salarioBruto == null || salarioBruto.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal desconto = calcular(salarioBruto);

        return desconto
                .divide(salarioBruto, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
