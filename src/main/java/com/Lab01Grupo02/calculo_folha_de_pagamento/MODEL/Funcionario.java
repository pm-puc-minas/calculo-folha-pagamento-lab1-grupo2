package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Funcionario  {
    private BigDecimal salarioBruto;
    private LocalDate dataAdmissao;
    private boolean temPericulosidade;
    private boolean temInsalubridade;
    private String grauInsalubridade;
    private String cargo;
    private List<Dependente> dependentes;
}
