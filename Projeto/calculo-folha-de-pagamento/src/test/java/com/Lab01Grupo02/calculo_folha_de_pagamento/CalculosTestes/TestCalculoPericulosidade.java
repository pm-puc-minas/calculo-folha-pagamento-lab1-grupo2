package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoPericulosidade;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TestCalculoPericulosidade {

    private final CalculoPericulosidade calculadora = new CalculoPericulosidade() {
        @Override
        public FolhaDePagamento calcularFolha(BigDecimal salarioBruto) {
            return null;
        }
    };

    @Test
    void deveCalcularPericulosidadeFuncionarioApto() {
        // N1: Funcionário com direito a periculosidade
        double salario = 2500.00;
        boolean apto = true;
        String descricao = "Periculosidade - Funcionário Apto";

        // Se apto, 30% do salário
        FolhaDePagamento folha = calculadora.calcularFolha(salario, apto ? 1 : 0, descricao);

        assertEquals(new BigDecimal("750.00"), folha.getTotalProvento());
        // assertEquals(descricao, folha.getDescricao());
    }

    @Test
    void deveCalcularPericulosidadeFuncionarioNaoApto() {
        // N2: Funcionário sem direito a periculosidade
        double salario = 3000.00;
        boolean apto = false;
        String descricao = "Periculosidade - Funcionário Não Apto";

        // Se não apto, valor deve ser zero
        FolhaDePagamento folha = calculadora.calcularFolha(salario, apto ? 1 : 0, descricao);

        assertEquals(new BigDecimal("0.00"), folha.getTotalProvento());
        // assertEquals(descricao, folha.getDescricao());
    }

    @Test
    void deveCalcularPericulosidadeSalarioMinimo() {
        // N3: Periculosidade com salário mínimo
        double salario = 1380.60;
        boolean apto = true;
        String descricao = "Periculosidade - Salário Mínimo";

        FolhaDePagamento folha = calculadora.calcularFolha(salario, apto ? 1 : 0, descricao);

       
        assertEquals(new BigDecimal("414.18"), folha.getTotalProvento());
       // assertEquals(descricao, folha.getDescricao());
    }
}
