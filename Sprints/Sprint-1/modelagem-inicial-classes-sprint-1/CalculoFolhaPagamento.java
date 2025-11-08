package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaPagamento;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoFolhaPagamento {

    private final CalculoINSS calculoINSS = new CalculoINSS();
    private final CalculoIRRF calculoIRRF = new CalculoIRRF();
    private final CalculoPericulosidade calculoPericulosidade = new CalculoPericulosidade();
    private final CalculoInsalubridade calculoInsalubridade = new CalculoInsalubridade();
    private final CalculoBeneficio calculoBeneficios = new CalculoBeneficio();
    private final CalculoFGTS calculoFGTS = new CalculoFGTS();

    public FolhaPagamento calcularFolha(double salarioBase) {
        FolhaPagamento folha = new FolhaPagamento();

        BigDecimal salarioBruto = BigDecimal.valueOf(salarioBase).setScale(2, RoundingMode.HALF_UP);
        folha.setSalarioBruto(salarioBruto);

        BigDecimal adicionalPericulosidade = calculoPericulosidade.calcularAdicional(salarioBase);
        BigDecimal adicionalInsalubridade = calculoInsalubridade.calcularAdicional(salarioBase);
        BigDecimal totalBeneficios = calculoBeneficios.calcularTotalBeneficios(salarioBase);

        BigDecimal salarioBrutoComAdicionais = salarioBruto
                .add(adicionalPericulosidade)
                .add(adicionalInsalubridade);

        folha.setSalarioBruto(salarioBrutoComAdicionais); // Atualiza Salário Bruto na folha

        // INSS
        BigDecimal valorDescontoINSS = calculoINSS.calcularDesconto(salarioBrutoComAdicionais.doubleValue());
        folha.setValorINSS(valorDescontoINSS);

        // FGTS
        BigDecimal valorFGTS = calculoFGTS.calcularFGTS(salarioBrutoComAdicionais.doubleValue());
        folha.setValorFGTS(valorFGTS);

        // IRRF (após dedução do INSS)
        double baseIRRF = salarioBrutoComAdicionais.doubleValue() - valorDescontoINSS.doubleValue();
        BigDecimal valorDescontoIRRF = calculoIRRF.calcularDesconto(baseIRRF);
        folha.setValorIRRF(valorDescontoIRRF);

        // Salário Líquido = Salário Bruto com Adicionais - INSS - IRRF
        BigDecimal salarioLiquido = salarioBrutoComAdicionais
                .subtract(valorDescontoINSS)
                .subtract(valorDescontoIRRF)
                .setScale(2, RoundingMode.HALF_UP);

        folha.setSalarioLiquido(salarioLiquido);

        return folha;
    }
}
