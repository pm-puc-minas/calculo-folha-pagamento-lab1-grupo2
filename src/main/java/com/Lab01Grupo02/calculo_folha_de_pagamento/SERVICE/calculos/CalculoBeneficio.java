package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoBeneficio {
    public double calcularVT(double salarioBruto, double valorVT) {
        double limiteDesconto = salarioBruto * 0.06;
        if (valorVT < limiteDesconto) {
            return valorVT;
        } else {
            return limiteDesconto;
        }
    }
    
    public BigDecimal calcularVA(int diasUteis, double valorDiario) {
        double valorTotalVA = diasUteis * valorDiario;
        
        return BigDecimal.valueOf(valorTotalVA)
                         .setScale(2, RoundingMode.HALF_UP);
    }
}
