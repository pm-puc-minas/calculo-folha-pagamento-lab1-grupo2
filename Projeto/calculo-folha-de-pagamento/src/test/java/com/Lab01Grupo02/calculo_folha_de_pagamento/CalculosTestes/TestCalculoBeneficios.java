package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoBeneficio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestCalculoBeneficios {

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        funcionario = new Funcionario();
        funcionario.setMatricula(1);
        funcionario.setNome("Carlos Santos");
        funcionario.setCpf("123.456.789-00");
        funcionario.setDataNascimento(LocalDate.of(1988, 3, 10));
        funcionario.setDataAdmissao(LocalDate.of(2019, 6, 1));
        funcionario.setCargo("Desenvolvedor");
    }

    @Test
    @DisplayName("N1 - Vale alimentação para mês padrão (22 dias × R$24,00)")
    void testN1_ValeAlimentacaoMesPadrao() {
        Integer diasUteis = 22;
        BigDecimal valorDiario = new BigDecimal("24.00");
        CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(diasUteis, valorDiario);

        ItemFolha resultado = calculo.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("528.00");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("Vale Alimentação", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$528,00 (22 dias × R$24,00), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N2 - Vale transporte menor que 6% do salário")
    void testN2_ValeTransporteMenorQue6Porcento() {
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        BigDecimal valorVT = new BigDecimal("150.00");
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(valorVT);

        ItemFolha resultado = calculo.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("150.00");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("Vale Transporte", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$150,00 (valor integral, pois é menor que 6% de R$3000,00), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N3 - Vale transporte maior que 6% do salário")
    void testN3_ValeTransporteMaiorQue6Porcento() {
        funcionario.setSalarioBruto(new BigDecimal("2000.00"));
        BigDecimal valorVT = new BigDecimal("300.00");
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(valorVT);

        ItemFolha resultado = calculo.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("120.00");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("Vale Transporte", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$120,00 (6% de R$2000,00), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("VT - Desconto exato de 6% quando valor VT é igual ao limite")
    void testValeTransporte_ValorIgualAoLimite() {
        funcionario.setSalarioBruto(new BigDecimal("5000.00"));
        BigDecimal valorVT = new BigDecimal("300.00");
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(valorVT);

        ItemFolha resultado = calculo.calcular(funcionario);

        BigDecimal limiteEsperado = new BigDecimal("300.00");

        assertNotNull(resultado);
        assertEquals(0, limiteEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$300,00 (6% de R$5000,00), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("VA - Cálculo correto com valor fracionado")
    void testValeAlimentacao_ValorFracionado() {
        Integer diasUteis = 20;
        BigDecimal valorDiario = new BigDecimal("30.50");
        CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(diasUteis, valorDiario);

        ItemFolha resultado = calculo.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("610.00");

        assertNotNull(resultado);
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$610,00 (20 dias × R$30,50), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("VT - Valor zero quando valorVT é nulo")
    void testValeTransporte_ValorNulo() {
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(null);

        ItemFolha resultado = calculo.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals("Vale Transporte", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("VT - Valor zero quando valorVT é zero")
    void testValeTransporte_ValorZero() {
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(BigDecimal.ZERO);

        ItemFolha resultado = calculo.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("VA - Valor zero quando dias úteis é zero")
    void testValeAlimentacao_DiasUteisZero() {
        CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(0, new BigDecimal("25.00"));

        ItemFolha resultado = calculo.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals("Vale Alimentação", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("VA - Valor zero quando valor diário é nulo")
    void testValeAlimentacao_ValorDiarioNulo() {
        CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(22, null);

        ItemFolha resultado = calculo.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("VT - Funcionário nulo deve retornar valor zero")
    void testValeTransporte_FuncionarioNulo() {
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(new BigDecimal("150.00"));

        ItemFolha resultado = calculo.calcular(null);

        assertNotNull(resultado);
        assertEquals("Vale Transporte", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("VA - Funcionário nulo não afeta cálculo do vale alimentação")
    void testValeAlimentacao_FuncionarioNulo() {
        CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(22, new BigDecimal("25.00"));

        ItemFolha resultado = calculo.calcular(null);

        assertNotNull(resultado);
        assertEquals("Vale Alimentação", resultado.getDesc());
        assertEquals("Provento", resultado.getTipo());
        assertEquals(0, new BigDecimal("550.00").compareTo(resultado.getValor()),
                "VA não depende do funcionário, apenas dos dias úteis e valor diário");
    }

    @Test
    @DisplayName("VT - Cálculo com salário alto e VT baixo")
    void testValeTransporte_SalarioAltoVTBaixo() {
        funcionario.setSalarioBruto(new BigDecimal("10000.00"));
        BigDecimal valorVT = new BigDecimal("100.00");
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(valorVT);

        ItemFolha resultado = calculo.calcular(funcionario);

        assertEquals(0, new BigDecimal("100.00").compareTo(resultado.getValor()),
                "VT baixo não atinge o limite de 6%");
    }

    @Test
    @DisplayName("VT - Validação do limite de 6% com diferentes salários")
    void testValeTransporte_ValidacaoLimite6Porcento() {
        BigDecimal[][] casos = {
                {new BigDecimal("1000.00"), new BigDecimal("100.00"), new BigDecimal("60.00")},
                {new BigDecimal("2500.00"), new BigDecimal("200.00"), new BigDecimal("150.00")},
                {new BigDecimal("4000.00"), new BigDecimal("250.00"), new BigDecimal("240.00")}
        };

        for (BigDecimal[] caso : casos) {
            funcionario.setSalarioBruto(caso[0]);
            CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(caso[1]);
            ItemFolha resultado = calculo.calcular(funcionario);

            assertEquals(0, caso[2].compareTo(resultado.getValor()),
                    "Para salário " + caso[0] + " e VT " + caso[1] +
                            ", esperado: " + caso[2] + ", mas foi: " + resultado.getValor());
        }
    }

    @Test
    @DisplayName("VT - Tipo do item deve sempre ser Desconto")
    void testValeTransporte_TipoDesconto() {
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        CalculoBeneficio calculo = CalculoBeneficio.valeTransporte(new BigDecimal("150.00"));

        ItemFolha resultado = calculo.calcular(funcionario);

        assertEquals("Desconto", resultado.getTipo(),
                "Vale Transporte deve sempre ser um desconto");
    }

    @Test
    @DisplayName("VA - Tipo do item deve sempre ser Provento")
    void testValeAlimentacao_TipoProvento() {
        CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(22, new BigDecimal("25.00"));

        ItemFolha resultado = calculo.calcular(funcionario);

        assertEquals("Provento", resultado.getTipo(),
                "Vale Alimentação deve sempre ser um provento");
    }

    @Test
    @DisplayName("VA - Diferentes combinações de dias e valores")
    void testValeAlimentacao_DiferentesCombinacoes() {
        Object[][] casos = {
                {22, new BigDecimal("24.00"), new BigDecimal("528.00")},
                {20, new BigDecimal("30.00"), new BigDecimal("600.00")},
                {15, new BigDecimal("20.00"), new BigDecimal("300.00")},
                {25, new BigDecimal("28.50"), new BigDecimal("712.50")}
        };

        for (Object[] caso : casos) {
            CalculoBeneficio calculo = CalculoBeneficio.valeAlimentacao(
                    (Integer) caso[0],
                    (BigDecimal) caso[1]
            );
            ItemFolha resultado = calculo.calcular(funcionario);

            assertEquals(0, ((BigDecimal) caso[2]).compareTo(resultado.getValor()),
                    "Para " + caso[0] + " dias × " + caso[1] +
                            ", esperado: " + caso[2] + ", mas foi: " + resultado.getValor());
        }
    }
}