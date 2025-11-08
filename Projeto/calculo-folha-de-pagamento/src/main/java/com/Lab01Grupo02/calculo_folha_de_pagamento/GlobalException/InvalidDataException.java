package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

/**
 * Exceção lançada quando os dados fornecidos são inválidos ou inconsistentes.
 * Exemplos: salário negativo, data de admissão futura, CPF inválido, etc.
 */
public class InvalidDataException extends RuntimeException {

    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
