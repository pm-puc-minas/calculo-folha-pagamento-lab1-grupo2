package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoINSS implements ICalculoFolha {

    private static final BigDecimal TETO_INSS = new BigDecimal("7507.49");

    private enum FaixaINSS {
        FAIXA_1(new BigDecimal("0.00"), new BigDecimal("1302.00"), new BigDecimal("0.075")),
        FAIXA_2(new BigDecimal("1302.01"), new BigDecimal("2571.29"), new BigDecimal("0.09")),
        FAIXA_3(new BigDecimal("2571.30"), new BigDecimal("3856.94"), new BigDecimal("0.12")),
        FAIXA_4(new BigDecimal("3856.95"), new BigDecimal("7507.49"), new BigDecimal("0.14"));

        private final BigDecimal limiteInferior;
        private final BigDecimal limiteSuperior;
        private final BigDecimal aliquota;

        FaixaINSS(BigDecimal limiteInferior, BigDecimal limiteSuperior, BigDecimal aliquota) {
            this.limiteInferior = limiteInferior;
            this.limiteSuperior = limiteSuperior;
            this.aliquota = aliquota;
        }

        public BigDecimal getLimiteInferior() {
            return limiteInferior;
        }

        public BigDecimal getLimiteSuperior() {
            return limiteSuperior;
        }

        public BigDecimal getAliquota() {
            return aliquota;
        }
    }

    @Override
    public ItemFolha calcular(Funcionario funcionario) {
        if (funcionario == null || funcionario.getSalarioBruto() == null) {
            return criarItemVazio();
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal descontoINSS = calcularDescontoProgressivo(salarioBruto);

        ItemFolha item = new ItemFolha();
        item.setDesc("INSS");
        item.setTipo("DESCONTO");
        item.setValor(descontoINSS);

        return item;
    }

    private BigDecimal calcularDescontoProgressivo(BigDecimal salarioBruto) {
        if (salarioBruto == null || salarioBruto.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO; //Aqui, melhor usar tratamento de exceção com throws
        }

        BigDecimal salario = salarioBruto.min(TETO_INSS);
        BigDecimal descontoTotal = BigDecimal.ZERO;

        for (FaixaINSS faixa : FaixaINSS.values()) {
            if (salario.compareTo(faixa.getLimiteInferior()) > 0) {
                BigDecimal baseCalculo = salario.min(faixa.getLimiteSuperior())
                        .subtract(faixa.getLimiteInferior());
                BigDecimal descontoFaixa = baseCalculo.multiply(faixa.getAliquota());
                descontoTotal = descontoTotal.add(descontoFaixa);
            } else {
                break;
            }
        }

        return descontoTotal.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularAliquotaEfetiva(BigDecimal salarioBruto) {
        if (salarioBruto == null || salarioBruto.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal desconto = calcularDescontoProgressivo(salarioBruto);
        return desconto
                .divide(salarioBruto, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private ItemFolha criarItemVazio() {
        ItemFolha itemVazio = new ItemFolha();
        itemVazio.setDesc("INSS");
        itemVazio.setTipo("DESCONTO");
        itemVazio.setValor(BigDecimal.ZERO);
        return itemVazio;
    }
}