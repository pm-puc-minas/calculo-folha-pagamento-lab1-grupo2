package com.Lab01Grupo02.calculo_folha_de_pagamento.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para resposta de login bem-sucedido.
 * Contém informações do usuário autenticado.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    
    private int idUsuario;
    private String nome;
    private String email;
    private String mensagem;
    private boolean sucesso;
    
    /**
     * Construtor para login bem-sucedido.
     */
    public LoginResponse(int idUsuario, String nome, String email) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.mensagem = "Login realizado com sucesso";
        this.sucesso = true;
    }
    
    /**
     * Construtor para falha no login.
     */
    public LoginResponse(String mensagem) {
        this.mensagem = mensagem;
        this.sucesso = false;
    }
}
