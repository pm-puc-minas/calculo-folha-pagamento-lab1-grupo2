package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;

public class CalculoBeneficio {
    public double calcularVT(double salarioBruto, double valorVT) {
        return salarioBruto * 0.06; // Exemplo
    }

    public BigDecimal calcularVA(int diasUteis, double valorDiario) {
        return BigDecimal.valueOf(diasUteis * valorDiario);
    }
}
