/**
 * O CODIGO FUNCIONARIO FOI ALTERADO OS ATRIBUTOS PARA CORRESPONDER CORRETAMENTE AO BANCO DE DADOS
 * ALTERAÇÃO EFETUADA POR: Iago Adrien
 */
package com.Lab01Grupo02.calculo_folha_de_pagamento.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.converter.LocalDateAttributeConverter;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "FUNCIONARIO")
@PrimaryKeyJoinColumn(name = "Matricula") // Define que "Matricula" é a PK desta tabela e FK para a tabela Pessoa
public class Funcionario extends Pessoa {

    // --- CORREÇÃO 1: REMOVIDO ---
    // Alterado "Id_Pessoa" alterado para Matricula, pois Id pessoa nao é um dado reconhecido pelo RH
    // @Column(name = "matricula") <REMOVIDO>
    // private int matricula; <REMOVIDO>

    @Column(name = "Cargo") // Mapeamento estava faltando
    private String cargo;

    @Column(name = "DataAdmissao")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate dataAdmissao;

    @Column(name = "SalarioBruto")
    private BigDecimal salarioBruto;

    @Column(name = "GrauInsalubridade")
    private String grauInsalubridade;

    // --- Adicionado Carga Horaria Semnal ---
    @Column(name = "CargaHorariaSemanal")
    private int cargaHorariaSemanal;

    // --- CORREÇÃO 2: RENOMEADO ---
    // O campo é "PossuiPericulosidade"
    @Column(name = "PossuiPericulosidade")
    private Boolean possuiPericulosidade = false; // Alterado para Boolean wrapper para aceitar null

    // --- CORREÇÃO 3: REMOVIDO ---
    // O campo "TemInsalubridade" (boolean) não está na imagem
    // e é redundante, já que "GrauInsalubridade" (String) já passa essa informação.
    // (Ex: se for "Nenhum" ou nulo, não tem; se por "Minimo", "Medio", etc., ele tem).
    // @Column(name = "TemInsalubridade")
    // private boolean temInsalubridade;

    @Column(name = "Senha")
    private String senha;

    @Column(name = "Ativo")
    private Boolean ativo = true; // Alterado para Boolean wrapper para aceitar null

    @JsonManagedReference
    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dependente> dependentes;

    // Método auxiliar (Correto, pode manter)
    public int getQuantidadeDependentes() {
        return dependentes != null ? dependentes.size() : 0;
    }
}