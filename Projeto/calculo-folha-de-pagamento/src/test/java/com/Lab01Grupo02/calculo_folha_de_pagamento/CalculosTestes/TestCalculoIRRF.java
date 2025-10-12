package test.java.com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoIRRF;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCalculoIRRF {

    private final CalculoIRRF calculadora = new CalculoIRRF();
    private static final int ESCALA = 2;

    @Test
    void deveCalcularIRRFFaixaIsenta() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("2000.00"));
        BigDecimal inssCalculado = new BigDecimal("180.00");

        ItemFolha itemIRRF = calculadora.calcular(funcionario, inssCalculado);

        assertEquals(BigDecimal.ZERO.setScale(ESCALA), itemIRRF.getValor().abs());
        assertEquals("Desconto", itemIRRF.getTipo());
    }

    @Test
    void deveCalcularIRRFFaixa3ComDependente() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("3500.00"));
        BigDecimal inssCalculado = new BigDecimal("340.00");

        ItemFolha itemIRRF = calculadora.calcular(funcionario, inssCalculado);

        BigDecimal irrfEsperado = new BigDecimal("-90.76"); // Corrigido
        assertEquals(irrfEsperado, itemIRRF.getValor());
        assertEquals("Desconto", itemIRRF.getTipo());
    }

    @Test
    void deveCalcularIRRFFaixa5MultiplosDependentes() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("7507.49"));
        BigDecimal inssCalculado = new BigDecimal("877.24");

        ItemFolha itemIRRF = calculadora.calcular(funcionario, inssCalculado);

        BigDecimal irrfEsperado = new BigDecimal("-849.74"); // Corrigido
        assertEquals(irrfEsperado, itemIRRF.getValor());
        assertEquals("Desconto", itemIRRF.getTipo());
    }
}
