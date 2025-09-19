package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import lombok.Value;

import java.math.BigDecimal;
import java.time.YearMonth;

@Value
public class FolhaDePagamento {
     int matricula; // Atributo implementado para pesquisa de dados - Deve ser att na UML
     BigDecimal salarioBruto;
     BigDecimal totalProvento;
     BigDecimal totalDesconto;
     BigDecimal salarioLiquido;
     BigDecimal baseINSS;
     BigDecimal baseIRRF;
     BigDecimal baseFGTS;
     BigDecimal valorVT;
     BigDecimal valorVA;
     BigDecimal valorPericulosidade;
     BigDecimal valorInsalubridade;
     YearMonth mesReferencia;
     BigDecimal salarioHora;
}
