package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes;

import org.springframework.boot.SpringApplication;

import com.Lab01Grupo02.calculo_folha_de_pagamento.CalculoFolhaDePagamentoApplication;

public class TestCalculoFolhaDePagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.from(CalculoFolhaDePagamentoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
