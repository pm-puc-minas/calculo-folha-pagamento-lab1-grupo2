package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCalculoBeneficios {

    private final CalculoBeneficio calculadora = new CalculoBeneficio();
    // Constante para arredondamento, garantindo que a comparação usa a mesma regra da implementação
    private static final int ESCALA = 2; 

    @Test
    void deveDescontarValorVTQuandoForMenorQueSeisPorcento() {
        double salarioBruto = 3000.00;
        double valesEntregues = 150.00; 

        double descontoEsperado = 150.00;

        assertEquals(descontoEsperado, calculadora.calcularVT(salarioBruto, valesEntregues), 0.01);
    }

    @Test
    void deveDescontarSeisPorcentoQuandoVTForMaiorOuIgualASeisPorcento() {
        double salarioBruto = 2000.00;
        double valesEntregues = 300.00; 

        double descontoEsperado = 120.00;

        assertEquals(descontoEsperado, calculadora.calcularVT(salarioBruto, valesEntregues), 0.01);
    }

    @Test
    void deveDescontarSeisPorcentoQuandoVTForExatamenteSeisPorcento() {
        double salarioBruto = 5000.00;
        double valesEntregues = 300.00; 

        double descontoEsperado = 300.00;

        assertEquals(descontoEsperado, calculadora.calcularVT(salarioBruto, valesEntregues), 0.01);
    }

    @Test
    void deveCalcularVATotalCorretamenteConformeExemplo() {
        int diasUteis = 26;
        double valorDiario = 24.00;

        BigDecimal resultado = calculadora.calcularVA(diasUteis, valorDiario);
        
        // CORREÇÃO: Usando setScale para garantir a comparação exata de escala
        assertEquals(new BigDecimal("624.00").setScale(ESCALA, RoundingMode.HALF_UP), resultado);
    }

    @Test
    void deveCalcularVATotalComValoresDecimaisEArredondarCorretamente() {
        int diasUteis = 22;
        double valorDiario = 33.3333; 
        
        BigDecimal resultado = calculadora.calcularVA(diasUteis, valorDiario);

        // CORREÇÃO: Usando setScale para garantir a comparação exata de escala
        assertEquals(new BigDecimal("733.33").setScale(ESCALA, RoundingMode.HALF_UP), resultado);
    }

    @Test
    void deveRetornarZeroQuandoNaoHouverDiasUteis() {
        int diasUteis = 0;
        double valorDiario = 50.00;
        
        BigDecimal resultado = calculadora.calcularVA(diasUteis, valorDiario);

        // Uso do setScale no BigDecimal.ZERO
        assertEquals(BigDecimal.ZERO.setScale(ESCALA, RoundingMode.HALF_UP), resultado);
    }
}