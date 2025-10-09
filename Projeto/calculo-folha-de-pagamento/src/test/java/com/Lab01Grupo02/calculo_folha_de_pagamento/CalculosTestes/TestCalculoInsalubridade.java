package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoInsalubridade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCalculoInsalubridade {

    @Test
    @DisplayName("N1 - Grau de risco Baixo (10%)")
    void calcularInsalubridade_Baixo() {
        BigDecimal salarioMinimo = new BigDecimal("1380.60");
        BigDecimal grauRisco = new BigDecimal("0.10"); // 10%
        CalculoInsalubridade calculo = new CalculoInsalubridade(salarioMinimo, grauRisco, "Baixo");

        BigDecimal adicional = salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, new BigDecimal("138.06").compareTo(adicional));
    }

    @Test
    @DisplayName("N2 - Grau de risco Medio (20%)")
    void calcularInsalubridade_Medio() {
        BigDecimal salarioMinimo = new BigDecimal("1380.60");
        BigDecimal grauRisco = new BigDecimal("0.20"); // 20%
        CalculoInsalubridade calculo = new CalculoInsalubridade(salarioMinimo, grauRisco, "Medio");

        BigDecimal adicional = salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, new BigDecimal("276.12").compareTo(adicional));
    }

    @Test
    @DisplayName("N3 - Grau de risco Alto (40%)")
    void calcularInsalubridade_Alto() {
        BigDecimal salarioMinimo = new BigDecimal("1380.60");
        BigDecimal grauRisco = new BigDecimal("0.40"); // 40%
        CalculoInsalubridade calculo = new CalculoInsalubridade(salarioMinimo, grauRisco, "Alto");

        BigDecimal adicional = salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, new BigDecimal("552.24").compareTo(adicional));
    }
}
