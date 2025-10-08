package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;


import java.math.BigDecimal;

public class CalculoINSS {
    public BigDecimal calcularDesconto(double salarioBruto) {
        return BigDecimal.valueOf(salarioBruto * 0.11); // Exemplo simples
    }
}

