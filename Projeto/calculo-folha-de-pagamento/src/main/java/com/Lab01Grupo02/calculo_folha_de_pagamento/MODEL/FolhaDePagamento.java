package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;
import java.time.YearMonth;

@Entity
@Getter
@Setter
public class FolhaDePagamento {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     int matricula; // Atributo implementado para pesquisa de dados - Deve ser att na UML
     BigDecimal salarioBruto;
     BigDecimal totalProvento;
     BigDecimal totalDesconto;
     BigDecimal salarioLiquido;
     BigDecimal baseINSS;
     BigDecimal baseIRRF;
     BigDecimal baseFGTS;
     BigDecimal valorVT;
     BigDecimal valorVA;
     BigDecimal valorPericulosidade;
     BigDecimal valorInsalubridade;
     YearMonth mesReferencia;
     BigDecimal salarioHora;
}
