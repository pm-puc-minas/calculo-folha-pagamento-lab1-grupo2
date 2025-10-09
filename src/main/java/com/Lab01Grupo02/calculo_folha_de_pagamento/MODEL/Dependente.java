package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@Getter // Define os métodos getters para todos os campos
@Setter // Define os métodos setters para todos os campos
@NoArgsConstructor // Define um construtor sem argumentos
@AllArgsConstructor // Define um construtor com argumentos

@Entity
@Table(name = "DEPENDENTES")
@PrimaryKeyJoinColumn(name = "ID_Pessoa")
public class Dependente extends Pessoa {

    @Column(name = "Parentesco")
    private String parentesco;
    // Muitos dependentes para um funcionário
    @ManyToOne
    @JoinColumn(name = "ID_Funcionario")
    private Funcionario funcionario;
}
