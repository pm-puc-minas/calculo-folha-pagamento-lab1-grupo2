package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

/**
 * Exceção lançada quando um usuário tenta fazer login com uma conta desativada.
 */
public class UserDeactivatedException extends RuntimeException {
    
    public UserDeactivatedException(String message) {
        super(message);
    }
    
    public UserDeactivatedException(String message, Throwable cause) {
        super(message, cause);
    }
}
