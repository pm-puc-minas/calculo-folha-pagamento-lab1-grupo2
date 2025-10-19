package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoIRRF implements ICalculoFolha {

    private static final BigDecimal DEDUCAO_POR_DEPENDENTE = new BigDecimal("189.59");
    
    private enum FaixaIRRF {
        ISENTO(new BigDecimal("0.00"), new BigDecimal("1903.98"), new BigDecimal("0.00"), new BigDecimal("0.00")),
        FAIXA_1(new BigDecimal("1903.99"), new BigDecimal("2826.65"), new BigDecimal("0.075"), new BigDecimal("142.80")),
        FAIXA_2(new BigDecimal("2826.66"), new BigDecimal("3751.05"), new BigDecimal("0.15"), new BigDecimal("354.80")),
        FAIXA_3(new BigDecimal("3751.06"), new BigDecimal("4664.68"), new BigDecimal("0.225"), new BigDecimal("636.13")),
        FAIXA_4(new BigDecimal("4664.69"), new BigDecimal("999999999.99"), new BigDecimal("0.275"), new BigDecimal("869.36"));

        private final BigDecimal limiteInferior;
        private final BigDecimal limiteSuperior;
        private final BigDecimal aliquota;
        private final BigDecimal deducao;

        FaixaIRRF(BigDecimal limiteInferior, BigDecimal limiteSuperior, BigDecimal aliquota, BigDecimal deducao) {
            this.limiteInferior = limiteInferior;
            this.limiteSuperior = limiteSuperior;
            this.aliquota = aliquota;
            this.deducao = deducao; //Isso NÃO existe em cálculo de IRRF
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

        public BigDecimal getDeducao() {
            return deducao;
        }

        public static FaixaIRRF obterFaixa(BigDecimal baseCalculo) {
            for (FaixaIRRF faixa : values()) {
                if (baseCalculo.compareTo(faixa.getLimiteInferior()) >= 0 &&
                        baseCalculo.compareTo(faixa.getLimiteSuperior()) <= 0) {
                    return faixa;
                }
            }
            return ISENTO;
        }
    }

    @Override
    public ItemFolha calcular(Funcionario funcionario) {
        return calcular(funcionario, null);
    }

    public ItemFolha calcular(Funcionario funcionario, BigDecimal descontoINSS) {
        if (funcionario == null || funcionario.getSalarioBruto() == null) {
            return criarItemVazio();
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal salarioBase = salarioBruto;

        if (descontoINSS != null && descontoINSS.compareTo(BigDecimal.ZERO) > 0) {
            salarioBase = salarioBruto.subtract(descontoINSS); //tem que descontar também valor de dependente, pensão alimentícia, se houver
        }

        int quantidadeDependentes = funcionario.getQuantidadeDependentes();
        BigDecimal deducaoDependentes = DEDUCAO_POR_DEPENDENTE.multiply(BigDecimal.valueOf(quantidadeDependentes));
        BigDecimal baseCalculo = salarioBase.subtract(deducaoDependentes);

        if (baseCalculo.compareTo(BigDecimal.ZERO) <= 0) {
            return criarItemVazio();
        }

        FaixaIRRF faixa = FaixaIRRF.obterFaixa(baseCalculo);

        BigDecimal irrf = baseCalculo.multiply(faixa.getAliquota())
                .subtract(faixa.getDeducao())
                .setScale(2, RoundingMode.HALF_UP);

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        ItemFolha item = new ItemFolha();
        item.setDesc("IRRF");
        item.setTipo("Desconto");
        item.setValor(irrf);

        return item;
    }

    private ItemFolha criarItemVazio() {
        ItemFolha item = new ItemFolha();
        item.setDesc("IRRF");
        item.setTipo("Desconto");
        item.setValor(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP));
        return item;
    }
}
