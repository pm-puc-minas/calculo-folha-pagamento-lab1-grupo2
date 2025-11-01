package com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFolhaRepository extends JpaRepository<ItemFolha, Integer> {
}

