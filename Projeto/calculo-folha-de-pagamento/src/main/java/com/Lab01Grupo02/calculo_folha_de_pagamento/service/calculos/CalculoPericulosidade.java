package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoPericulosidade implements CalculoFolha { 

    private static final BigDecimal ALIQUOTA_PERICULOSIDADE = new BigDecimal("0.30");
    private static final int ESCALA = 2;

    @Override
    public ItemFolha calcular(Funcionario funcionario) {
        if (funcionario == null || funcionario.getSalarioBruto() == null || !funcionario.isTemPericulosidade()) {
            return criarItemVazio();
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal valorAdicional = salarioBruto.multiply(ALIQUOTA_PERICULOSIDADE).setScale(ESCALA, RoundingMode.HALF_UP);

        ItemFolha item = new ItemFolha();
        item.setDesc("Adicional de Periculosidade (30%)");
        item.setTipo("Provento");
        item.setValor(valorAdicional);
        return item;
    }

    private ItemFolha criarItemVazio() {
        ItemFolha itemVazio = new ItemFolha();
        itemVazio.setDesc("Adicional de Periculosidade (30%)");
        itemVazio.setTipo("Provento");
        itemVazio.setValor(BigDecimal.ZERO.setScale(ESCALA));
        return itemVazio;
    }
}
