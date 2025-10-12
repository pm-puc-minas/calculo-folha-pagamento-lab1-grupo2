package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.ICalculadora;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CalculoIRRF implements ICalculadora {

    private static final BigDecimal DEDUCAO_POR_DEPENDENTE = new BigDecimal("189.59");
    private static final int ESCALA = 2;

    @Override
    public List<ItemFolha> calcularFolhaCompleta(Funcionario funcionario) {
        List<ItemFolha> lista = new ArrayList<>();
        if (funcionario == null || funcionario.getSalarioBruto() == null) {
            lista.add(criarItemVazio());
            return lista;
        }

        BigDecimal salarioBase = funcionario.getSalarioBruto();
        BigDecimal deducaoDependentes = DEDUCAO_POR_DEPENDENTE
                .multiply(BigDecimal.valueOf(funcionario.getNumeroDependentes()));
        BigDecimal baseCalculo = salarioBase.subtract(deducaoDependentes);

        if (baseCalculo.compareTo(BigDecimal.ZERO) <= 0) {
            lista.add(criarItemVazio());
            return lista;
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

        BigDecimal irrf = baseCalculo.multiply(aliquota).subtract(deducaoIR)
                .setScale(ESCALA, RoundingMode.HALF_UP);

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        ItemFolha item = new ItemFolha();
        item.setDesc("IRRF - Imposto de Renda Retido na Fonte");
        item.setTipo("Desconto");
        item.setValor(irrf.negate());
        lista.add(item);
        return lista;
    }

    private ItemFolha criarItemVazio() {
        ItemFolha item = new ItemFolha();
        item.setDesc("IRRF - Imposto de Renda Retido na Fonte");
        item.setTipo("Desconto");
        item.setValor(BigDecimal.ZERO.setScale(ESCALA));
        return item;
    }
}
