package com.Lab01Grupo02.calculo_folha_de_pagamento;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.CalculadoraService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class CalculoFolhaDePagamentoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CalculoFolhaDePagamentoApplication.class, args);
    }

/**
 * NÃO É PRECISO REMOVER ESSE CODIGO COMENTÁRIO POR ENQUANTO
 * PODE SER UTILIZADO PARA TESTES DE COMPILAÇÃO DA APLICAÇÃO CASO NECESSARIO.
 *
 *
    public static void main(String[] args) {
        // --- PASSO 1: INICIAR A APLICAÇÃO SPRING ---
        ConfigurableApplicationContext context = SpringApplication.run(CalculoFolhaDePagamentoApplication.class, args);
        CalculadoraService calculadoraService = context.getBean(CalculadoraService.class);

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=====================================================");
            System.out.println("==  Teste Interativo da Calculadora de Folha de Pagamento  ==");
            System.out.println("=====================================================");
            System.out.println("\nPor favor, preencha os dados do funcionário fictício:");

            // --- PASSO 2: COLETAR DADOS DO FUNCIONÁRIO ---
            System.out.print("Nome do Funcionário: ");
            String nome = scanner.nextLine();

            int matricula = 0;
            try {
                System.out.print("Matrícula (apenas números): ");
                matricula = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Matrícula inválida. Usando o valor 0.");
            }

            BigDecimal salarioBruto = BigDecimal.ZERO;
            try {
                System.out.print("Salário Bruto (ex: 3500.50): ");
                String salarioInput = scanner.nextLine().replace(",", ".");
                salarioBruto = new BigDecimal(salarioInput);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Salário inválido. O cálculo será feito com o valor 0.");
            }

            // --- PASSO 3: CRIAR OBJETO FUNCIONÁRIO ---
            Funcionario funcionario = new Funcionario();
            funcionario.setIdPessoa(matricula);
            funcionario.setNome(nome);
            funcionario.setSalarioBruto(salarioBruto);

            System.out.println("\n----------------------------------------------------");
            System.out.println("Funcionário criado para o teste:");
            System.out.println("Matrícula: " + funcionario.getIdPessoa());
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Salário Bruto: R$ " + funcionario.getSalarioBruto());
            System.out.println("----------------------------------------------------");

            // --- PASSO 4: CALCULAR FOLHA COMPLETA USANDO OS MÓDULOS ---
            System.out.println("Calculando folha de pagamento...");
            List<ItemFolha> itensDaFolha = calculadoraService.calcularFolhaCompleta(funcionario);

            // --- PASSO 5: EXIBIR RESULTADO ---
            System.out.println("\n--- ITENS DA FOLHA CALCULADOS ---");
            for (ItemFolha item : itensDaFolha) {
                System.out.printf("  - Descrição: %s | Tipo: %s | Valor: R$ %.2f\n",
                        item.getDesc(),
                        item.getTipo(),
                        item.getValor()
                );
            }
        }

        System.out.println("\n--- TESTE FINALIZADO ---");
        context.close();
    }
 */
}
