package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import java.math.BigDecimal;
import java.math.RoundingMode;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.ICalculadora;

public class CalculoInsalubridade implements ICalculadora {

    @Override
    public FolhaDePagamento calcularFolha(double salarioMinimo, double grauRisco, String descricao) {
        BigDecimal salarioMinimoBD = BigDecimal.valueOf(salarioMinimo);
        BigDecimal grauRiscoBD = BigDecimal.valueOf(grauRisco);

        BigDecimal adicional = salarioMinimoBD.multiply(grauRiscoBD).setScale(2, RoundingMode.HALF_UP);

        FolhaDePagamento folha = new FolhaDePagamento();
        folha.setDescricao(descricao);
        folha.setTotalProvento(adicional);
        folha.setSalarioLiquido(adicional); // se não há descontos

        return folha;
    }
}
