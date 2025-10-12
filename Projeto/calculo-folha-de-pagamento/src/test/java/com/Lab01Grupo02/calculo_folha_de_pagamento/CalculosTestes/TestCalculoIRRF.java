package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoIRRF;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCalculoIRRF {

    private final CalculoIRRF calculadora = new CalculoIRRF();
    private static final int ESCALA = 2;

    @Test
    void deveCalcularIRRFFaixaIsenta() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("2000.00"));
        funcionario.setNumeroDependentes(0);

        List<ItemFolha> itens = calculadora.calcularFolhaCompleta(funcionario);
        ItemFolha itemIRRF = itens.get(0);

        assertEquals(BigDecimal.ZERO.setScale(ESCALA), itemIRRF.getValor().abs());
        assertEquals("Desconto", itemIRRF.getTipo());
    }

    @Test
    void deveCalcularIRRFFaixa3ComDependente() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("3500.00"));
        funcionario.setNumeroDependentes(1);

        List<ItemFolha> itens = calculadora.calcularFolhaCompleta(funcionario);
        ItemFolha itemIRRF = itens.get(0);

        BigDecimal irrfEsperado = new BigDecimal("-89.76").setScale(ESCALA, RoundingMode.HALF_UP);
        assertEquals(irrfEsperado, itemIRRF.getValor());
        assertEquals("Desconto", itemIRRF.getTipo());
    }

    @Test
    void deveCalcularIRRFFaixa5MultiplasDeducoes() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("7507.49"));
        funcionario.setNumeroDependentes(2);

        List<ItemFolha> itens = calculadora.calcularFolhaCompleta(funcionario);
        ItemFolha itemIRRF = itens.get(0);

        BigDecimal irrfEsperado = new BigDecimal("-849.68").setScale(ESCALA, RoundingMode.HALF_UP);
        assertEquals(irrfEsperado, itemIRRF.getValor());
        assertEquals("Desconto", itemIRRF.getTipo());
    }
}
