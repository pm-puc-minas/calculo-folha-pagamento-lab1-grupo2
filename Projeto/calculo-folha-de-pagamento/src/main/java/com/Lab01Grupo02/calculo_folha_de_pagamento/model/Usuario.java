package com.Lab01Grupo02.calculo_folha_de_pagamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade Usuario que representa um usuário do sistema.
 * Estende a classe Pessoa e adiciona campos específicos para autenticação.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "USUARIO")
@PrimaryKeyJoinColumn(name = "ID_Pessoa")
public class Usuario extends Pessoa {
    
    @Column(name = "Email", unique = true, nullable = false)
    @Email(message = "Email deve ter um formato válido")
    @NotBlank(message = "Email é obrigatório")
    private String email;
    
    @Column(name = "Senha", nullable = false)
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter pelo menos 6 caracteres")
    private String senha;
    
    @Column(name = "Ativo", nullable = false)
    private boolean ativo = true;
    
    /**
     * Construtor que aceita os campos da classe pai Pessoa.
     */
    public Usuario(String nome, String cpf, String email, String senha) {
        super();
        this.setNome(nome);
        this.setCpf(cpf);
        this.email = email;
        this.senha = senha;
        this.ativo = true;
    }
}
