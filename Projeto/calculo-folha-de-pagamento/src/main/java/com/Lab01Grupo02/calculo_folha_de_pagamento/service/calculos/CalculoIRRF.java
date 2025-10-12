package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoIRRF implements CalculoFolha {

    private static final BigDecimal DEDUCAO_POR_DEPENDENTE = new BigDecimal("189.59");
    private static final int ESCALA = 2;

    public ItemFolha calcular(Funcionario funcionario, BigDecimal inssDescontado) {

        if (funcionario == null || funcionario.getSalarioBruto() == null || inssDescontado == null) {
            return criarItemVazio();
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal numeroDependentes = BigDecimal.valueOf(funcionario.getNumeroDependentes());
        BigDecimal salarioBase = salarioBruto.subtract(inssDescontado);
        BigDecimal deducaoDependentes = DEDUCAO_POR_DEPENDENTE.multiply(numeroDependentes);
        BigDecimal baseCalculo = salarioBase.subtract(deducaoDependentes);

        if (baseCalculo.compareTo(BigDecimal.ZERO) <= 0) {
            return criarItemVazio();
        }

        BigDecimal aliquota;
        BigDecimal deducaoIR;

        if (baseCalculo.compareTo(new BigDecimal("1903.98")) <= 0) {
            aliquota = BigDecimal.ZERO;
            deducaoIR = BigDecimal.ZERO;
        } else if (baseCalculo.compareTo(new BigDecimal("2826.65")) <= 0) {
            aliquota = new BigDecimal("0.075");
            deducaoIR = new BigDecimal("142.80");
        } else if (baseCalculo.compareTo(new BigDecimal("3751.05")) <= 0) {
            aliquota = new BigDecimal("0.15");
            deducaoIR = new BigDecimal("354.80");
        } else if (baseCalculo.compareTo(new BigDecimal("4664.68")) <= 0) {
            aliquota = new BigDecimal("0.225");
            deducaoIR = new BigDecimal("636.13");
        } else {
            aliquota = new BigDecimal("0.275");
            deducaoIR = new BigDecimal("869.36");
        }

        BigDecimal irrf = baseCalculo.multiply(aliquota)
                                     .subtract(deducaoIR)
                                     .setScale(ESCALA, RoundingMode.HALF_UP);

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        ItemFolha item = new ItemFolha();
        item.setDesc("IRRF - Imposto de Renda Retido na Fonte");
        item.setTipo("Desconto");
        item.setValor(irrf.negate());
        return item;
    }

    private ItemFolha criarItemVazio() {
        ItemFolha itemVazio = new ItemFolha();
        itemVazio.setDesc("IRRF - Imposto de Renda Retido na Fonte");
        itemVazio.setTipo("Desconto");
        itemVazio.setValor(BigDecimal.ZERO.setScale(ESCALA));
        return itemVazio;
    }
}
