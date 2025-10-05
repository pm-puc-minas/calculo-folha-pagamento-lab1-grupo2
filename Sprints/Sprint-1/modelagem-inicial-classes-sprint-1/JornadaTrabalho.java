package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;
import java.time.YearMonth;

public class JornadaTrabalho {
    private double horasSemanais;
    private double horasMensais;

    public JornadaTrabalho(double horasSemanais) {
        this.horasSemanais = horasSemanais;
        this.horasMensais = horasSemanais * 4.5;
    }

    public int getHorasSemanais() {
        return (int) horasSemanais;
    }

    public int getHorasMensais(YearMonth periodo) {
        return (int) horasMensais;
    }

    public BigDecimal calcularSalarioHora(BigDecimal salarioBruto, YearMonth periodo) {
        return salarioBruto.divide(BigDecimal.valueOf(horasMensais), 2, BigDecimal.ROUND_HALF_UP);
    }
}
