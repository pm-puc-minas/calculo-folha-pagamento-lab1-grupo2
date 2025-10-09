package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoFGTS;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TestCalculoFGTS {

    private final CalculoFGTS calculadora = new CalculoFGTS();

    @Test
    void deveCalcularFGTSSalarioMedio() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("3000.00"));
        assertEquals(new BigDecimal("240.00"), resultado);
    }

    @Test
    void deveCalcularFGTSSalarioMinimo() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"));
        assertEquals(new BigDecimal("110.45"), resultado);
    }

    @Test
    void deveCalcularFGTSSalarioAlto() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("7500.00"));
        assertEquals(new BigDecimal("600.00"), resultado);
    }

    @Test
    void deveRetornarZeroSeSalarioForNulo() {
        BigDecimal resultado = calculadora.calcular(null);
        assertEquals(BigDecimal.ZERO, resultado);
    }
}
