package com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para receber a requisição de geração de folha.
 */
public record GerarFolhaRequest(
        Integer matricula,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate mesReferencia,
        int diasFalta
) {}

