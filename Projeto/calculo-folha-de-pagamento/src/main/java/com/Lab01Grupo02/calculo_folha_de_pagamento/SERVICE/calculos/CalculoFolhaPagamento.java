package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;


import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import java.math.BigDecimal;

public class CalculoFolhaPagamento {

    private final CalculoINSS calculoINSS = new CalculoINSS();
    private final CalculoIRRF calculoIRRF = new CalculoIRRF();
    private final CalculoPericulosidade calculoPericulosidade = new CalculoPericulosidade();
    private final CalculoInsalubridade calculoInsalubridade = new CalculoInsalubridade();
    private final CalculoBeneficio calculoBeneficios = new CalculoBeneficio();
    private final CalculoFGTS calculoFGTS = new CalculoFGTS();

    public FolhaDePagamento calcularFolha(double salarioBruto) {
        FolhaDePagamento folha = new FolhaDePagamento();

        folha.setSalarioBruto(BigDecimal.valueOf(salarioBruto));
        folha.setBaseINSS(calculoINSS.calcularDesconto(salarioBruto));
        folha.setBaseFGTS(calculoFGTS.calcularFGTS(salarioBruto));

        folha.setSalarioLiquido(
            folha.getSalarioBruto()
                .subtract(folha.getBaseINSS())
                .subtract(folha.getBaseFGTS())
        );

        return folha;
    }
}
