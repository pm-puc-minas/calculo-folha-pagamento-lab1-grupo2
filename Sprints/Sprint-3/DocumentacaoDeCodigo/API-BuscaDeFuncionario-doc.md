# 📘 Documentação da API de Busca de Funcionários

Este documento detalha a arquitetura e o uso dos componentes da API de busca de funcionários.
A arquitetura segue o padrão de **3 camadas**:

* **Controller (API)**
* **Service (Lógica de Negócio)**
* **Repository (Banco de Dados)**
  Com uma camada adicional de **Config** para segurança.

---

## 1. `SecurityConfig.java` (A Camada de Segurança)

**Arquivo:** `com.Lab01Grupo02.calculo_folha_de_pagamento.config.SecurityConfig.java`

### 🧩 Responsabilidade

Esta classe é a **porta de entrada** da aplicação. Sua única responsabilidade é **configurar o Spring Security**.

Por padrão, o Spring Security bloqueia todas as rotas da API com uma tela de login (causando o erro `401 Unauthorized`).
Esta classe sobrescreve esse comportamento.

### ⚙️ Como Funciona

* `@Configuration` e `@EnableWebSecurity`: informam ao Spring que esta é uma classe de configuração de segurança.
* `securityFilterChain(HttpSecurity http)`: define as regras do filtro de segurança.
* `.csrf(csrf -> csrf.disable())`: desabilita a proteção CSRF (comum e seguro para APIs REST).
* `.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())`: permite todas as requisições sem autenticação.

---

## 2. `FuncionarioController.java` (A Camada de API – O “Garçom”)

**Arquivo:** `com.Lab01Grupo02.calculo_folha_de_pagamento.controller.FuncionarioController.java`

### 🧩 Responsabilidade

O **Controller** é a camada que escuta a web.
Suas funções são:

* Definir as **rotas/endpoints** da API.
* Receber as **requisições HTTP** (ex: GET do Postman).
* Delegar o trabalho para a camada de serviço (`FuncionarioService`).
* Converter a resposta Java em **JSON**.

### ⚙️ Como Funciona

* `@RestController`: converte automaticamente os objetos Java de retorno em JSON.
* `@RequestMapping("/api/funcionarios")`: define o prefixo de URL.
* `@Autowired`: injeta uma instância do `FuncionarioService`.
* `@GetMapping`: mapeia uma requisição HTTP GET para um método.
* `ResponseEntity.ok(funcionario)`: retorna o objeto Java em uma resposta HTTP `200 OK`.

---

## 3. `FuncionarioService.java` (A Camada de Serviço – O “Cérebro”)

**Arquivo:** `com.Lab01Grupo02.calculo_folha_de_pagamento.service.FuncionarioService.java`

### 🧩 Responsabilidade

Camada central da **lógica de negócio**.
Não lida com JSON nem URLs — apenas executa as tarefas requisitadas pelo Controller.

Funções principais:

* Receber pedidos do Controller.
* Buscar dados no banco via `FuncionarioRepository`.
* Aplicar regras de negócio (como limpeza de CPF e tratamento de exceções).

### ⚙️ Lógica dos Métodos

#### 🔹 `buscarPorMatricula(Integer matricula)`

1. Chama `funcionarioRepository.findById(matricula)`.
2. O repositório retorna um `Optional<Funcionario>`.
3. Usa `.orElseThrow(...)`:

   * Retorna o funcionário se existir.
   * Caso contrário, lança `ResourceNotFoundException`.
4. A exceção é capturada pelo `GlobalExceptionHandler` e transformada em JSON de erro `404`.

---

#### 🔹 `buscarPorCpf(String cpf)`

1. **Validação:** verifica se o CPF é nulo ou vazio → lança `ResourceNotFoundException`.
2. **Limpeza:** remove `.` e `-` com `cpf.replaceAll("[.-]", "")`.
3. **Busca:** chama `findByCpf(cpfLimpo)` e usa `.orElseThrow(...)` para erro 404 se não encontrado.

---

#### 🔹 `buscarPorNome(String nome)`

1. Se o nome for nulo ou vazio → retorna lista vazia `List.of()`.
2. Caso contrário → retorna o resultado do repositório (lista de funcionários).
3. Se nenhum resultado for encontrado → retorna `[]` (sucesso, não erro).

---

## 4. 🧭 Guia da API: Como Usar as Rotas (Postman)

**Pré-requisito:** Servidor rodando na porta `9090`.

---

### 🧩 Rota 1: Buscar Funcionário por Matrícula

**Método:** `GET`
**URL:** `http://localhost:9090/api/funcionarios/{matricula}`
**Parâmetro:** Variável de Caminho (Path Variable)

#### ✅ Exemplo (Sucesso)

**Requisição:**
`GET http://localhost:9090/api/funcionarios/101`

**Resposta (200 OK):**

```json
{
  "nome": "Ana Silva",
  "cpf": "11122233344",
  "dataNascimento": "1990-05-15",
  "cargo": "Analista de RH",
  "dataAdmissao": "2018-03-01",
  "salarioBruto": 5500.00,
  "grauInsalubridade": "Nenhum",
  "cargaHorariaSemanal": 40,
  "possuiPericulosidade": true,
  "dependentes": [
    {
      "nome": "Lucas Silva",
      "cpf": "44455566677",
      "dataNascimento": "2015-01-10",
      "parentesco": "Filho(a)"
    }
  ]
}
```

#### ❌ Exemplo (Erro – Não Encontrado)

**Requisição:**
`GET http://localhost:9090/api/funcionarios/999`

**Resposta (404 Not Found):**

```json
{
  "statusCode": 404,
  "message": "Funcionário não encontrado com a matrícula: 999",
  "details": "uri=/api/funcionarios/999",
  "timestamp": "..."
}
```

---

### 🧩 Rota 2: Buscar Funcionário por CPF

**Método:** `GET`
**URL:** `http://localhost:9090/api/funcionarios/cpf`
**Parâmetro:** Parâmetro de Consulta (Query Param)

#### ✅ Exemplo (Sucesso)

**Requisição:**
`GET http://localhost:9090/api/funcionarios/cpf?valor=11122233344`

**Resposta (200 OK):**
*(Mesma resposta JSON da Ana Silva acima)*

#### ❌ Exemplo (Erro – Não Encontrado)

**Requisição:**
`GET http://localhost:9090/api/funcionarios/cpf?valor=000`

**Resposta (404 Not Found):**

```json
{
  "statusCode": 404,
  "message": "Funcionário não encontrado com o CPF informado.",
  "details": "uri=/api/funcionarios/cpf",
  "timestamp": "..."
}
```

---

### 🧩 Rota 3: Buscar Funcionário por Nome

**Método:** `GET`
**URL:** `http://localhost:9090/api/funcionarios/nome`
**Parâmetro:** Parâmetro de Consulta (Query Param)

#### ✅ Exemplo (Sucesso)

**Requisição:**
`GET http://localhost:9090/api/funcionarios/nome?termo=Ana`

**Resposta (200 OK):**

```json
[
  {
    "nome": "Ana Silva",
    "cpf": "11122233344",
    "dataNascimento": "1990-05-15",
    "cargo": "Analista de RH"
  }
]
```

#### ✅ Exemplo (Sucesso – Nenhum Encontrado)

**Requisição:**
`GET http://localhost:9090/api/funcionarios/nome?termo=Zelia`

**Resposta (200 OK):**

```json
[]
```
