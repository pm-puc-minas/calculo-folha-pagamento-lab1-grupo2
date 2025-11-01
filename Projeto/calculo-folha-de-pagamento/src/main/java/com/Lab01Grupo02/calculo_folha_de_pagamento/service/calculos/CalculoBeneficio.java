package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoBeneficio implements ICalculoFolha {

    private BigDecimal valorVT;
    private Integer diasUteis;
    private BigDecimal valorDiarioVA;
    private TipoBeneficio tipoBeneficio;

    public enum TipoBeneficio {
        VALE_TRANSPORTE,
        VALE_ALIMENTACAO
    }

    public CalculoBeneficio(TipoBeneficio tipoBeneficio) {
        this.tipoBeneficio = tipoBeneficio;
    }

    public CalculoBeneficio(TipoBeneficio tipoBeneficio, BigDecimal valorVT) {
        this.tipoBeneficio = tipoBeneficio;
        this.valorVT = valorVT;
    }

    public CalculoBeneficio(TipoBeneficio tipoBeneficio, Integer diasUteis, BigDecimal valorDiarioVA) {
        this.tipoBeneficio = tipoBeneficio;
        this.diasUteis = diasUteis;
        this.valorDiarioVA = valorDiarioVA;
    }

    @Override
    public ItemFolha calcular(Funcionario funcionario) {
        if (tipoBeneficio == TipoBeneficio.VALE_TRANSPORTE) {
            if (funcionario == null || funcionario.getSalarioBruto() == null) {
                return criarItemVazio();
            }
            return calcularValeTransporte(funcionario);
        } else if (tipoBeneficio == TipoBeneficio.VALE_ALIMENTACAO) {
            return calcularValeAlimentacao();
        }

        return criarItemVazio();
    }

    private ItemFolha calcularValeTransporte(Funcionario funcionario) {
        if (valorVT == null || valorVT.compareTo(BigDecimal.ZERO) <= 0) {
            return criarItemVazio();
        }

        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal limiteDesconto = salarioBruto.multiply(new BigDecimal("0.06"));
        BigDecimal descontoVT;

        if (valorVT.compareTo(limiteDesconto) < 0) {
            descontoVT = valorVT;
        } else {
            descontoVT = limiteDesconto;
        }

        ItemFolha item = new ItemFolha();
        item.setDesc("Vale Transporte");
        item.setTipo("DESCONTO");
        item.setValor(descontoVT.setScale(2, RoundingMode.HALF_UP));

        return item;
    }

    private ItemFolha calcularValeAlimentacao() {
        if (diasUteis == null || diasUteis <= 0 || valorDiarioVA == null || valorDiarioVA.compareTo(BigDecimal.ZERO) <= 0) {
            ItemFolha itemVazio = new ItemFolha();
            itemVazio.setDesc("Vale Alimentação");
            itemVazio.setTipo("PROVENTO");
            itemVazio.setValor(BigDecimal.ZERO);
            return itemVazio;
        }

        BigDecimal valorTotalVA = valorDiarioVA.multiply(new BigDecimal(diasUteis));

        ItemFolha item = new ItemFolha();
        item.setDesc("Vale Alimentação");
        item.setTipo("PROVENTO");
        item.setValor(valorTotalVA.setScale(2, RoundingMode.HALF_UP));

        return item;
    }

    private ItemFolha criarItemVazio() {
        ItemFolha itemVazio = new ItemFolha();
        if (tipoBeneficio == TipoBeneficio.VALE_TRANSPORTE) {
            itemVazio.setDesc("Vale Transporte");
            itemVazio.setTipo("DESCONTO");
        } else {
            itemVazio.setDesc("Vale Alimentação");
            itemVazio.setTipo("PROVENTO");
        }
        itemVazio.setValor(BigDecimal.ZERO);
        return itemVazio;
    }

    public static CalculoBeneficio valeTransporte(BigDecimal valorVT) {
        return new CalculoBeneficio(TipoBeneficio.VALE_TRANSPORTE, valorVT);
    }

    public static CalculoBeneficio valeAlimentacao(Integer diasUteis, BigDecimal valorDiarioVA) {
        return new CalculoBeneficio(TipoBeneficio.VALE_ALIMENTACAO, diasUteis, valorDiarioVA);
    }
}