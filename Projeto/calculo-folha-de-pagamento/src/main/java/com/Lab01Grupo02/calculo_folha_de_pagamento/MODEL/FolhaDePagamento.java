package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;

@Data
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
