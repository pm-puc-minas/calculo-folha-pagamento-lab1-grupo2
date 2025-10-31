package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoInsalubridade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
class TestCalculoInsalubridade {

    private CalculoInsalubridade calculoInsalubridade;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        calculoInsalubridade = new CalculoInsalubridade();

        funcionario = new Funcionario();
        funcionario.setMatricula(1);
        funcionario.setNome("João Silva");
        funcionario.setCpf("123.456.789-00");
        funcionario.setDataNascimento(LocalDate.of(1990, 1, 1));
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        funcionario.setDataAdmissao(LocalDate.of(2020, 1, 1));
        funcionario.setCargo("Operador");
        funcionario.setTemInsalubridade(true);
    }

    @Test
    @DisplayName("N1 - Cálculo Insalubridade Grau Baixo (10%)")
    void testN1_CalculoInsalubridadeGrauBaixo() {
        funcionario.setGrauInsalubridade("BAIXO");

        ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("138.06");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("Adicional de Insalubridade - Grau Mínimo (10%)", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$138,06, mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N2 - Cálculo Insalubridade Grau Médio (20%)")
    void testN2_CalculoInsalubridadeGrauMedio() {
        funcionario.setGrauInsalubridade("MEDIO");

        ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("276.12");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("Adicional de Insalubridade - Grau Médio (20%)", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$276,12, mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N3 - Cálculo Insalubridade Grau Alto (40%)")
    void testN3_CalculoInsalubridadeGrauAlto() {
        funcionario.setGrauInsalubridade("ALTO");

        ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("552.24");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("Adicional de Insalubridade - Grau Máximo (40%)", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$552,24, mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("Funcionário sem insalubridade deve retornar valor zero")
    void testFuncionarioSemInsalubridade() {
        funcionario.setTemInsalubridade(false);
        funcionario.setGrauInsalubridade("BAIXO");

        ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals("Adicional de Insalubridade", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("Funcionário nulo deve retornar valor zero")
    void testFuncionarioNulo() {
        ItemFolha resultado = calculoInsalubridade.calcular(null);

        assertNotNull(resultado);
        assertEquals("Adicional de Insalubridade", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("Grau de insalubridade nulo deve retornar valor zero")
    void testGrauInsalubridadeNulo() {
        funcionario.setTemInsalubridade(true);
        funcionario.setGrauInsalubridade(null);

        ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals("Adicional de Insalubridade", resultado.getDesc());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("Variações do grau mínimo devem calcular corretamente")
    void testVariacoesGrauMinimo() {
        String[] variacoes = {"MINIMO", "MÍNIMO", "BAIXO"};
        BigDecimal valorEsperado = new BigDecimal("138.06");

        for (String grau : variacoes) {
            funcionario.setGrauInsalubridade(grau);
            ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

            assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                    "Falha para o grau: " + grau + ". Valor obtido: " + resultado.getValor());
        }
    }

    @Test
    @DisplayName("Variações do grau médio devem calcular corretamente")
    void testVariacoesGrauMedio() {
        String[] variacoes = {"MEDIO", "MÉDIO"};
        BigDecimal valorEsperado = new BigDecimal("276.12");

        for (String grau : variacoes) {
            funcionario.setGrauInsalubridade(grau);
            ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

            assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                    "Falha para o grau: " + grau + ". Valor obtido: " + resultado.getValor());
        }
    }

    @Test
    @DisplayName("Variações do grau máximo devem calcular corretamente")
    void testVariacoesGrauMaximo() {
        String[] variacoes = {"MAXIMO", "MÁXIMO", "ALTO"};
        BigDecimal valorEsperado = new BigDecimal("552.24");

        for (String grau : variacoes) {
            funcionario.setGrauInsalubridade(grau);
            ItemFolha resultado = calculoInsalubridade.calcular(funcionario);

            assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                    "Falha para o grau: " + grau + ". Valor obtido: " + resultado.getValor());
        }
    }
}
 */