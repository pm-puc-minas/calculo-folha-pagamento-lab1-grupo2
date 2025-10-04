package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FolhaPagamentoRepository extends JpaRepository<FolhaDePagamento, Integer> {
    // O Spring Data JPA vai criar os métodos findAll(), findById(), save(), delete(), etc.
}