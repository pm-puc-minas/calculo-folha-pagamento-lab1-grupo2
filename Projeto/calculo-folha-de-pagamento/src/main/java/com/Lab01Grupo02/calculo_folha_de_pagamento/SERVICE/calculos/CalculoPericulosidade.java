package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;


import java.math.BigDecimal;

public class CalculoPericulosidade {
    public BigDecimal calcularAdicional(double salarioBruto) {
        return BigDecimal.valueOf(salarioBruto * 0.3); // 30%
    }
}

