package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Funcionario extends Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal salarioBruto;
    private LocalDate dataAdmissao;
    private boolean temPericulosidade;
    private boolean temInsalubridade;
    private String grauInsalubridade;
    private String cargo;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependente> dependentes;

    // Métodos adicionais do diagrama
    public int getQuantidadeDependentes() {
        return dependentes != null ? dependentes.size() : 0;
    }
}
