package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoInsalubridade {

    public BigDecimal calcular(BigDecimal salarioMinimo, BigDecimal grauRisco) {
        if (salarioMinimo == null || grauRisco == null) {
            return BigDecimal.ZERO;
        }
        return salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);
    }
}
