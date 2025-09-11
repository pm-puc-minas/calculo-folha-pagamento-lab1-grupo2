# Documentação da Modelagem do Frontend

## Tela de Login
Porta de entrada do sistema, responsável pela autenticação do usuário.
- Tela simples onde o usuário se identifica para acessar o site.
- **Componentes principais:**
  - Campo para número de matrícula/e-mail.
  - Campo para senha.
- **Interações:**
  - O usuário preenche os campos e, após clicar em "Entrar" ou pressionar Enter, o frontend envia as credenciais para o endpoint de autenticação do backend.
  - Se a autenticação for bem-sucedida, o usuário é redirecionado para a `Tela Inicial`. Caso ocorra um erro, uma mensagem de erro é exibida na tela.

## Tela Inicial (RH)
A tela principal do sistema após o login, servindo como um painel central para todas as funcionalidades. Apresenta dados gerais, mas algumas informações e permissões могут variar.
- **Componentes principais:**
  - Barra de navegação com: `Gerenciamento de Funcionários`, `Folha de Pagamento` e `Relatórios`.
  - Botão "Logout".
  - Outras informações visuais (não é necessário implementar funcionalidades adicionais aqui).
- **Interações:**
  - O usuário clica nos links de navegação para acessar diferentes áreas do sistema.
  - Ao clicar no botão "Logout", ele é desconectado e redirecionado para a tela de login.

## Tela Inicial (Demais Funcionários)
A tela principal do sistema para usuários não-RH.
- **Componentes principais:**
  - Barra de navegação com: `Meu Perfil`, `Folha de Pagamento` e `Relatórios`.
  - Botão "Logout".
- **Interações:**
  - O usuário clica nos links de navegação para acessar suas informações pessoais e relatórios.
  - Ao clicar no botão "Logout", ele é desconectado e redirecionado para a tela de login.

## Gerenciamento de Funcionários (RH)
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

## Gerenciamento de Funcionário (Perfil Individual)
Permite que o usuário modifique dados não críticos em seu próprio perfil.
- **Componentes principais:**
  - Botão de "Editar".
- **Interações:**
  - Ao clicar em "Editar", é possível alterar algumas informações não relevantes para o sistema (ex: telefone de contato, endereço).

## Formulário de Funcionário
Tela dedicada à entrada e modificação de dados de um funcionário.
- **Componentes principais:**
  - Campos de texto para: Nome, CPF, Endereço, Cargo, Salário Base, entre outros.
  - Botão "Salvar".
  - Botão "Cancelar".
- **Interações:**
  - O usuário preenche ou edita os dados e clica em "Salvar".
  - O frontend envia os dados do formulário para o endpoint correspondente no backend.
  - Ao clicar em "Cancelar", o usuário retorna à tela anterior sem salvar as alterações.

## Folha de Pagamento
Tela focada na operação principal do sistema: o cálculo da folha de pagamento.
- **Componentes principais:**
  - Seletores de mês e ano para definir o período de competência.
  - Botão "Calcular Folha de Pagamento".
  - Área para exibição de status (ex.: "Calculando...", "Cálculo concluído com sucesso.").
- **Interações:**
  - O usuário seleciona o mês e o ano desejados.
  - Ao clicar em "Calcular Folha de Pagamento", uma chamada é feita ao backend para processar os pagamentos.
  - Após a conclusão, o usuário pode ser redirecionado para os `Relatórios` para ver o resultado detalhado.

## Relatórios
Esta tela permite a visualização dos resultados dos cálculos da folha de pagamento.
- **Componentes principais:**
  - Lista das folhas de pagamento já processadas, agrupadas por período.
  - Filtros por período ou por funcionário.
  - Opção para visualizar o contracheque individual de um funcionário.
- **Interações:**
  - O usuário seleciona uma folha de pagamento da lista para ver os detalhes.
  - O usuário pode aplicar filtros para encontrar pagamentos específicos.
  - Pode-se verificar o contracheque, que exibirá o salário bruto, descontos e o salário líquido.
 
# Implementação Extra

## Tela de Log
Ela permite ao Gerente conferir os logs de alterações, emissões de folha de pagamento, alteração de dados de usuário, entre outras informações executadas pelo RH.
- **Componentes principais:**
  ...
- **Interações:**
  ...
