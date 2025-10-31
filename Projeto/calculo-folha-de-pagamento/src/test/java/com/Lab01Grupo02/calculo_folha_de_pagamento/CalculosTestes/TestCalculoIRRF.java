package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Dependente;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoIRRF;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
class TestCalculoIRRF {

    private CalculoIRRF calculoIRRF;
    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        calculoIRRF = new CalculoIRRF();

        funcionario = new Funcionario();
        funcionario.setMatricula(1);
        funcionario.setNome("João Silva");
        funcionario.setCpf("123.456.789-00");
        funcionario.setDataNascimento(LocalDate.of(1985, 5, 15));
        funcionario.setDataAdmissao(LocalDate.of(2018, 3, 1));
        funcionario.setCargo("Analista");
    }

    @Test
    @DisplayName("N1 - Salário na faixa de isenção (até R$ 1.903,98)")
    void testN1_SalarioNaFaixaIsencao() {
        funcionario.setSalarioBruto(new BigDecimal("2000.00"));
        funcionario.setDependentes(new ArrayList<>());

        BigDecimal descontoINSS = new BigDecimal("160.47");

        ItemFolha resultado = calculoIRRF.calcular(funcionario, descontoINSS);

        assertNotNull(resultado);
        assertEquals("IRRF", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()),
                "Salário: R$2000,00 - INSS: R$160,47 = R$1839,53 (isento). Valor esperado: R$0,00.");
    }

    @Test
    @DisplayName("N2 - Salário na terceira faixa (15%) com 1 dependente")
    void testN2_SalarioTerceiraFaixaComDependente() {
        funcionario.setSalarioBruto(new BigDecimal("3500.00"));

        List<Dependente> dependentes = new ArrayList<>();
        Dependente dependente = new Dependente();
        dependente.setNome("Filho");
        dependentes.add(dependente);
        funcionario.setDependentes(dependentes);

        BigDecimal descontoINSS = new BigDecimal("281.62");

        ItemFolha resultado = calculoIRRF.calcular(funcionario, descontoINSS);

        BigDecimal valorEsperado = new BigDecimal("99.52");

        assertNotNull(resultado);
        assertEquals("IRRF", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$99,52, mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("N3 - Salário na quinta faixa (27,5%) com 2 dependentes")
    void testN3_SalarioQuintaFaixaComDoisDependentes() {
        funcionario.setSalarioBruto(new BigDecimal("7507.49"));

        List<Dependente> dependentes = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Dependente d = new Dependente();
            d.setNome("Dependente " + (i + 1));
            dependentes.add(d);
        }
        funcionario.setDependentes(dependentes);

        BigDecimal descontoINSS = new BigDecimal("877.24");

        ItemFolha resultado = calculoIRRF.calcular(funcionario, descontoINSS);

        BigDecimal valorEsperado = new BigDecimal("849.68");

        assertNotNull(resultado);
        assertEquals("IRRF", resultado.getDesc());
        assertEquals("Desconto", resultado.getTipo());
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()),
                "Valor esperado: R$849,68, mas foi: R$" + resultado.getValor());
    }

    @Test
    @DisplayName("Funcionário com salário nulo deve retornar valor zero")
    void testFuncionarioComSalarioNulo() {
        funcionario.setSalarioBruto(null);
        ItemFolha resultado = calculoIRRF.calcular(funcionario);
        assertNotNull(resultado);
        assertEquals(BigDecimal.ZERO.setScale(2), resultado.getValor());
    }

    @Test
    @DisplayName("Funcionário nulo deve retornar valor zero")
    void testFuncionarioNulo() {
        ItemFolha resultado = calculoIRRF.calcular(null);
        assertNotNull(resultado);
        assertEquals(BigDecimal.ZERO.setScale(2), resultado.getValor());
    }

    @Test
    @DisplayName("Dedução por dependente reduz a base de cálculo corretamente")
    void testDeducaoPorDependente() {
        funcionario.setSalarioBruto(new BigDecimal("5000.00"));

        funcionario.setDependentes(new ArrayList<>());
        ItemFolha resultadoSemDep = calculoIRRF.calcular(funcionario);

        List<Dependente> comDependentes = new ArrayList<>();
        comDependentes.add(new Dependente());
        funcionario.setDependentes(comDependentes);
        ItemFolha resultadoComDep = calculoIRRF.calcular(funcionario);

        assertTrue(resultadoSemDep.getValor().compareTo(resultadoComDep.getValor()) > 0);
    }

    @Test
    @DisplayName("Base de cálculo negativa ou zero deve resultar em IRRF zero")
    void testBaseCalculoNegativa() {
        funcionario.setSalarioBruto(new BigDecimal("500.00"));

        List<Dependente> dependentes = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            dependentes.add(new Dependente());
        }
        funcionario.setDependentes(dependentes);

        ItemFolha resultado = calculoIRRF.calcular(funcionario);

        assertEquals(0, BigDecimal.ZERO.compareTo(resultado.getValor()));
    }
}
*/