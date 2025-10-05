package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;

public class CalculoInsalubridade {
    public BigDecimal calcularAdicional(double salarioMinimo, double grauRisco, String descricao) {
        return BigDecimal.valueOf(salarioMinimo * grauRisco); // Exemplo
    }
}
