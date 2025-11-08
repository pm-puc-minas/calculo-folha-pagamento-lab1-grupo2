package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoBeneficio {
    

    public BigDecimal calcularVT(BigDecimal salarioBruto, BigDecimal valorVTTotal) {
        BigDecimal descontoLegal = salarioBruto.multiply(new BigDecimal("0.06")); 
        
  
        return descontoLegal.min(valorVTTotal).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calcularVA(int diasUteis, BigDecimal valorDiario) {
        return valorDiario.multiply(BigDecimal.valueOf(diasUteis)).setScale(2, RoundingMode.HALF_UP);
    }
}
