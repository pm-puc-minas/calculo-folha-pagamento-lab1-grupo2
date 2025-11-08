package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

/**
 * Exceção lançada quando tenta-se cadastrar um funcionário com CPF duplicado.
 * Esta exceção indica violação de regra de negócio: CPF deve ser único.
 */
public class DuplicateCpfException extends RuntimeException {

    public DuplicateCpfException(String message) {
        super(message);
    }

    public DuplicateCpfException(String message, Throwable cause) {
        super(message, cause);
    }
}
