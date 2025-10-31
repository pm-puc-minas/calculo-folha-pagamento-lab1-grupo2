package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

import java.time.LocalDateTime;

/**
 * Representa a estrutura padrão de uma resposta de erro da API.
 * Esta é uma classe imutável (DTO) que será convertida para JSON.
 *
 * @param statusCode O código de status HTTP (ex: 404, 500)
 * @param message A mensagem de erro principal (amigável para o usuário)
 * @param details Detalhes contextuais (ex: o URI da requisição)
 * @param timestamp A data e hora exatas em que o erro ocorreu
 */
public record ErrorResponse(
        int statusCode,
        String message,
        String details,
        LocalDateTime timestamp
) {
    /**
     * Construtor auxiliar para criar uma resposta de erro com o timestamp atual.
     */
    public ErrorResponse(int statusCode, String message, String details) {
        this(statusCode, message, details, LocalDateTime.now());
    }
}
