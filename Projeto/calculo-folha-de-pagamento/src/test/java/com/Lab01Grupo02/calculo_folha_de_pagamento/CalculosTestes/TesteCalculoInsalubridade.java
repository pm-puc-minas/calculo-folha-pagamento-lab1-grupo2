package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.Calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoInsalubridade;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class TesteCalculoInsalubridade {

    @Test
    @DisplayName("Deve calcular corretamente o adicional de insalubridade")
    void calcularAdicional_DeveRetornarValorCorreto() {
        // Arrange
        BigDecimal salarioMinimo = new BigDecimal("1300.00");
        BigDecimal grauRisco = new BigDecimal("0.20"); // 20%
        String descricao = "Insalubridade Grau 2";

        CalculoInsalubridade calculadora = new CalculoInsalubridade(salarioMinimo, grauRisco, descricao);

        // Act
        BigDecimal adicional = calculadora.calcularAdicional(salarioMinimo, grauRisco);

        // Assert
        BigDecimal esperado = salarioMinimo.multiply(grauRisco).setScale(2, RoundingMode.HALF_UP);
        assertEquals(0, esperado.compareTo(adicional));
    }

    @Test
    @DisplayName("Deve retornar zero quando salário mínimo ou grau de risco forem nulos")
    void calcularAdicional_Nulo_DeveRetornarZero() {
        // Arrange
        CalculoInsalubridade calculadora = new CalculoInsalubridade(null, null, "Teste");

        // Act
        BigDecimal adicional = calculadora.calcularAdicional(null, null);

        // Assert
        assertEquals(BigDecimal.ZERO, adicional);
    }
}
