package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;
import java.math.BigDecimal;

public interface ICalculadora {
    FolhaDePagamento calcularFolha(double salarioMinimo, double grauRisco, String descricao);
}
