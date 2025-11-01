package com.Lab01Grupo02.calculo_folha_de_pagamento.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter // Define os métodos getters para todos os campos
@Setter // Define os métodos setters para todos os campos
@NoArgsConstructor // Define um construtor sem argumentos
@AllArgsConstructor // Define um construtor com argumentos
    
// --- Anotações do JPA (Jakarta Persistence API) ---
// Elas transformam esta classe Java em um espelho de uma tabela do banco de dados.
@Entity  // Marca esta classe como uma "entidade", ou seja, um objeto que pode ser salvo no banco de dados.
@Table(name = "FOLHA_PAGAMENTO")
public class FolhaDePagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Folha")
    private int id_Folha;

    @Column(name = "Matricula_Funcionario")
    private int matricula;

    @Column(name = "MesReferencia")
    private LocalDate mesReferencia;

    @Column(name = "SalarioBrutoCalculo")
    private BigDecimal salarioBruto;

    @Column(name = "TotalProventos")
    private BigDecimal totalProvento;

    @Column(name = "TotalDescontos")
    private BigDecimal totalDesconto;

    @Column(name = "SalarioLiquido")
    private BigDecimal salarioLiquido;

    // -- NOVO CAMPO --
    /**
     * Armazena a quantidade de dias de falta
     */
    @Column(name = "DiasFalta")
    private int diasFalta;

    // --- RELACIONAMENTO ADICIONADO ---
    @JsonManagedReference // Lado "Pai" do relacionamento JSON (para evitar loops)
    @OneToMany(mappedBy = "folhaDePagamento", cascade = CascadeType.ALL)
    private List<ItemFolha> itens;

}
