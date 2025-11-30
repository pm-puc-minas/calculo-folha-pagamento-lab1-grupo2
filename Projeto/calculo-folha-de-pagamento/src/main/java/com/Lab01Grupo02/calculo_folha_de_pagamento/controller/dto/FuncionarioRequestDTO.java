package com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto;

public class FuncionarioRequestDTO {
    // Dados Pessoais (Herança de Pessoa)
    public String nome;
    public String cpf;
    public String dataNascimento; // Recebe como String "yyyy-MM-dd"

    // Dados Funcionais
    public String cargo;
    public String dataAdmissao;   // Recebe como String "yyyy-MM-dd"
    public String salarioBruto;   // Recebe como String "2500.00"
    public String grauInsalubridade; // "MINIMO", "MEDIO", "MAXIMO" ou null
    public Integer cargaHorariaSemanal;
    public Boolean possuiPericulosidade;
}