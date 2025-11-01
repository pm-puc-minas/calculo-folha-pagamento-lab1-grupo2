package com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto;

import java.time.LocalDate;

/**
 * DTO (Data Transfer Object) para receber a requisição de geração de folha.
 */
public record GerarFolhaRequest(
        Integer matricula,
        LocalDate mesReferencia,
        int diasFalta
) {}

