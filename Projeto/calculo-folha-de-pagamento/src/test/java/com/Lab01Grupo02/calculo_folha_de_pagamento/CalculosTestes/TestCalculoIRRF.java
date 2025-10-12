package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

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

        ItemFolha itemIRRF = calculadora.calcularFolhaCompleta(funcionario).get(0);

        assertEquals(BigDecimal.ZERO.setScale(ESCALA), itemIRRF.getValor().abs());
        assertEquals("Desconto", itemIRRF.getTipo());
    }

    @Test
    void deveCalcularIRRFFaixa3ComDependente() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("3500.00"));

     
        funcionario.setDependentes(java.util.Collections.singletonList(new com.Lab01Grupo02.calculo_folha_de_pagamento.model.Dependente()));

        BigDecimal irrfEsperado = new BigDecimal("-89.76").setScale(ESCALA);

        ItemFolha itemIRRF = calculadora.calcularFolhaCompleta(funcionario).get(0);

        assertEquals(irrfEsperado, itemIRRF.getValor());
        assertEquals("Desconto", itemIRRF.getTipo());
    }

    @Test
    void deveCalcularIRRFFaixa5MultiplosDependentes() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("7507.49"));

  
        funcionario.setDependentes(java.util.Arrays.asList(
                new com.Lab01Grupo02.calculo_folha_de_pagamento.model.Dependente(),
                new com.Lab01Grupo02.calculo_folha_de_pagamento.model.Dependente()
        ));

        BigDecimal irrfEsperado = new BigDecimal("-849.68").setScale(ESCALA);

        ItemFolha itemIRRF = calculadora.calcularFolhaCompleta(funcionario).get(0);

        assertEquals(irrfEsperado, itemIRRF.getValor());
        assertEquals("Desconto", itemIRRF.getTipo());
    }
}
