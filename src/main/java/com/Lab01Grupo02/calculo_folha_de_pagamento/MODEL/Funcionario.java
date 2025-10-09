package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Define os métodos getters para todos os campos
@Setter // Define os métodos setters para todos os campos
@NoArgsConstructor // Define um construtor sem argumentos
@AllArgsConstructor // Define um construtor com argumentos

public class Funcionario extends Pessoa  {
    private BigDecimal salarioBruto;
    private LocalDate dataAdmissao;
    private boolean temPericulosidade;
    private boolean temInsalubridade;
    private String grauInsalubridade;
    private String cargo;
    private List<Dependente> dependentes;
}
