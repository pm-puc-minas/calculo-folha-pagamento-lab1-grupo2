package test.java.com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoInsalubridade;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;

public class TestCalculoInsalubridade {

    private final BigDecimal salarioMinimo = new BigDecimal("1380.60");

    @Test
    @DisplayName("N1 - Grau de risco Baixo (10%)")
    void testInsalubridadeBaixo() {
        CalculoInsalubridade calculadora = new CalculoInsalubridade(0.10, "Baixo");
        ItemFolha item = calculadora.calcularInsalubridade(salarioMinimo);

        assertEquals(new BigDecimal("138.06"), item.getValor(), "Valor do adicional incorreto");
        assertEquals("Baixo", item.getDesc(), "Descrição incorreta");
        assertEquals("Provento", item.getTipo(), "Tipo incorreto");
    }

    @Test
    @DisplayName("N2 - Grau de risco Médio (20%)")
    void testInsalubridadeMedio() {
        CalculoInsalubridade calculadora = new CalculoInsalubridade(0.20, "Médio");
        ItemFolha item = calculadora.calcularInsalubridade(salarioMinimo);

        assertEquals(new BigDecimal("276.12"), item.getValor(), "Valor do adicional incorreto");
        assertEquals("Médio", item.getDesc(), "Descrição incorreta");
        assertEquals("Provento", item.getTipo(), "Tipo incorreto");
    }

    @Test
    @DisplayName("N3 - Grau de risco Alto (40%)")
    void testInsalubridadeAlto() {
        CalculoInsalubridade calculadora = new CalculoInsalubridade(0.40, "Alto");
        ItemFolha item = calculadora.calcularInsalubridade(salarioMinimo);

        assertEquals(new BigDecimal("552.24"), item.getValor(), "Valor do adicional incorreto");
        assertEquals("Alto", item.getDesc(), "Descrição incorreta");
        assertEquals("Provento", item.getTipo(), "Tipo incorreto");
    }
}
