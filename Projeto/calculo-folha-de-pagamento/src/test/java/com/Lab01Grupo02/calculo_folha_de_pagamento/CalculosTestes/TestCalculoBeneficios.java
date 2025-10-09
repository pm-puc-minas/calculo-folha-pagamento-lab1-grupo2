package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class TestCalculoBeneficio {

    private final CalculoBeneficio calculadora = new CalculoBeneficio();

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

        BigDecimal esperado = new BigDecimal("624.00").setScale(2, RoundingMode.HALF_UP);

        BigDecimal resultado = calculadora.calcularVA(diasUteis, valorDiario);
        assertEquals(esperado, resultado);
    }

    @Test
    void deveCalcularVATotalComValoresDecimaisEArredondarCorretamente() {
        int diasUteis = 22;
        double valorDiario = 33.3333; 

        BigDecimal esperado = new BigDecimal("733.33").setScale(2, RoundingMode.HALF_UP);

        BigDecimal resultado = calculadora.calcularVA(diasUteis, valorDiario);
        assertEquals(esperado, resultado);
    }

    @Test
    void deveRetornarZeroQuandoNaoHouverDiasUteis() {
        int diasUteis = 0;
        double valorDiario = 50.00;

        BigDecimal esperado = new BigDecimal("0.00").setScale(2, RoundingMode.HALF_UP);

        BigDecimal resultado = calculadora.calcularVA(diasUteis, valorDiario);
        assertEquals(esperado, resultado);
    }
}