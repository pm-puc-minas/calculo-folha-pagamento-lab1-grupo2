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
