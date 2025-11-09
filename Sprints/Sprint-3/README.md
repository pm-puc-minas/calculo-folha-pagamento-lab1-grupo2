# 🚀 README - Sprint 3

Este documento detalha todas as rotas da API implementadas, com foco nas operações de **consulta de funcionários** e na **geração/atualização da folha de pagamento**.

---

## 🌐 URL Base da API
```

[http://localhost:9090](http://localhost:9090)

````

---

## 1️⃣ API de Funcionários (`/api/funcionarios`)

Este conjunto de endpoints gerencia a **consulta e atualização de dados dos funcionários**.

---

### 1.1. 🔍 Buscar Todos os Funcionários

Busca uma lista de todos os funcionários cadastrados no banco de dados.

**Método:** `GET`  
**URL:** `http://localhost:9090/api/funcionarios`  
**Parâmetros:** Nenhum

#### ✅ Exemplo de Resposta (Sucesso - 200 OK)
```json
[
  {
    "nome": "Ana Silva",
    "cpf": "11122233344",
    "dataNascimento": "1990-05-15",
    "cargo": "Analista de RH"
  },
  {
    "nome": "Carlos Pereira",
    "cpf": "55566677788",
    "dataNascimento": "1985-10-20",
    "cargo": "Desenvolvedor"
  }
]
````

#### ✅ Resposta (Banco Vazio - 200 OK)

```json
[]
```

---

### 1.2. 🧾 Buscar Funcionário por Matrícula

Busca um funcionário específico pela sua matrícula (ID).

**Método:** `GET`
**URL:** `http://localhost:9090/api/funcionarios/{matricula}`
**Parâmetros:** `{matricula}` (Path Variable)

#### ✅ Exemplo de Requisição

```
GET http://localhost:9090/api/funcionarios/101
```

#### ✅ Resposta (Sucesso - 200 OK)

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
  "dependentes": []
}
```

#### ❌ Resposta (Erro - 404 Not Found)

```json
{
  "statusCode": 404,
  "message": "Funcionário não encontrado com a matrícula: 999",
  "details": "uri=/api/funcionarios/999",
  "timestamp": "2025-11-06T17:30:01.12345"
}
```

---

### 1.3. 🪪 Buscar Funcionário por CPF

Busca um funcionário específico pelo seu CPF.

**Método:** `GET`
**URL:** `http://localhost:9090/api/funcionarios/cpf`
**Parâmetros:** `?valor={cpf}` (Query Param)

#### ✅ Exemplo de Requisição

```
GET http://localhost:9090/api/funcionarios/cpf?valor=111.222.333-44
```

#### ✅ Resposta (Sucesso - 200 OK)

*(Retorna o mesmo JSON do funcionário "Ana Silva" acima)*

#### ❌ Resposta (Erro - 404 Not Found)

```json
{
  "statusCode": 404,
  "message": "Funcionário não encontrado com o CPF informado.",
  "details": "uri=/api/funcionarios/cpf",
  "timestamp": "2025-11-06T17:31:01.12345"
}
```

---

### 1.4. 🔠 Buscar Funcionário por Nome

Busca uma lista de funcionários que contenham o termo pesquisado no nome (não diferencia maiúsculas/minúsculas).

**Método:** `GET`
**URL:** `http://localhost:9090/api/funcionarios/nome`
**Parâmetros:** `?termo={nome}` (Query Param)

#### ✅ Exemplo de Requisição

```
GET http://localhost:9090/api/funcionarios/nome?termo=Ana
```

#### ✅ Resposta (Sucesso - 200 OK)

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

---

### 1.5. ⏱️ Atualizar Carga Horária do Funcionário

Atualiza (parcialmente) a carga horária semanal de um funcionário.

**Método:** `PATCH`
**URL:** `http://localhost:9090/api/funcionarios/{matricula}/carga-horaria`
**Parâmetros:** `{matricula}` (Path Variable)
**Corpo (Body):** JSON

#### ✅ Exemplo de Requisição

```
PATCH http://localhost:9090/api/funcionarios/101/carga-horaria
```

**Corpo:**

```json
{
  "cargaHoraria": 44
}
```

#### ✅ Resposta (Sucesso - 200 OK)

```json
{
  "nome": "Ana Silva",
  "cpf": "11122233344",
  "dataNascimento": "1990-05-15",
  "cargo": "Analista de RH",
  "cargaHorariaSemanal": 44
}
```

---

## 2️⃣ API da Folha de Pagamento (`/api/folhapagamento`)

Este endpoint gerencia a **criação e atualização da folha de pagamento** de um funcionário para um mês específico.

---

### 2.1. 💰 Gerar ou Atualizar Folha de Pagamento (com Dias de Falta)

Implementa uma lógica **"UPSERT"**:

* Se **não existir** uma folha para a matrícula e mês de referência, **uma nova folha é criada**.
* Se **já existir**, **a folha é atualizada** com os novos dados (como diasFalta).

**Método:** `POST`
**URL:** `http://localhost:9090/api/folhapagamento`
**Corpo (Body):** JSON

#### ✅ Exemplo de Requisição

```
POST http://localhost:9090/api/folhapagamento
```

**Corpo:**

```json
{
  "matricula": 101,
  "mesReferencia": "2025-10-01",
  "diasFalta": 2
}
```

#### ✅ Resposta (Sucesso - 200 OK)

```json
{
  "id_Folha": 1,
  "matricula": 101,
  "mesReferencia": "2025-10-01",
  "salarioBruto": 5500.00,
  "totalProvento": 5500.00,
  "totalDesconto": 1000.00,
  "salarioLiquido": 4500.00,
  "diasFalta": 2,
  "itens": [
    {
      "id_Folha": 1,
      "desc": "Salário Base",
      "tipo": "PROVENTO",
      "valor": 5500.00
    },
    {
      "id_Folha": 2,
      "desc": "INSS",
      "tipo": "DESCONTO",
      "valor": 600.00
    },
    {
      "id_Folha": 3,
      "desc": "IRRF",
      "tipo": "DESCONTO",
      "valor": 400.00
    }
  ]
}
```

---
---

### 1.6. ➕ Criar Novo Funcionário

Cria um novo funcionário no sistema com todos os dados necessários.

**Método:** `POST`
**URL:** `http://localhost:9090/api/funcionarios`
**Corpo (Body):** JSON

#### ✅ Exemplo de Requisição

```
POST http://localhost:9090/api/funcionarios
```

**Corpo:**

```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "dataNascimento": "1990-01-15",
  "cargo": "Desenvolvedor",
  "dataAdmissao": "2024-01-10",
  "salarioBruto": 5000.00,
  "cargaHorariaSemanal": 40,
  "grauInsalubridade": "NENHUM",
  "possuiPericulosidade": false
}
```

#### ✅ Resposta (Sucesso - 201 Created)

```json
{
  "idPessoa": 10,
  "nome": "João Silva",
  "cpf": "12345678901",
  "dataNascimento": "1990-01-15",
  "cargo": "Desenvolvedor",
  "dataAdmissao": "2024-01-10",
  "salarioBruto": 5000.00,
  "cargaHorariaSemanal": 40,
  "grauInsalubridade": "NENHUM",
  "possuiPericulosidade": false,
  "dependentes": []
}
```

#### ❌ Resposta (Erro - 400 Bad Request - Dados Inválidos)

```json
{
  "statusCode": 400,
  "message": "O nome do funcionário é obrigatório.",
  "details": "uri=/api/funcionarios",
  "timestamp": "2025-11-08T10:15:30.12345"
}
```

#### ❌ Resposta (Erro - 409 Conflict - CPF Duplicado)

```json
{
  "statusCode": 409,
  "message": "Já existe um funcionário cadastrado com o CPF: 12345678901",
  "details": "uri=/api/funcionarios",
  "timestamp": "2025-11-08T10:15:30.12345"
}
```

---

### 1.7. ✏️ Atualizar Funcionário Existente

Atualiza todos os dados de um funcionário existente.

**Método:** `PUT`
**URL:** `http://localhost:9090/api/funcionarios/{matricula}`
**Parâmetros:** `{matricula}` (Path Variable)
**Corpo (Body):** JSON

#### ✅ Exemplo de Requisição

```
PUT http://localhost:9090/api/funcionarios/10
```

**Corpo:**

```json
{
  "nome": "João Silva Santos",
  "cpf": "12345678901",
  "dataNascimento": "1990-01-15",
  "cargo": "Desenvolvedor Senior",
  "dataAdmissao": "2024-01-10",
  "salarioBruto": 7500.00,
  "cargaHorariaSemanal": 40,
  "grauInsalubridade": "NENHUM",
  "possuiPericulosidade": false
}
```

#### ✅ Resposta (Sucesso - 200 OK)

```json
{
  "idPessoa": 10,
  "nome": "João Silva Santos",
  "cpf": "12345678901",
  "dataNascimento": "1990-01-15",
  "cargo": "Desenvolvedor Senior",
  "dataAdmissao": "2024-01-10",
  "salarioBruto": 7500.00,
  "cargaHorariaSemanal": 40,
  "grauInsalubridade": "NENHUM",
  "possuiPericulosidade": false,
  "dependentes": []
}
```

#### ❌ Resposta (Erro - 404 Not Found - Funcionário Não Encontrado)

```json
{
  "statusCode": 404,
  "message": "Funcionário não encontrado com a matrícula: 99999",
  "details": "uri=/api/funcionarios/99999",
  "timestamp": "2025-11-08T10:15:30.12345"
}
```

#### ❌ Resposta (Erro - 400 Bad Request - Dados Inválidos)

```json
{
  "statusCode": 400,
  "message": "A carga horária semanal deve ser maior que zero.",
  "details": "uri=/api/funcionarios/10",
  "timestamp": "2025-11-08T10:15:30.12345"
}
```

#### ❌ Resposta (Erro - 409 Conflict - CPF Já Cadastrado para Outro Funcionário)

```json
{
  "statusCode": 409,
  "message": "Já existe outro funcionário cadastrado com o CPF: 98765432100",
  "details": "uri=/api/funcionarios/10",
  "timestamp": "2025-11-08T10:15:30.12345"
}
```

---


---

🧩 **Resumo Final:**

| Endpoint                                      | Método | Descrição                           |
| --------------------------------------------- | ------ | ----------------------------------- |
| `/api/funcionarios`                           | GET    | Lista todos os funcionários         |
| `/api/funcionarios/{matricula}`               | GET    | Busca funcionário por matrícula     |
| `/api/funcionarios/cpf?valor=`                | GET    | Busca funcionário por CPF           |
| `/api/funcionarios/nome?termo=`               | GET    | Busca funcionário por nome          |
| `/api/funcionarios/{matricula}/carga-horaria` | PATCH  | Atualiza carga horária semanal      |
| `/api/folhapagamento`                         | POST   | Cria ou atualiza folha de pagamento |
| `/api/funcionarios`                           | POST   | Cria um novo funcionário                   |
| `/api/funcionarios/{matricula}`               | PUT    | Atualiza todos os dados de um funcionário  |


