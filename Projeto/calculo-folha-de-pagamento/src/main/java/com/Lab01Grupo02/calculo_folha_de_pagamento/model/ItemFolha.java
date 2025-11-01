package com.Lab01Grupo02.calculo_folha_de_pagamento.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ITEM_FOLHA")
public class ItemFolha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Item")
    private int id_Folha;

    // --- RELACIONAMENTO ADICIONADO ---
    @JsonBackReference // Lado "Filho" do relacionamento JSON (para evitar loops)
    @ManyToOne
    @JoinColumn(name = "ID_Folha") // Mapeia a coluna de chave estrangeira
    private FolhaDePagamento folhaDePagamento;

    //@Column(name = "ID_Folha")
    //private int matricula;

    @Column(name = "Descricao")
    private String desc;

    @Column(name = "Tipo")
    private String tipo;

    @Column(name = "Valor")
    private BigDecimal valor;

}
