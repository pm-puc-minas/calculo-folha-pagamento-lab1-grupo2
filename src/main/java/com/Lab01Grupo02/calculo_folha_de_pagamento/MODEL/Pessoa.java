package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import lombok.Getter;
import lombok.Setter;

@Getter // Define os métodos getters para todos os campos
@Setter // Define os métodos setters para todos os campos

public class Pessoa {
    private String nome;
    private String cpf;
    private String dataNascimento;
}
