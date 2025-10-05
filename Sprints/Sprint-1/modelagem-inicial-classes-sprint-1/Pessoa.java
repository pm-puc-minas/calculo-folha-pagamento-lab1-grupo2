package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Pessoa {

    private String nome;
    private String cpf;
    private String dataNascimento;

}
