
package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter // Define os métodos getters para todos os campos
@Setter // Define os métodos setters para todos os campos
@NoArgsConstructor // Define um construtor sem argumentos
@AllArgsConstructor // Define um construtor com argumentos

@Entity
@Table(name = "FUNCIONARIOS")
@PrimaryKeyJoinColumn(name = "ID_Pessoa")
public class Funcionario extends Pessoa {

    @Column(name = "SalarioBruto")
    private BigDecimal salarioBruto;

    @Column(name = "DataAdmissao")
    private LocalDate dataAdmissao;

    @Column(name = "TemPericulosidade")
    private boolean temPericulosidade;

    @Column(name = "TemInsalubridade")
    private boolean temInsalubridade;

    @Column(name = "GrauInsalubridade")
    private String grauInsalubridade;
    @Column(name = "Cargo")
    private String cargo;

    // Relação com Dependente
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependente> dependentes;

    public int getQuantidadeDependentes() {
        return dependentes != null ? dependentes.size() : 0;
    }
}
