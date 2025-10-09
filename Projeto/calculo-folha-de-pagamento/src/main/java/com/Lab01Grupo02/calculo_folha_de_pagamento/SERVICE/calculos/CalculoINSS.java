package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoINSS implements ICalculadora {

    private static final double TETO_INSS = 7507.49;

    // Tabela progressiva de 2023
    private static final double[][] FAIXAS = {
        {0.00, 1302.00, 0.075},      
        {1302.01, 2571.29, 0.09},    
        {2571.30, 3856.94, 0.12},    
        {3856.95, 7507.49, 0.14}    
    };

    @Override
    public BigDecimal calcular(BigDecimal salarioBruto) {
        double salario = salarioBruto.doubleValue();

       
        salario = Math.min(salario, TETO_INSS);

        double descontoTotal = 0.0;

    
        for (double[] faixa : FAIXAS) {
            double limiteInferior = faixa[0];
            double limiteSuperior = faixa[1];
            double aliquota = faixa[2];

            if (salario > limiteInferior) {
                // valor dentro da faixa
                double baseCalculo = Math.min(salario, limiteSuperior) - limiteInferior;
                descontoTotal += baseCalculo * aliquota;
            } else {
                break;
            }
        }

      
        return BigDecimal.valueOf(descontoTotal).setScale(2, RoundingMode.HALF_UP);
    }

  
    public BigDecimal calcularAliquotaEfetiva(BigDecimal salarioBruto) {
        BigDecimal desconto = calcular(salarioBruto);
        if (salarioBruto.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return desconto
                .divide(salarioBruto, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100)); // transforma em %
    }

    public static void main(String[] args) {
        CalculoINSS calculo = new CalculoINSS();
        BigDecimal salario = new BigDecimal("7507.49");

        BigDecimal desconto = calculo.calcular(salario);
        BigDecimal aliquotaEfetiva = calculo.calcularAliquotaEfetiva(salario);

        System.out.println("Salário Bruto: R$ " + salario);
        System.out.println("Desconto INSS: R$ " + desconto);
        System.out.println("Alíquota Efetiva: " + aliquotaEfetiva + "%");
    }
}