# Entrega Sprint 2

## Model
- [Model](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/tree/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/model)
  - [FolhaDePagamento](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/model/FolhaDePagamento.java)
  
  **NOTA:** Foi utilizada a dependência do Lombok para criar os getters, setters e construtores do objeto. Além da biblioteca da dependência JPA para rastrear os atributos na tabela com o banco de dados.
---
## Interfaces
- [JPA](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/tree/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/service/jpa)

  **NOTA:** Foi criada no momento da Sprint 2, duas interfaces ([FuncionarioRepository](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/service/jpa/FuncionarioRepository.java) e [FolhaPagamentoRepository](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/service/jpa/FolhaPagamentoRepository.java)) utilizando o JPA que gera automaticamente findById, findAll, etc.

- Calculo
  - [ICalculo](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/service/calculos/ICalculadora.java)

    - **NOTA:** Sua única responsabilidade é definir um método que possa processar uma folha completa para um funcionário.
  - [CalculoFolha](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/service/calculos/CalculoFolha.java)
    - **NOTA:** Ela garante que todo e qualquer cálculo individual (FGTS, INSS, etc.) tenha a mesma estrutura e seja tratado da mesma forma pelo sistema, promovendo a programação modular.
---
## Services
 - [x] FGTS
   - [Service](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/service/calculos/CalculoFGTS.java)
   - [Teste](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/test/java/com/Lab01Grupo02/calculo_folha_de_pagamento/CalculosTestes/TestCalculoFGTS.java)
- [ ] INSS
   - Service
   - Teste
- [ ] Insalubridade
   - Service
   - Teste
- [ ] Periculosidade
   - Service
   - Teste
- [ ] Beneficios
   - Service
   - Teste
- [ ] IRRF
   - Service
   - Teste
---
## Entrega Funcional
A entrega funcional foi adaptada ao método de cálculo da nossa calculadora FGTS. Encontrada no [main](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Projeto/calculo-folha-de-pagamento/src/main/java/com/Lab01Grupo02/calculo_folha_de_pagamento/CalculoFolhaDePagamentoApplication.java) da nossa aplicação.

Nesse caso, basta fazer download do nosso projeto em sua maquina e executar o projeto a partir de uma IDE executando ele em geral.O correto é ele fazer a conexão com o banco de dados e após isso solicitar o nome, matrícula e salário do funcionário.

### Fluxo de execução
1 -Inicialização do Spring: A aplicação Spring é iniciada através do `SpringApplication.run()`. Em seguida, uma instância da `CalculadoraService` é obtida a partir do contexto da aplicação (`context.getBean()`).

2 - Início do Modo Interativo: Um `Scanner` é criado para ler a entrada do usuário no console. Uma mensagem de boas-vindas é exibida para o "Teste Interativo da Calculadora de Folha de Pagamento".

3 - Coleta de Dados do Usuário: O programa solicita que o usuário preencha os dados de um "funcionário fictício". São solicitados o nome, a matrícula e o salário bruto.

4 - Para a matrícula e o salário, o código utiliza blocos `try-catch` para lidar com entradas inválidas (que não sejam números). Caso um erro de formato numérico (`NumberFormatException`) ocorra, uma mensagem de erro é exibida e um valor padrão (0 ou `BigDecimal.ZERO`) é utilizado.

5 - O código aceita tanto vírgula quanto ponto como separador decimal para o salário.

6 - Criação do Objeto: Com os dados coletados, uma nova instância de `Funcionario` é criada e seus atributos (matrícula, nome e salário) são preenchidos. Os dados do funcionário criado são exibidos no console para confirmação.

7 - Execução do Cálculo: O método `calcularFolhaCompleta` do `calculadoraService` é chamado, passando o funcionário recém-criado como argumento.

8 - Exibição do Resultado: O programa imprime uma seção de "ITENS DA FOLHA CALCULADOS". Em seguida, percorre a lista de `ItemFolha` retornada pelo serviço e exibe a descrição, o tipo и o valor de cada item de forma formatada.

9 - Finalização: Após a exibição dos resultados, uma mensagem de "TESTE FINALIZADO" é impressa e o contexto da aplicação Spring é fechado com `context.close()`.


