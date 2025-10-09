package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoInsalubridade;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;

public class TestCalculoInsalubridade {

    private final CalculoInsalubridade calculadora = new CalculoInsalubridade();

    @Test
    @DisplayName("N1 - Grau de risco Baixo (10%)")
    void testInsalubridadeBaixo() {
        FolhaDePagamento folha = calculadora.calcularFolha(1380.60, 0.10, "Baixo");
        assertEquals(new BigDecimal("138.06"), folha.getTotalProvento());
    }

    @Test
    @DisplayName("N2 - Grau de risco Médio (20%)")
    void testInsalubridadeMedio() {
        FolhaDePagamento folha = calculadora.calcularFolha(1380.60, 0.20, "Médio");
        assertEquals(new BigDecimal("276.12"), folha.getTotalProvento());
    }

    @Test
    @DisplayName("N3 - Grau de risco Alto (40%)")
    void testInsalubridadeAlto() {
        FolhaDePagamento folha = calculadora.calcularFolha(1380.60, 0.40, "Alto");
        assertEquals(new BigDecimal("552.24"), folha.getTotalProvento());
    }
}
