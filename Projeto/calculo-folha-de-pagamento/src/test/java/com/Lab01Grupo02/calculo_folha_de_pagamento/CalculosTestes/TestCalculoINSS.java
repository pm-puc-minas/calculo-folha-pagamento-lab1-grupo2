package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoINSS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TestCalculoINSS {

    private CalculoINSS calculoINSS;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        calculoINSS = new CalculoINSS();

        funcionario = new Funcionario();
        funcionario.setMatricula(1);
        funcionario.setNome("Maria Silva");
        funcionario.setCpf("987.654.321-00");
        funcionario.setDataNascimento(LocalDate.of(1985, 5, 15));
        funcionario.setDataAdmissao(LocalDate.of(2018, 3, 1));
        funcionario.setCargo("Analista");
    }

    @Test
    @DisplayName("N1 - Cálculo progressivo para salário na segunda faixa (R$ 2000,00)")
    void testN1_CalculoProgressivoSegundaFaixa() {
        funcionario.setSalarioBruto(new BigDecimal("2000.00"));

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("160.47");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("INSS", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$160,47 (1ª Faixa: R$97,65 + 2ª Faixa: R$62,82), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N2 - Cálculo para salário no teto máximo (R$ 7507,49)")
    void testN2_CalculoSalarioNoTetoMaximo() {
        funcionario.setSalarioBruto(new BigDecimal("7507.49"));

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("877.24");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("INSS", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$877,24 (1ª: R$97,65 + 2ª: R$114,24 + 3ª: R$154,28 + 4ª: R$511,07), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N3 - Cálculo limitado ao teto para salário acima do máximo (R$ 8000,00)")
    void testN3_CalculoLimitadoAoTeto() {
        funcionario.setSalarioBruto(new BigDecimal("8000.00"));

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        BigDecimal valorEsperado = new BigDecimal("877.24");

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals("INSS", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$877,24 (limitado ao teto de R$7507,49), mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("Detalhamento do cálculo N1 - Verificar cálculo por faixas")
    void testDetalhamentoCalculoN1() {
        BigDecimal salario = new BigDecimal("2000.00");

        BigDecimal faixa1 = new BigDecimal("1302.00").multiply(new BigDecimal("0.075"));
        BigDecimal faixa2 = new BigDecimal("2000.00").subtract(new BigDecimal("1302.00"))
                .multiply(new BigDecimal("0.09"));
        BigDecimal total = faixa1.add(faixa2).setScale(2, RoundingMode.HALF_UP);

        assertEquals(new BigDecimal("97.65"), faixa1.setScale(2, RoundingMode.HALF_UP),
                "1ª Faixa (R$1302,00 × 7,5%) deve ser R$97,65");
        assertEquals(new BigDecimal("62.82"), faixa2.setScale(2, RoundingMode.HALF_UP),
                "2ª Faixa (R$698,00 × 9%) deve ser R$62,82");
        assertEquals(new BigDecimal("160.47"), total,
                "Total deve ser R$160,47");
    }

    @Test
    @DisplayName("Detalhamento do cálculo N2 - Verificar todas as faixas no teto")
    void testDetalhamentoCalculoN2() {
        BigDecimal faixa1 = new BigDecimal("1302.00").multiply(new BigDecimal("0.075"));
        BigDecimal faixa2 = new BigDecimal("2571.29").subtract(new BigDecimal("1302.00"))
                .multiply(new BigDecimal("0.09"));
        BigDecimal faixa3 = new BigDecimal("3856.94").subtract(new BigDecimal("2571.29"))
                .multiply(new BigDecimal("0.12"));
        BigDecimal faixa4 = new BigDecimal("7507.49").subtract(new BigDecimal("3856.94"))
                .multiply(new BigDecimal("0.14"));

        assertEquals(new BigDecimal("97.65"), faixa1.setScale(2, RoundingMode.HALF_UP),
                "1ª Faixa deve ser R$97,65");
        assertEquals(new BigDecimal("114.24"), faixa2.setScale(2, RoundingMode.HALF_UP),
                "2ª Faixa deve ser R$114,24");
        assertEquals(new BigDecimal("154.28"), faixa3.setScale(2, RoundingMode.HALF_UP),
                "3ª Faixa deve ser R$154,28");
        assertEquals(new BigDecimal("511.08"), faixa4.setScale(2, RoundingMode.HALF_UP),
                "4ª Faixa deve ser R$511,08");

        BigDecimal totalManual = faixa1.add(faixa2).add(faixa3).add(faixa4);

        assertTrue(totalManual.setScale(2, RoundingMode.HALF_UP).compareTo(new BigDecimal("877.24")) >= 0,
                "Total calculado manualmente: " + totalManual.setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("Funcionário com salário nulo deve retornar valor zero")
    void funcionarioComSalarioNuloDeveRetornarZero() {
        funcionario.setSalarioBruto(null);

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals("INSS", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("Funcionário nulo deve retornar valor zero")
    void funcionarioNuloDeveRetornarZero() {
        ItemFolha resultado = calculoINSS.calcular(null);

        assertNotNull(resultado);
        assertEquals("INSS", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }

    @Test
    @DisplayName("Salário zero deve retornar desconto zero")
    void salarioZeroDeveRetornarDescontoZero() {
        funcionario.setSalarioBruto(BigDecimal.ZERO);

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        assertNotNull(resultado);
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()),
                "Salário zero deve resultar em desconto zero");
    }

    @Test
    @DisplayName("Validar que salários acima do teto sempre resultam no mesmo desconto")
    void salariosAcimaDoTetoDevemTerMesmoDesconto() {
        BigDecimal[] salariosAcimaDoTeto = {
                new BigDecimal("8000.00"),
                new BigDecimal("10000.00"),
                new BigDecimal("15000.00"),
                new BigDecimal("20000.00")
        };

        BigDecimal descontoEsperado = new BigDecimal("877.24");

        for (BigDecimal salario : salariosAcimaDoTeto) {
            funcionario.setSalarioBruto(salario);
            ItemFolha resultado = calculoINSS.calcular(funcionario);

            assertEquals(0, descontoEsperado.compareTo(resultado.getValor()),
                    "Salário de R$" + salario + " deve ter desconto limitado a R$877,24");
        }
    }

    @Test
    @DisplayName("Alíquota efetiva para salário no teto deve ser aproximadamente 11,68%")
    void aliquotaEfetivaParaSalarioNoTeto() {
        BigDecimal salario = new BigDecimal("7507.49");

        BigDecimal aliquota = calculoINSS.calcularAliquotaEfetiva(salario);
        BigDecimal aliquotaArredondada = aliquota.setScale(2, RoundingMode.HALF_UP);

        BigDecimal aliquotaEsperada = new BigDecimal("11.68");

        assertEquals(0, aliquotaEsperada.compareTo(aliquotaArredondada),
                "Alíquota efetiva esperada: 11,68%, mas foi: " + aliquotaArredondada + "%");
    }

    @Test
    @DisplayName("Tipo do item deve sempre ser Desconto")
    void tipoItemDeveSerDesconto() {
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        assertEquals("Desconto", resultado.getTipo(),
                "O tipo do item INSS deve sempre ser 'Desconto'");
    }

    @Test
    @DisplayName("Descrição do item deve ser INSS")
    void descricaoItemDeveSerINSS() {
        funcionario.setSalarioBruto(new BigDecimal("2500.00"));

        ItemFolha resultado = calculoINSS.calcular(funcionario);

        assertEquals("INSS", resultado.getDesc(),
                "A descrição do item deve ser 'INSS'");
    }
}