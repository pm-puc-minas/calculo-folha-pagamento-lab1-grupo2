package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoInsalubridade;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class CalculoInsalubridadeTest {

    private final CalculoInsalubridade calculadora = new CalculoInsalubridade();

    @Test
    void deveCalcularInsalubridadeGrauBaixo() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"), new BigDecimal("0.10"));
        assertEquals(new BigDecimal("138.06"), resultado);
    }

    @Test
    void deveCalcularInsalubridadeGrauMedio() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"), new BigDecimal("0.20"));
        assertEquals(new BigDecimal("276.12"), resultado);
    }

    @Test
    void deveCalcularInsalubridadeGrauAlto() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"), new BigDecimal("0.40"));
        assertEquals(new BigDecimal("552.24"), resultado);
    }

    @Test
    void deveRetornarZeroSeEntradasForemNulas() {
        BigDecimal resultado = calculadora.calcular(null, null);
        assertEquals(BigDecimal.ZERO, resultado);
    }
}
