package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.Calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoFGTS;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class TesteCalculoFGTS {

    @Test
    @DisplayName("Deve calcular corretamente 8% de FGTS sobre o salário bruto")
    void calcularFGTS_DeveRetornarValorCorreto() {
        // Arrange
        CalculoFGTS calculadora = new CalculoFGTS();
        BigDecimal salarioBruto = new BigDecimal("5000.00");

        // Act
        BigDecimal fgts = calculadora.calcularFGTS(salarioBruto);

        // Assert
        BigDecimal esperado = new BigDecimal("400.00").setScale(2, RoundingMode.HALF_UP); // 8% de 5000
        assertEquals(0, esperado.compareTo(fgts));
    }

    @Test
    @DisplayName("Deve retornar zero se o salário bruto for nulo")
    void calcularFGTS_SalarioNulo_DeveRetornarZero() {
        // Arrange
        CalculoFGTS calculadora = new CalculoFGTS();

        // Act
        BigDecimal fgts = calculadora.calcularFGTS(null);

        // Assert
        assertEquals(BigDecimal.ZERO, fgts);
    }

    @Test
    @DisplayName("Deve calcular corretamente valores decimais")
    void calcularFGTS_SalarioDecimal_DeveRetornarValorArredondado() {
        // Arrange
        CalculoFGTS calculadora = new CalculoFGTS();
        BigDecimal salarioBruto = new BigDecimal("1234.56");

        // Act
        BigDecimal fgts = calculadora.calcularFGTS(salarioBruto);

        // Assert
        BigDecimal esperado = new BigDecimal("98.77").setScale(2, RoundingMode.HALF_UP); // 1234.56 * 0.08 = 98.7648 -> 98.77
        assertEquals(0, esperado.compareTo(fgts));
    }
}
