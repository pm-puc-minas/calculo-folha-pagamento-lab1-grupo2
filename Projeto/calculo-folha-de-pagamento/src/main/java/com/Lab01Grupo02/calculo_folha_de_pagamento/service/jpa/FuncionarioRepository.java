package com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * NOTA: Este é o repositório para a entidade Funcionario.
 * Ele irá fornecer os métodos para buscar funcionários no banco de dados,
 * como findById(), findAll(), etc.
 * O 'Funcionario' é a entidade e 'Integer' é o tipo da sua chave primária (ID_Pessoa).
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
    // --- 1. Busca por NOME ---
    /**
     * Busca funcionários cujo nome contém a string fornecida (ignorando maiúsculas/minúsculas).
     * SQL (Equivalente): WHERE UPPER(nome) LIKE UPPER('%nome%')
     */
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);


    // --- 2. Busca por CPF ---
    /**
     * Busca um funcionário pelo seu CPF (busca exata).
     * SQL (Equivalente): WHERE cpf = 'numero_cpf'
     */
    Optional<Funcionario> findByCpf(String cpf);
}