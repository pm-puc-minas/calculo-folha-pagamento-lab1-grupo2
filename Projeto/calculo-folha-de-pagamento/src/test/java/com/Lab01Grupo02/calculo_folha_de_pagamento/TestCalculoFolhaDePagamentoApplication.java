package test.java.com.Lab01Grupo02.calculo_folha_de_pagamento;

import org.springframework.boot.SpringApplication;

public class TestCalculoFolhaDePagamentoApplication {

	public static void main(String[] args) {
		SpringApplication.from(CalculoFolhaDePagamentoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
