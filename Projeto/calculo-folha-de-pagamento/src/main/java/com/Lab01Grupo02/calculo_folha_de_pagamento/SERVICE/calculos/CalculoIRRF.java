package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;


import java.math.BigDecimal;

public class CalculoIRRF {
    public BigDecimal calcularDesconto(double salarioBruto, double valorINSS, int numDependentes) {
        double base = salarioBruto - valorINSS - (numDependentes * 189.59);
        return BigDecimal.valueOf(base * 0.15); // Exemplo simplificado
    }
}
