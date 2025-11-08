package com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para a entidade Usuario.
 * Fornece métodos para operações de banco de dados relacionadas à autenticação.
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    
    /**
     * Busca um usuário pelo email.
     * Usado para autenticação e verificação de duplicidade.
     * 
     * @param email O email do usuário
     * @return Optional contendo o usuário se encontrado
     */
    Optional<Usuario> findByEmail(String email);
    
    /**
     * Verifica se existe um usuário com o email fornecido.
     * 
     * @param email O email a ser verificado
     * @return true se o email já existe, false caso contrário
     */
    boolean existsByEmail(String email);
    
    /**
     * Busca um usuário pelo email e que esteja ativo.
     * Usado especificamente para login.
     * 
     * @param email O email do usuário
     * @param ativo Status ativo do usuário
     * @return Optional contendo o usuário se encontrado e ativo
     */
    Optional<Usuario> findByEmailAndAtivo(String email, boolean ativo);
}
