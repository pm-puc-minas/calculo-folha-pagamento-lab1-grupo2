package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoINSS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;


class TestCalculoINSS {

    private CalculoINSS calculadora;

    @BeforeEach
    void setUp() {
        calculadora = new CalculoINSS();
    }

    @Test
    @DisplayName("Deve calcular corretamente o INSS para salário até a primeira faixa")
    void deveCalcularINSSParaPrimeiraFaixa() {
        BigDecimal salario = new BigDecimal("1300.00");
        BigDecimal valorEsperado = new BigDecimal("97.50");

        BigDecimal desconto = calculadora.calcular(salario);

        
        assertNotNull(desconto, "O valor calculado não deve ser nulo.");
        assertEquals(0, valorEsperado.compareTo(desconto), "Desconto incorreto para a primeira faixa.");
    }

    @Test
    @DisplayName("Deve calcular corretamente o INSS para a segunda faixa")
    void deveCalcularINSSParaSegundaFaixa() {
        
        BigDecimal salario = new BigDecimal("2000.00");
        BigDecimal valorEsperado = new BigDecimal("160.47");

        BigDecimal desconto = calculadora.calcular(salario);

        
        assertEquals(0, valorEsperado.compareTo(desconto), "Desconto incorreto para a segunda faixa.");
    }

    @Test
    @DisplayName("Deve calcular corretamente o INSS para a terceira faixa")
    void deveCalcularINSSParaTerceiraFaixa() {

        BigDecimal salario = new BigDecimal("3500.00");
        BigDecimal valorEsperado = new BigDecimal("323.46");

        
        BigDecimal desconto = calculadora.calcular(salario);

     
        assertEquals(0, valorEsperado.compareTo(desconto), "Desconto incorreto para a terceira faixa.");
    }

    @Test
    @DisplayName("Deve calcular corretamente o INSS aplicando o teto na quarta faixa")
    void deveCalcularINSSComTeto() {
       
        BigDecimal salario = new BigDecimal("7507.49");
        BigDecimal valorEsperado = new BigDecimal("877.24");

        
        BigDecimal desconto = calculadora.calcular(salario);

     
        assertEquals(0, valorEsperado.compareTo(desconto), "Desconto incorreto no teto do INSS.");
    }

    @Test
    @DisplayName("Deve aplicar o teto corretamente para salário acima do limite máximo")
    void deveAplicarTetoParaSalarioAcimaDoLimite() {
       
        BigDecimal salario = new BigDecimal("9000.00");
        BigDecimal valorEsperado = new BigDecimal("877.24");

   
        BigDecimal desconto = calculadora.calcular(salario);

   
        assertEquals(0, valorEsperado.compareTo(desconto), "O teto do INSS não foi aplicado corretamente.");
    }

    @Test
    @DisplayName("Deve calcular a alíquota efetiva corretamente para o teto do INSS")
    void deveCalcularAliquotaEfetivaCorretamente() {
  
        BigDecimal salario = new BigDecimal("7507.49");
        BigDecimal valorEsperado = new BigDecimal("11.68"); // 11.68%

  
        BigDecimal aliquotaEfetiva = calculadora.calcularAliquotaEfetiva(salario)
                .setScale(2, RoundingMode.HALF_UP);

      
        assertEquals(0, valorEsperado.compareTo(aliquotaEfetiva),
                "A alíquota efetiva foi calculada incorretamente.");
    }
}

