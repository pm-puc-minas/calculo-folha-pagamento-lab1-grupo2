# Sprint 1


## Análise do estudo de caso e interpretação do conceito de domínio
| Conceito (Classe Candidata) | Análise |
| :--- | :--- |
| **Folha de Pagamento** | **Provável Classe**: Conceito central que agrega os proventos, descontos e o resultado líquido para um funcionário em um período de referência. |
| **Gestão de acesso** | **Provável Classe**: Responsável pelas operações de login e autenticação dos usuários, controlando quem pode acessar o sistema e suas funcionalidades. |
| **Pessoa** | **Provável Classe (Superclasse)**: Classe base que abstrai atributos comuns a todos os indivíduos no sistema (ex: nome, CPF, dados de contato). Serve como fundamento para outros tipos de atores. |
| **Funcionário** | **Provável Classe (Subclasse de Pessoa)**: Especialização que herda os atributos de Pessoa e adiciona informações específicas do contexto de trabalho, como cargo, salário, data de admissão e jornada de trabalho. |
| **Salário** | **Provável Classe**: Entidade que pode abranger diferentes tipos de salário (bruto, base, líquido, por hora) e seus respectivos cálculos. |
| **Adicionais** | **Operação**: Categoria que engloba os cálculos de Periculosidade e Insalubridade, que acrescem valor ao salário bruto. |
| **Benefícios** | **Operação**: Categoria que engloba os cálculos de Vale Transporte e Vale Alimentação. |
| **Descontos** | **Operação**: Categoria que engloba os cálculos de INSS e IRRF, que deduzem valor do salário bruto. |
| **Jornada de Trabalho** | **Provável Classe**: Agrega informações como horas diárias, dias por semana e horas mensais, usada para o cálculo do salário por hora. |
| **Dependente** | **Provável Classe**: Relaciona-se com o Funcionário e é uma entidade fundamental para a base de cálculo do Imposto de Renda (IRRF). |
| **Cálculo de Periculosidade** | **Operação**: Representa o cálculo do adicional de 30% sobre o salário do trabalhador. |
| **Cálculo de Insalubridade** | **Operação**: Representa o cálculo do adicional que pode ser de 10%, 20% ou 40% sobre o salário mínimo, dependendo do grau de risco. |
| **Cálculo de Vale Transporte** | **Operação**: Cálculo que considera o valor total do benefício e o limite de desconto de 6% do salário bruto. |
| **Cálculo de INSS** | **Operação**: Cálculo de desconto com base em alíquotas progressivas aplicadas a diferentes faixas salariais. |
| **Cálculo de FGTS** | **Operação**: Refere-se ao cálculo do depósito de 8% sobre o salário bruto, que é uma obrigação do empregador. |
| **Cálculo de IRRF** | **Operação**: Representa o cálculo do imposto de renda, que considera deduções como INSS e número de dependentes. |
| **Relatório** | **Entidade de Implementação**: Representa a exibição final de todas as informações e cálculos processados da folha de pagamento. |

---

## Identificação de requisitos do sistema
### Requisitos Funcionais

| ID | Descrição |
| :--- | :--- |
| **RF1** | O sistema deve ser capaz de calcular o valor do salário por hora de um funcionário a partir do seu salário bruto. |
| **RF2** | O sistema deve ser capaz de verificar se o funcionário é apto ao adicional de periculosidade e realizar o cálculo. |
| **RF3** | O sistema deve ser capaz de verificar se o funcionário é apto ao adicional de insalubridade e realizar o cálculo. |
| **RF4** | O sistema deve ser capaz de calcular o benefício de vale transporte. |
| **RF5** | O sistema deve ser capaz de calcular o benefício de vale alimentação. |
| **RF6** | O sistema deve ser capaz de calcular o desconto de INSS. |
| **RF7** | O sistema deve ser capaz de calcular o desconto de FGTS. |
| **RF8** | O sistema deve ser capaz de calcular o desconto de IRRF. |
| **RF9** | O sistema deve ser capaz de calcular o salário líquido do funcionário. |
| **RF10**| O sistema deve ser capaz de mostrar na tela o relatório da folha de pagamento. |
| **RF11**| O sistema deve ter uma forma de autenticação de usuário e identificar o perfil do usuário conectado, para saber se é funcionário, RH ou gerente. |

### Requisitos Não Funcionais

| ID | Categoria | Descrição |
| :--- | :--- | :--- |
| **RNF1** | **Usabilidade** | O sistema deverá ser fácil de usar e todas as mensagens mostradas na tela deverão estar muito bem escritas e formatadas. |
| **RNF2** | **Manutenabilidade** | O software deverá ser desenvolvido usando o paradigma Orientado a Objetos, com baixo acoplamento e alta coesão. |
| **RNF3** | **Manutenabilidade** | O código deverá estar limpo, muito bem organizado e estruturado, seguindo as boas práticas da programação. |
| **RNF4** | **Manutenabilidade** | O código fonte deverá seguir as convenções de código da linguagem de programação Java. |
| **RNF5** | **Usabilidade** | O sistema deverá registrar um log de todas as alterações realizadas, permitindo ao gerente a verificação e o controle das ações executadas pelo setor de RH. |

### Requisitos Extra (Opcionais)

| ID | Descrição |
| :--- | :--- |
| **RE1** | O sistema deve implementar em sua interface de linha de comando caracteres de desenhos de caixas, reproduzindo um visual retrô semelhante à interface MS-DOS. |
| **RE2** | O sistema deve utilizar a classe `BigDecimal` para a realização dos cálculos. |
| **RE3** | O sistema deverá verificar automaticamente quantas semanas tem o mês corrente. |

---

## Produção de Cartões CRC
### **CalculadoraFolhaPagamento**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Orquestrar o processo completo de cálculo da folha. | `Funcionario` |
| Delegar o cálculo de proventos e descontos para classes especializadas. | `JornadaDeTrabalho` |
| Montar o objeto final FolhaDePagamento. | `FolhaDePagamento`, `CalculoINSS`, `CalculoIRRF`, `CalculoPericulosidade`, `CalculoInsalubridade`, `CalculoBeneficios`, `CalculoFGTS` |

### **Funcionario**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Armazenar informações pessoais e contratuais. | `Pessoa` |
| Conhecer seu salário bruto e cargo. | `JornadaDeTrabalho` |
| Conhecer seus dependentes e se possui adicionais. | `Dependente` |


### **JornadaDeTrabalho**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Armazenar a carga horária diária e semanal. | Nenhum |
| Calcular a jornada mensal. | Nenhum |

### **CalculoINSS**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor de desconto do INSS com base na tabela progressiva. | Nenhum |


### **CalculoFGTS**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do FGTS. | Nenhum |


### **CalculoIRRF**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor de desconto de IRRF. | `CalculoINSS` |
| Conhecer o valor do INSS para dedução da base de cálculo. |`CalculoINSS` |

### **CalculoPericulosidade**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do adicional de periculosidade. | Nenhum |

### **CalculoInsalubridade**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do adicional de insalubridade. | Nenhum |

### **CalculoBeneficios**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Calcular o valor do vale-transporte e vale-alimentação. | Nenhum |

### **GestaoDeAcesso**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Validar a autenticidade das credenciais de um usuário. | `UsuarioRepository` |

### **LoginController**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Receber as credenciais do usuário. |`GestaoDeAcesso` |
| Iniciar o processo de autenticação. | |

### **RelatorioService**
| Responsabilidades | Colaboradores |
| :--- | :--- |
| Formatar e exibir o relatório da folha de pagamento. | `FolhaDePagamento`, `Funcionario`  | 

---

## Modelagem de um Diagrama de Classes
![UML Diagrama](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Sprints/Sprint-1/umlDiagrama.drawio.png)

---

## Modelagem inicial das classes do frontend (UI)
![Fluxo do Front-end](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/blob/main/Sprints/Sprint-1/Fluxo-Front-End.jpg)

## Documentação da Modelagem do Frontend

### Tela de Login
Porta de entrada do sistema, responsável pela autenticação do usuário.
- Tela simples onde o usuário se identifica para acessar o site.
- **Componentes principais:**
  - Campo para número de matrícula/e-mail.
  - Campo para senha.
- **Interações:**
  - O usuário preenche os campos e, após clicar em "Entrar" ou pressionar Enter, o frontend envia as credenciais para o endpoint de autenticação do backend.
  - Se a autenticação for bem-sucedida, o usuário é redirecionado para a `Tela Inicial`. Caso ocorra um erro, uma mensagem de erro é exibida na tela.

### Tela Inicial (RH)
A tela principal do sistema após o login, servindo como um painel central para todas as funcionalidades. Apresenta dados gerais, mas algumas informações e permissões могут variar.
- **Componentes principais:**
  - Barra de navegação com: `Gerenciamento de Funcionários`, `Folha de Pagamento` e `Relatórios`.
  - Botão "Logout".
  - Outras informações visuais (não é necessário implementar funcionalidades adicionais aqui).
- **Interações:**
  - O usuário clica nos links de navegação para acessar diferentes áreas do sistema.
  - Ao clicar no botão "Logout", ele é desconectado e redirecionado para a tela de login.

### Tela Inicial (Demais Funcionários)
A tela principal do sistema para usuários não-RH.
- **Componentes principais:**
  - Barra de navegação com: `Meu Perfil`, `Folha de Pagamento` e `Relatórios`.
  - Botão "Logout".
- **Interações:**
  - O usuário clica nos links de navegação para acessar suas informações pessoais e relatórios.
  - Ao clicar no botão "Logout", ele é desconectado e redirecionado para a tela de login.

### Gerenciamento de Funcionários (RH)
Esta tela permite ao usuário de RH gerenciar o cadastro dos funcionários da empresa.
- **Componentes principais:**
  - Tabela ou lista com os funcionários existentes e seus respectivos dados.
  - Botão para "Cadastrar Novo Funcionário".
  - Botões de "Editar" e "Excluir" (a exclusão deve ser lógica, não física do banco de dados).
  - Campo de busca e filtros.
- **Interações:**
  - Ao clicar em "Cadastrar Novo Funcionário", o usuário é levado para o `Formulário de Funcionário`.
  - Ao clicar em "Editar", é levado para o `Formulário de Funcionário` com os campos já preenchidos.
  - A busca permite filtrar a lista por CPF, nome ou matrícula, com opções de ordenação.

### Gerenciamento de Funcionário (Perfil Individual)
Permite que o usuário modifique dados não críticos em seu próprio perfil.
- **Componentes principais:**
  - Botão de "Editar".
- **Interações:**
  - Ao clicar em "Editar", é possível alterar algumas informações não relevantes para o sistema (ex: telefone de contato, endereço).

### Formulário de Funcionário
Tela dedicada à entrada e modificação de dados de um funcionário.
- **Componentes principais:**
  - Campos de texto para: Nome, CPF, Endereço, Cargo, Salário Base, entre outros.
  - Botão "Salvar".
  - Botão "Cancelar".
- **Interações:**
  - O usuário preenche ou edita os dados e clica em "Salvar".
  - O frontend envia os dados do formulário para o endpoint correspondente no backend.
  - Ao clicar em "Cancelar", o usuário retorna à tela anterior sem salvar as alterações.

### Folha de Pagamento
Tela focada na operação principal do sistema: o cálculo da folha de pagamento.
- **Componentes principais:**
  - Seletores de mês e ano para definir o período de competência.
  - Botão "Calcular Folha de Pagamento".
  - Área para exibição de status (ex.: "Calculando...", "Cálculo concluído com sucesso.").
- **Interações:**
  - O usuário seleciona o mês e o ano desejados.
  - Ao clicar em "Calcular Folha de Pagamento", uma chamada é feita ao backend para processar os pagamentos.
  - Após a conclusão, o usuário pode ser redirecionado para os `Relatórios` para ver o resultado detalhado.

### Relatórios
Esta tela permite a visualização dos resultados dos cálculos da folha de pagamento.
- **Componentes principais:**
  - Lista das folhas de pagamento já processadas, agrupadas por período.
  - Filtros por período ou por funcionário.
  - Opção para visualizar o contracheque individual de um funcionário.
- **Interações:**
  - O usuário seleciona uma folha de pagamento da lista para ver os detalhes.
  - O usuário pode aplicar filtros para encontrar pagamentos específicos.
  - Pode-se verificar o contracheque, que exibirá o salário bruto, descontos e o salário líquido.
 
## Implementação Extra

### Tela de Log
Ela permite ao Gerente conferir os logs de alterações, emissões de folha de pagamento, alteração de dados de usuário, entre outras informações executadas pelo RH.
- **Componentes principais:**
  ...
- **Interações:**
  ...


---

## Planejamento dos testes unitários
### 1. Cálculo INSS
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Verifica um calculo Progressivo para um salário que se enquadra na segunda faia da tabela de contribuição. | Salário bruto: 2000 | 1a Faixa: R$97,65; 2a Faixa: R$62,82; Valor do desconto do INSS: R$160,47 | ? |
| **N2** | Validar o calculo do INSS para um salário exatamente no valor do teto da tabela, percorrendo todas as faixas de contribuição. | Salario bruto: R$7507,49 | 1a Faixa: R$97,65; 2a Faixa: R$114,24; 3a Faixa: R$154,28; 4a Faixa: R$511,07; Valor do desconto do INSS: R$877,24 | ? |
| **N3** | Salários superiores ao teste , o calculo INSS seja limitado ao teto máximo de contribuição. (R$7507,49 em 2023) | R$8000 | Valor do desconto do INSS: R$877,24 (O Valor é o mesmo do teto pois a base de calculo é limitada a R$7507,49) | ? |

### 2. Cálculo IRRF
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Verificar o calculo para salário cujo base de cálculo de enquadra na primeira faixa da tabela, resultando em isenção de imposto. | Salário Bruto: R$2000; Desconto INSS: R$160,47; Dependentes: 0 | Salário Base: R$1839,53; Base de Calculo: R$1839,53; O valor na faixa até R$1903,98, que é insenta. | ? |
| **N2** | Valida o cálculo do IRRF para salário que se enquadra na terceira faixa da tabela, considerando a dedução do dependente. | Salario: R$3500; Desconto NSS: R$281,62; Dependentes: 1 | Salario base: R$3218,38; Base de Calculo: R$3028,79; Alicota da 3a faixa(15%): R$454,32; Subtrai dedução: R$99,52; Valor do desconto do IRRF: R$99,52 | ? |
| **N3** | Valida um calculo complexo do IRRF para um sálario na 5a faixa, incluindo múltiplas deduções. | Salario bruto: R$7507,49; Desconto: R$877,24; Dependentes: 2 | Dedução total: R$1256,42; Base de Calculo: R$651,07; Alicota 5a faixa: R$1719,04; Subtrai dedução da faica: R$849,68: Valor do desconto: R$849,68 | ? |

### 3. Cálculo Insalubridade
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Verifica se o o cálculo de insalubridade está correto para o grau de risco "Baixo", que corresponde a 10% do salário mínimo. | Salário: R$1380,60; Grau de risco: Baixo (10%) | Valor do adicional: R$138,06  | ? |
| **N2** |  Verifica se o o cálculo de insalubridade está correto para o grau de risco "Medio", que corresponde a 20% do salário mínimo. | Salário: R$1380,60; Grau de risco: Baixo (20%) | Valor do adicional: R$276,12 | ? |
| **N3** |  Verifica se o o cálculo de insalubridade está correto para o grau de risco "Alto", que corresponde a 40% do salário mínimo. | Salário: R$1380,60; Grau de risco: Baixo (40%) | Valor do adicional: R$552,24 | ? |


### 4. Cálculo Periculosidade
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Funcionário com direito a periculosidade | Salário: R$ 2.500,00, Apto: true | Adicional: R$ 750,00 (30% do salário) | ? |
| **N2** | Funcionário sem direito a periculosidade | Salário: R$ 3.000,00, Apto: false | Adicional: R$ 0,00 | ? |
| **N3** | Periculosidade com salário mínimo | Salário: R$ 1.380,60, Apto: true | Adicional: R$ 414,18 (30% do salário) | ? |

### 5. Cálculo Benefícios
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Vale alimentação para mês padrão | Vale diário: R$ 24,00, Dias úteis: 22 | Vale total: R$ 528,00 | ? |
| **N2** | Vale transporte menor que 6% do salário | Salário: R$ 3.000,00, Vale recebido: R$ 150,00 | Desconto: R$ 150,00 (valor integral) | ? |
| **N3** | Vale transporte maior que 6% do salário | Salário: R$ 2.000,00, Vale recebido: R$ 300,00 | Desconto: R$ 120,00 (6% do salário) | ? |

### 6. Cálculo FGTS
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | FGTS para salário médio | Salário bruto: R$ 3.000,00 | FGTS: R$ 240,00 (8% do salário) | ? |
| **N2** | FGTS para salário mínimo | Salário bruto: R$ 1.380,60 | FGTS: R$ 110,45 (8% do salário) | ? |
| **N3** | FGTS para salário alto | Salário bruto: R$ 7.500,00 | FGTS: R$ 600,00 (8% do salário) | ? |

### 7. Jornada de Trabalho
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Retornar horas semanais | Funcionário, Período: 07/09/2025 a 13/09/2025 | 40 horas semanais | ? |
| **N2** | Calcular horas mensais para mês completo | Funcionário, Mês: setembro/2025 | 160 horas mensais (40h/semana × 4 semanas)| ? |
| **N3** | Calcular horas mensais com faltas | Funcionário, Setembro/2025 com 2 faltas (16h) | 144 horas mensais (160 - 16h de faltas) | ? |
| **N4** | Calcular salário/hora base | Salário mensal: R$ 4.400,00, jornada: 160/mês | R$ 27,50 por hora | ? |

### 8. Acesso ao Banco de Dados
| Teste | Descrição | Entrada | Resultado Esperado | Status |
|-------|-----------|---------|-------------------|--------|
| **N1** | Conectar ao banco | String de conexão | Conexão estabelecida com sucesso, retorna true | ? |
| **N2** | Fechar Conexão com o banco | Sem dados  de entrada | Conexão fechada | ? |
| **N3** | Executar SELECT | Query: "SELECT COUNT(*) FROM Funcionarios" | Retornar dados da query: numero total de funcionarios | ? |

---

## Implementação inicial do esqueleto do sistema em Java Spring Boot
<table>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/c21b16bb-5164-4c3e-ae7f-a3f84783c53d" alt="Imagem 1">
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/e3814d98-68fa-4122-aa05-66ed9a4d4929" alt="Imagem 2">
    </td>
  </tr>
</table>

## Spring Boot Dependências iniciais para o esqueleto do projeto

- **1: LOMBOK:**  
  É uma biblioteca Java que ajuda a reduzir código *boilerplate* (código repetitivo e desnecessário) através do uso de anotações,  
  gerando automaticamente métodos como **getters**, **setters**, **construtores** e o método **toString()**.  

---

- **2: Spring Boot DevTools:**  
  Fornece ferramentas para facilitar o desenvolvimento, incluindo **reinicialização rápida da aplicação**, **LiveReload** e suporte a configurações que tornam a experiência de desenvolvimento mais produtiva.  
  Ele detecta alterações no código e reinicia automaticamente a aplicação, sem precisar parar e rodar manualmente.

---

- **3: Spring Security:**  
  Framework altamente personalizável para **autenticação** e **controle de acesso**.  
  Ele protege a aplicação contra acessos não autorizados e fornece uma estrutura robusta para implementação de **login**, **roles**, **permissões** e **segurança em APIs REST**.

---

- **4: Spring Data JPA:**  
  Simplifica a persistência de dados em bancos SQL através da **Java Persistence API (JPA)**, utilizando implementações como o **Hibernate**.  
  Permite criar repositórios com operações de CRUD de forma simples, sem necessidade de escrever consultas SQL complexas.

- **Ou: Spring Data JDBC:**  
  Alternativa mais leve ao JPA, focada em **simplicidade** e **performance**.  
  Indicado para projetos que não precisam de todas as funcionalidades do Hibernate, trabalhando diretamente com consultas SQL mais próximas do banco de dados.

---

- **5: Spring Web:**  
  Utilizado para construir aplicações **web**, incluindo **APIs RESTful** usando **Spring MVC**.  
  Ele vem com o **Tomcat** embutido como servidor padrão, o que facilita a inicialização e execução da aplicação sem necessidade de instalação de servidores externos.

---

- **6: Spring for RabbitMQ:**  
  Fornece uma plataforma para que aplicações possam **enviar** e **receber mensagens** através do **RabbitMQ**,  
  permitindo comunicação assíncrona entre sistemas de forma segura e eficiente.

  🐇 **RabbitMQ** é um broker de mensagens usado para **fila de tarefas**, processamento assíncrono e **integração entre microsserviços**.

---

- **7: Testcontainers:**  
  Biblioteca para testes que permite criar **instâncias temporárias e descartáveis** de serviços como bancos de dados, navegadores Selenium e outros componentes que possam rodar dentro de **containers Docker**.  
  Ideal para **testes de integração**, garantindo que os testes sejam executados em ambientes consistentes.

---
Link do spring boot: [Calulo Folha de Pagamento](https://start.spring.io/#!type=maven-project&language=java&platformVersion=3.5.5&packaging=jar&jvmVersion=24&groupId=com.Lab01Grupo02&artifactId=calculo-folha-de-pagamento&name=calculo-folha-de-pagamento&description=Projeto%20para%20gerir%20a%20folha%20de%20pagamento%20de%20funcionarios%20dentro%20de%20uma%20empresa.&packageName=com.Lab01Grupo02.calculo-folha-de-pagamento&dependencies=devtools,lombok,data-jpa,amqp,testcontainers,web,security)

























