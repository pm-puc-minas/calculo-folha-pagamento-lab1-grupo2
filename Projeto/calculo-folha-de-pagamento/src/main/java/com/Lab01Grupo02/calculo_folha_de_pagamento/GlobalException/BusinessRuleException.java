package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

/**
 * Exceção genérica lançada quando uma regra de negócio é violada.
 * Use esta exceção para regras de negócio que não se encaixam em outras categorias específicas.
 */
public class BusinessRuleException extends RuntimeException {

    public BusinessRuleException(String message) {
        super(message);
    }

    public BusinessRuleException(String message, Throwable cause) {
        super(message, cause);
    }
}
