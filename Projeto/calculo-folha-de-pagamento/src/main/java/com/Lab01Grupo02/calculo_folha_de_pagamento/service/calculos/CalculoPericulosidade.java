package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoPericulosidade implements ICalculoFolha { 

    private static final BigDecimal ALIQUOTA_PERICULOSIDADE = new BigDecimal("0.30");
    private static final int ESCALA = 2;

    @Override
    public ItemFolha calcular(Funcionario funcionario) {
        // Alterado 'isTemPericulosidade' para 'isPossuiPericulosidade'
        if (funcionario == null || funcionario.getSalarioBruto() == null || !funcionario.isPossuiPericulosidade()) {
            return criarItemVazio();
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal valorAdicional = salarioBruto.multiply(ALIQUOTA_PERICULOSIDADE).setScale(ESCALA, RoundingMode.HALF_UP);

        ItemFolha item = new ItemFolha();
        item.setDesc("Adicional de Periculosidade (30%)");
        item.setTipo("PROVENTO");
        item.setValor(valorAdicional);
        return item;
    }

    private ItemFolha criarItemVazio() {
        ItemFolha itemVazio = new ItemFolha();
        itemVazio.setDesc("Adicional de Periculosidade (30%)");
        itemVazio.setTipo("PROVENTO");
        itemVazio.setValor(BigDecimal.ZERO.setScale(ESCALA));
        return itemVazio;
    }
}
