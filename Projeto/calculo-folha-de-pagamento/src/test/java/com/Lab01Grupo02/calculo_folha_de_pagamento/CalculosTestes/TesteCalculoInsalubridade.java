package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.Calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoInsalubridade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class CalculoInsalubridadeTests {

    @Test
    @DisplayName("Deve calcular corretamente o adicional de insalubridade e criar ItemFolha")
    void calcularFolha_DeveRetornarFolhaComItemCorreto() {
        // Arrange
        BigDecimal salarioMinimo = new BigDecimal("1300.00");
        BigDecimal grauRisco = new BigDecimal("0.20"); // 20% de insalubridade
        String descricao = "Insalubridade Grau 2";

        CalculoInsalubridade calculadora = new CalculoInsalubridade(salarioMinimo, grauRisco, descricao);
        BigDecimal salarioBruto = new BigDecimal("5000.00");

        // Act
        FolhaDePagamento folha = calculadora.calcularFolha(salarioBruto);

        // Assert
        assertNotNull(folha);
        assertEquals(salarioBruto, folha.getSalarioBruto());

        // Verifica totais
        BigDecimal adicionalEsperado = salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, adicionalEsperado.compareTo(folha.getTotalProvento()));
        assertEquals(0, adicionalEsperado.compareTo(folha.getSalarioLiquido()));

        // Verifica o ItemFolha
        assertNotNull(folha.getItens());
        assertEquals(1, folha.getItens().size());

        ItemFolha item = folha.getItens().get(0);
        assertEquals(descricao, item.getDescricao());
        assertEquals("PROVENTO", item.getTipo());
        assertEquals(0, adicionalEsperado.compareTo(item.getValor()));
        assertEquals(folha, item.getFolha());
    }
}
