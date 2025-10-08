package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoFGTS {

    private static final BigDecimal ALIQUOTA = new BigDecimal("0.08");

    public BigDecimal calcularFGTS(BigDecimal salarioBruto) {
        if (salarioBruto == null) {
            return BigDecimal.ZERO;
        }
        // Multiplica e arredonda para 2 casas decimais
        return salarioBruto.multiply(ALIQUOTA).setScale(2, RoundingMode.HALF_UP);
    }
}
