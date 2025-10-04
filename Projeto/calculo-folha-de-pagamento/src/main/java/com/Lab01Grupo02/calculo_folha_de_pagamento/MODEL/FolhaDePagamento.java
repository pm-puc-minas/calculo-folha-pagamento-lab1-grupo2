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
}