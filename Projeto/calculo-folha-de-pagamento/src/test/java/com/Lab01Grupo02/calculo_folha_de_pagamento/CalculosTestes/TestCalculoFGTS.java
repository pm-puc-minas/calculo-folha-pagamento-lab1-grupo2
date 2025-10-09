package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TestCalculoFGTS {

    private final CalculoFGTS calculadora = new CalculoFGTS();

    @Test
    void deveCalcularFgtsParaSalarioMedio() {
        // Entrada
        BigDecimal salario = new BigDecimal("3000.00");

        // Ação
        BigDecimal resultado = calculadora.calcularFGTS(salario);

        // Resultado esperado: 8% de 3000 = 240
        assertEquals(new BigDecimal("240.00"), resultado);
    }

    @Test
    void deveCalcularFgtsParaSalarioMinimo() {
        BigDecimal salario = new BigDecimal("1380.60");
        BigDecimal resultado = calculadora.calcularFGTS(salario);

        // 1380.60 * 0.08 = 110.448 ≈ 110.45
        assertEquals(new BigDecimal("110.45"), resultado);
    }

    @Test
    void deveCalcularFgtsParaSalarioAlto() {
        BigDecimal salario = new BigDecimal("7500.00");
        BigDecimal resultado = calculadora.calcularFGTS(salario);

        // 8% de 7500 = 600
        assertEquals(new BigDecimal("600.00"), resultado);
    }

    @Test
    void deveRetornarZeroQuandoSalarioForNulo() {
        BigDecimal resultado = calculadora.calcularFGTS(null);
        assertEquals(BigDecimal.ZERO, resultado);
    }
}
