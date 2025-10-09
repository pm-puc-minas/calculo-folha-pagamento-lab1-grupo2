<<<<<<< HEAD
package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
=======
package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoInsalubridade;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;
>>>>>>> d319a4d32ffab291dbbad3a12a872799dc2b92db

class TestCalculoInsalubridade {

    private final CalculoInsalubridade calculadora = new CalculoInsalubridade();

    @Test
<<<<<<< HEAD
    void deveCalcularInsalubridadeParaGrauBaixo() {
        // Entrada
        double salarioMinimo = 1380.60;
        double grauRisco = 0.10; // 10%
        String descricao = "Insalubridade - Grau Baixo";

        // Ação
        FolhaDePagamento folha = calculadora.calcularFolha(salarioMinimo, grauRisco, descricao);

        // Resultado esperado: 1380.60 * 0.10 = 138.06
        assertEquals(new BigDecimal("138.06"), folha.getTotalProvento());
        assertEquals(descricao, folha.getDescricao());
    }

    @Test
    void deveCalcularInsalubridadeParaGrauMedio() {
        double salarioMinimo = 1380.60;
        double grauRisco = 0.20; // 20%
        String descricao = "Insalubridade - Grau Médio";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioMinimo, grauRisco, descricao);

        // 1380.60 * 0.20 = 276.12
        assertEquals(new BigDecimal("276.12"), folha.getTotalProvento());
    }

    @Test
    void deveCalcularInsalubridadeParaGrauAlto() {
        double salarioMinimo = 1380.60;
        double grauRisco = 0.40; // 40%
        String descricao = "Insalubridade - Grau Alto";

        FolhaDePagamento folha = calculadora.calcularFolha(salarioMinimo, grauRisco, descricao);

        // 1380.60 * 0.40 = 552.24
        assertEquals(new BigDecimal("552.24"), folha.getTotalProvento());
=======
    void deveCalcularInsalubridadeGrauBaixo() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"), new BigDecimal("0.10"));
        assertEquals(new BigDecimal("138.06"), resultado);
    }

    @Test
    void deveCalcularInsalubridadeGrauMedio() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"), new BigDecimal("0.20"));
        assertEquals(new BigDecimal("276.12"), resultado);
    }

    @Test
    void deveCalcularInsalubridadeGrauAlto() {
        BigDecimal resultado = calculadora.calcular(new BigDecimal("1380.60"), new BigDecimal("0.40"));
        assertEquals(new BigDecimal("552.24"), resultado);
    }

    @Test
    void deveRetornarZeroSeEntradasForemNulas() {
        BigDecimal resultado = calculadora.calcular(null, null);
        assertEquals(BigDecimal.ZERO, resultado);
>>>>>>> d319a4d32ffab291dbbad3a12a872799dc2b92db
    }
}
