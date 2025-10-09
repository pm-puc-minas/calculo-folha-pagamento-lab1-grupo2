package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FOLHA_PAGAMENTO") // <-- VOLTANDO PARA MAIÚSCULO
public class ItemFolha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Item")
    private int id_Folha;

    @Column(name = "ID_Folha")
    private int matricula;

    @Column(name = "Descricao")
    private String desc;

    @Column(name = "Tipo")
    private String tipo;

    @Column(name = "Valor")
    private BigDecimal valor;
}
