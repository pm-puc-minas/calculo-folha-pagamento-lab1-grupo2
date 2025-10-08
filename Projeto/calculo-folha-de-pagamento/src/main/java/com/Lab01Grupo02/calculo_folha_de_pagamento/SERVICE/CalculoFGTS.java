package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE;


import java.math.BigDecimal;

public class CalculoFGTS {
    public BigDecimal calcularFGTS(double salarioBruto) {
        return BigDecimal.valueOf(salarioBruto * 0.08);
    }
}
