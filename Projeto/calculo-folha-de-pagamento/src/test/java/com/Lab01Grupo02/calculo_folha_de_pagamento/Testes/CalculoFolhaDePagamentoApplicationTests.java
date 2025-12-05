package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.ICalculadora;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;

@SpringBootTest
class CalculoFolhaDePagamentoApplicationTests {

	@Autowired
	private ICalculadora calculadora;
	@Autowired
	private FuncionarioService funcionarioService;

	@Test
	void contextLoads() {
	}

	@Test
	void testCalculoFolhaComFaltasEInsalubridade() {
		// Cria um funcionário de exemplo
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Teste Folha");
		funcionario.setCpf("12345678900");
		funcionario.setDataNascimento(java.time.LocalDate.of(1990, 1, 1));
		funcionario.setSalarioBruto(new java.math.BigDecimal("2000.00"));
		funcionario.setGrauInsalubridade("Médio");
		funcionarioService.salvarFuncionario(funcionario);

		int diasFalta = 2;
		var itens = calculadora.calcularFolhaCompleta(funcionario, diasFalta);
		java.math.BigDecimal totalProventos = itens.stream().filter(i -> "PROVENTO".equals(i.getTipo())).map(ItemFolha::getValor).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
		java.math.BigDecimal totalDescontos = itens.stream().filter(i -> "DESCONTO".equals(i.getTipo())).map(ItemFolha::getValor).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);
		java.math.BigDecimal salarioLiquido = totalProventos.subtract(totalDescontos);

		// Verifica se o salário líquido é menor que o bruto devido às faltas
		org.junit.jupiter.api.Assertions.assertTrue(salarioLiquido.compareTo(funcionario.getSalarioBruto()) < 0);
	}
}
