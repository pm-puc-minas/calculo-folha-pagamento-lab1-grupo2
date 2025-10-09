package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Define os métodos getters para todos os campos
@Setter // Define os métodos setters para todos os campos
@NoArgsConstructor // Define um construtor sem argumentos
@AllArgsConstructor // Define um construtor com argumentos


public class Pessoa {
    private String nome;
    private String cpf;
    private String dataNascimento;
}
