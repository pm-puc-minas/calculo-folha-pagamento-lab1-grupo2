package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção customizada para ser lançada quando um recurso solicitado não é encontrado.
 * Herda de RuntimeException para evitar 'try-catch' obrigatórios (checked exception).
 *
 * @ResponseStatus(HttpStatus.NOT_FOUND) serve como um fallback: se esta exceção
 * for lançada e não for capturada por nenhum @ExceptionHandler, o Spring
 * automaticamente retornará um status HTTP 404.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construtor que aceita uma mensagem de erro.
     * @param message A mensagem explicando por que a exceção foi lançada.
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}