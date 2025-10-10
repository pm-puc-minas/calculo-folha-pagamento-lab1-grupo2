# Documentação: Nomeclatura das pastas; ICalculo; CalculoFolha; CalculoFGTS; CalculoService; TestCalculoFGTS; Main
Correções efetuadas após a apresentação no dia 09/10/2025
---
## Nome das partas
Foi alterada os nomes maiusculos para minusculos em relacao a: service, model, config, controller

---

## Interface: ICalculo
### Parametro
COMO ERA: `calcularFolha(BigDecimal salarioBruto)`

COMO FICOU: `calcularFolhaCompleta(Funcionario funcionario)`
- Ao passar o objeto Funcionario completo, nós tornamos a calculadora infinitamente mais flexível e preparada para o futuro.
Qualquer cálculo que criarmos terá acesso a todas as informações do funcionário (salário, dependentes, cargo, data de admissão, etc.),
garantindo que a regra de negócio possa ser implementada corretamente, por mais complexa que seja.

### Retorno
A mudança no tipo de retorno foi para separar as responsabilidades e deixar o sistema mais organizado.

- Antes: O retorno era FolhaDePagamento.

  - Problema: Isso forçava a calculadora a ter duas responsabilidades: 1) calcular cada item individual (FGTS, INSS) e 2) 
somar tudo para gerar os totais (salário líquido, total de descontos).

- Agora: O retorno é List<ItemFolha> (uma lista de itens).

  - Benefício: A calculadora agora tem uma única e clara responsabilidade: calcular e entregar a lista de todas as "linhas" individuais do holerite. Ela não se preocupa mais com os totais.

Em resumo, a calculadora agora gera as peças (a List<ItemFolha>), e quem a chamou fica com a tarefa de montar o produto final (o objeto FolhaDePagamento com os totais).

---

## Interface: CalculoFolha
A `CalculoFolha` é uma interface que estabelece um padrão para um "Módulo de Cálculo". Sua finalidade é garantir que todos os cálculos individuais, como FGTS e INSS, possuam a mesma estrutura e sejam processados de maneira uniforme pelo sistema, o que promove a programação modular.

A interface define um método padrão que cada módulo de cálculo deve obrigatoriamente implementar:

- Método: ItemFolha calcular(Funcionario funcionario)

  - Entrada: Recebe o objeto Funcionario sobre o qual o cálculo será aplicado, para ter acesso a todos os dados necessários.

  - Saída: Retorna um único objeto ItemFolha, que contém o resultado específico do cálculo e representa uma linha individual no holerite.

---

## CalculoFGTS
- Padronização: A classe passou a implementar a interface CalculoFolha, tornando-se um "Módulo de Cálculo" padronizado que pode ser usado de forma genérica pelo CalculadoraService.

- Flexibilidade: A entrada do método de cálculo foi alterada de apenas um BigDecimal salarioBruto para o objeto Funcionario completo, permitindo acesso a todas as informações do funcionário para futuras regras de negócio.

- Contexto no Retorno: O método, que antes retornava apenas o valor numérico BigDecimal, agora retorna um objeto ItemFolha, que inclui o valor, a descrição ("FGTS") e o tipo ("Provento"), tornando o resultado mais completo e fácil de processar.

- Robustez: Foi adicionada uma verificação de segurança para tratar dados nulos, evitando NullPointerExceptions.

---

## CalculoService
### Resumo da Classe CalculadoraService
A CalculadoraService é uma classe de serviço gerenciada pelo Spring (@Service) que funciona como o orquestrador central do sistema de cálculos. Ela implementa a interface ICalculadora e sua principal responsabilidade é utilizar uma lista de módulos de cálculo individuais para processar uma folha de pagamento completa.

### Funcionamento:

- A classe mantém uma lista privada de módulos chamada modulosDeCalculo, onde cada módulo é do tipo genérico CalculoFolha.

- No construtor, essa lista é inicializada com todas as implementações de cálculo que o sistema suporta, como new CalculoFGTS(). O design permite que novos cálculos sejam adicionados simplesmente instanciando a nova classe nesta lista.

- O método principal, calcularFolhaCompleta, recebe um Funcionario.

- Dentro do método, ele primeiro cria uma lista de ItemFolha e adiciona o "Salário Bruto" como o item base.

- Em seguida, ele itera sobre cada modulo na lista modulosDeCalculo, chama o método calcular de cada um e adiciona o ItemFolha resultante à lista final.

- Por fim, retorna a lista completa com todos os itens calculados.

- A principal característica desta classe é a modularidade, demonstrada pelo laço que executa cada módulo de forma genérica, sem precisar saber qual cálculo específico está sendo realizado.

---

## TestCalculoFGTS
### Estrutura e Funcionamento

- Setup Inicial: A classe utiliza um método setUp() anotado com @BeforeEach, que garante a criação de uma nova instância "limpa" de CalculoFGTS antes da execução de cada teste, evitando interferências entre eles.

- Padrão de Teste: Os testes seguem o padrão "Arrange, Act, Assert" (Preparar, Agir, Verificar), conforme indicado pelos comentários no código. Primeiro, um cenário é preparado; em seguida, o método a ser testado é executado; e, por fim, o resultado é verificado.

### Testes Implementados

- A classe contém quatro testes distintos, cada um com um objetivo claro, indicado pela anotação @DisplayName:

- Cálculo com Salário Válido: O teste deveCalcularFgtsParaSalarioValido verifica se o cálculo está correto para um salário de "5000.00", esperando um resultado de "400.00". Ele também valida se a descrição do item resultante é "FGTS" e o tipo é "Provento".

- Cálculo com Salário Nulo: O teste deveRetornarZeroParaSalarioNulo assegura que, ao receber um funcionário com salarioBruto nulo, o método retorna um valor igual a BigDecimal.ZERO.

- Cálculo com Funcionário Nulo: O teste deveLidarComFuncionarioNuloSemLancarExcecao confirma que o método é seguro contra nulidade ("null safety"). Ele passa um objeto Funcionario inteiramente null e verifica se o resultado retornado é BigDecimal.ZERO, sem lançar uma exceção.

- Cálculo com Salário Decimal: O teste deveCalcularFgtsParaSalarioComDecimais valida a precisão do cálculo para um salário com casas decimais, usando o valor "2550.75" e esperando o resultado "204.06".

---

## Main
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
