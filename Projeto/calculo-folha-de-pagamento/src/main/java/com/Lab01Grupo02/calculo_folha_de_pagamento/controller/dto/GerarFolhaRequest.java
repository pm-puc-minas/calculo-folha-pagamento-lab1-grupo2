package com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para receber a requisição de geração de folha.
 */
public record GerarFolhaRequest(
        @NotNull(message = "A Matricula não pode ser nula.")
        Integer matricula,

        @NotNull(message = "O mês de referência nao pode ser nulo.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate mesReferencia,

        @Min(value = 0, message = "Dias de falta não pode ser um valor negativo")
        int diasFalta
) {}

