package main.java.com.Lab01Grupo02.calculo_folha_de_pagamento;

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
        // --- PASSO 1: INICIAR A APLICAÇÃO SPRING ---
        ConfigurableApplicationContext context = SpringApplication.run(CalculoFolhaDePagamentoApplication.class, args);
        CalculadoraService calculadoraService = context.getBean(CalculadoraService.class);

        // --- PASSO 2: INICIAR O MODO INTERATIVO COM O USUÁRIO ---.
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=====================================================");
            System.out.println("==  Teste Interativo da Calculadora de Folha de Pagamento  ==");
            System.out.println("=====================================================");
            System.out.println("\nPor favor, preencha os dados do funcionário fictício:");

            // --- PASSO 3: COLETAR OS DADOS DO USUÁRIO ---

            // Pedir o nome
            System.out.print("Nome do Funcionário: ");
            String nome = scanner.nextLine();

            // Pedir a matrícula
            int matricula = 0;
            try {
                System.out.print("Matrícula (apenas números): ");
                matricula = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erro: Matrícula inválida. Usando o valor 0.");
            }

            // Pedir o salário bruto
            BigDecimal salarioBruto = BigDecimal.ZERO;
            try {
                System.out.print("Salário Bruto (ex: 3500.50): ");
                String salarioInput = scanner.nextLine().replace(",", "."); // Aceita vírgula ou ponto
                salarioBruto = new BigDecimal(salarioInput);
            } catch (NumberFormatException e) {
                System.out.println("Erro: Salário inválido. O cálculo será feito com o valor 0.");
            }

            // --- PASSO 4: CRIAR O OBJETO FUNCIONÁRIO COM OS DADOS ---
            Funcionario funcionarioParaTeste = new Funcionario();
            funcionarioParaTeste.setIdPessoa(matricula);
            funcionarioParaTeste.setNome(nome);
            funcionarioParaTeste.setSalarioBruto(salarioBruto);

            System.out.println("\n----------------------------------------------------");
            System.out.println("Funcionário criado para o teste:");
            System.out.println("Matrícula: " + funcionarioParaTeste.getIdPessoa());
            System.out.println("Nome: " + funcionarioParaTeste.getNome());
            System.out.println("Salário Bruto: R$ " + funcionarioParaTeste.getSalarioBruto());
            System.out.println("----------------------------------------------------");

            // --- PASSO 5: EXECUTAR O CÁLCULO ---
            System.out.println("Calculando folha de pagamento...");
            List<ItemFolha> itensDaFolha = calculadoraService.calcularFolhaCompleta(funcionarioParaTeste);

            // --- PASSO 6: EXIBIR O RESULTADO ---
            System.out.println("\n--- ITENS DA FOLHA CALCULADOS ---");
            for (ItemFolha item : itensDaFolha) {
                System.out.printf("  - Descrição: %s | Tipo: %s | Valor: R$ %.2f\n",
                        item.getDesc(),
                        item.getTipo(),
                        item.getValor()
                );
            }
        } // Fechamento do Scanner

        System.out.println("\n--- TESTE FINALIZADO ---");

        // Fechar aplicação
        context.close();
    }
}