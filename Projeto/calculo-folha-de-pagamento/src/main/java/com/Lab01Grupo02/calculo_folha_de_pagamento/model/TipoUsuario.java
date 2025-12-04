package com.Lab01Grupo02.calculo_folha_de_pagamento.model;

/**
 * Enum que define os tipos de usuário do sistema.
 *
 * RH: Usuário com acesso administrativo completo (pode gerenciar funcionários, folhas, etc.)
 * FUNCIONARIO: Usuário com acesso limitado (pode apenas visualizar seus próprios dados)
 */
public enum TipoUsuario {
    RH("RH"),
    FUNCIONARIO("Funcionário");

    private final String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
