package main.java.com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * NOTA: Este é o repositório para a entidade Funcionario.
 * Ele irá fornecer os métodos para buscar funcionários no banco de dados,
 * como findById(), findAll(), etc.
 * O 'Funcionario' é a entidade e 'Integer' é o tipo da sua chave primária (ID_Pessoa).
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Integer> {
}