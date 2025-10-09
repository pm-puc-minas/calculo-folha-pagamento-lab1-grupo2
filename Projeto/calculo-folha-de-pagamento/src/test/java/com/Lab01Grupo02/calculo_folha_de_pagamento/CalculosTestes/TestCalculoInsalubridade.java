package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;

class TestCalculoInsalubridade {

    private final CalculoInsalubridade calculadora = new CalculoInsalubridade();

    @Test
    void deveCalcularInsalubridadeParaGrauBaixo() {
        // Entrada
        double salarioMinimo = 1380.60;
        double grauRisco = 0.10; // 10%
        String descricao = "Insalubridade - Grau Baixo";

        // Ação
        FolhaDePagamento folha = calculadora.calcularFolha(salarioMinimo, grauRisco, descricao);

        // Resultado esperado: 1380.60 * 0.10 = 138.06
        assertEquals(new BigDecimal("138.06"), folha.getTotalProvento());
        assertEquals(descricao, folha.getDescricao());
    }

    @Test
    void deveCalcularInsalubridadeParaGrauMedio() {
        double salarioMinimo = 1380.60;
        double grauRisco = 0.20; // 20%
        String descricao = "Insalubridade - Grau Médio";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioMinimo, grauRisco, descricao);

        // 1380.60 * 0.20 = 276.12
        assertEquals(new BigDecimal("276.12"), folha.getTotalProvento());
    }

    @Test
    void deveCalcularInsalubridadeParaGrauAlto() {
        double salarioMinimo = 1380.60;
        double grauRisco = 0.40; // 40%
        String descricao = "Insalubridade - Grau Alto";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioMinimo, grauRisco, descricao);

        // 1380.60 * 0.40 = 552.24
        assertEquals(new BigDecimal("552.24"), folha.getTotalProvento());
    }
}
