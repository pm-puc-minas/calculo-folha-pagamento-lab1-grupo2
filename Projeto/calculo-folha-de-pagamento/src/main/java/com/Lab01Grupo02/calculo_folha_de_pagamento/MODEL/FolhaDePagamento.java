package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FolhaDePagamento {
    private BigDecimal salarioBruto;
    private BigDecimal totalProvento;
    private BigDecimal totalDesconto;
    private BigDecimal salarioLiquido;
    private BigDecimal baseINSS;
    private BigDecimal baseIRRF;
    private BigDecimal baseFGTS;
    private BigDecimal valorVT;
    private BigDecimal valorVA;
    private BigDecimal valorPericulosidade;
    private BigDecimal valorInsalubridade;
    private YearMonth mesReferencia;
    private BigDecimal salarioHora;
}
