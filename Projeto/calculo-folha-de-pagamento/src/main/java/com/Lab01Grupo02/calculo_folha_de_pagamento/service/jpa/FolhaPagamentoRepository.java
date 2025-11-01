package com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.FolhaDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaDePagamento, Integer> {
    // O Spring Data JPA vai criar os métodos findAll(), findById(), save(), delete(), etc.
    Optional<FolhaDePagamento> findByMatriculaAndMesReferencia(Integer matricula, LocalDate mesReferencia);
}