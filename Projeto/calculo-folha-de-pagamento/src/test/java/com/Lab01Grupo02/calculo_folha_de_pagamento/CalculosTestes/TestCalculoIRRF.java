package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoIRRF;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TestCalculoIRRF {

    private final CalculoIRRF calculadora = new CalculoIRRF();

    @Test
    void deveCalcularIRRFFaixaIsenta() {
        double salarioBruto = 2000.00;
        double dependentes = 0;
        String descricao = "IRRF - Faixa Isenta";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioBruto, dependentes, descricao);

        assertEquals(new BigDecimal("0.00"), folha.getTotalProvento());
        assertEquals(new BigDecimal("2000.00"), folha.getSalarioLiquido().add(folha.getTotalProvento()));
        //assertEquals(descricao, folha.getDescricao());
    }

    @Test
    void deveCalcularIRRFFaixa3ComDependente() {
        double salarioBruto = 3500.00;
        double dependentes = 1;
        String descricao = "IRRF - Faixa 3 com Dependente";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioBruto, dependentes, descricao);

        assertEquals(new BigDecimal("99.52"), folha.getTotalProvento());
        assertEquals(new BigDecimal("3400.48"), folha.getSalarioLiquido());
        //assertEquals(descricao, folha.getDescricao());
    }

    @Test
    void deveCalcularIRRFFaixa5MultiplasDeducoes() {
        double salarioBruto = 7507.49;
        double dependentes = 2;
        String descricao = "IRRF - Faixa 5 múltiplas deduções";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioBruto, dependentes, descricao);

        assertEquals(new BigDecimal("849.68"), folha.getTotalProvento());
        assertEquals(new BigDecimal("6657.81"), folha.getSalarioLiquido());
        //assertEquals(descricao, folha.getDescricao());
    }
}
