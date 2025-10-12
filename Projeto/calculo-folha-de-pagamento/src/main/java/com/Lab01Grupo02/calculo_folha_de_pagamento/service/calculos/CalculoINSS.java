package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoINSS implements ICalculadora {

    private static final double TETO_INSS = 7507.49;

    // Enum representando o tipo de cálculo do INSS (aqui, apenas o progressivo)
    private enum TipoINSS {
        PROGRESSIVO {
            @Override
            public BigDecimal calcular(BigDecimal salarioBruto) {

                // Se o salário for nulo ou zero, retorna 0
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

                // Retorna o valor final arredondado
                return BigDecimal.valueOf(desconto).setScale(2, RoundingMode.HALF_UP);
            }
        };

        public abstract BigDecimal calcular(BigDecimal salarioBruto);
    }

    @Override
    public BigDecimal calcular(BigDecimal salarioBruto) {
        // Chama o cálculo progressivo de INSS
        return TipoINSS.PROGRESSIVO.calcular(salarioBruto);
    }

    @Override
    public BigDecimal calcularAliquotaEfetiva(BigDecimal salarioBruto) {

        // Evita divisão por zero
        if (salarioBruto == null || salarioBruto.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        // Calcula o valor de desconto total
        BigDecimal desconto = calcular(salarioBruto);

        // Calcula a alíquota efetiva em porcentagem
        return desconto
                .divide(salarioBruto, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }
}
