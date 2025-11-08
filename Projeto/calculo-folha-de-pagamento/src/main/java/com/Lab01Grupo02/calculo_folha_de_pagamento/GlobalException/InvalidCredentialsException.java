package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

/**
 * Exceção lançada quando as credenciais fornecidas são inválidas.
 * Utilizada quando o email ou senha estão incorretos durante o login.
 */
public class InvalidCredentialsException extends RuntimeException {
    
    public InvalidCredentialsException(String message) {
        super(message);
    }
    
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
