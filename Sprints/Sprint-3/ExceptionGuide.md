# 🧪 Guia de Testes Manuais — Handlers de Erro (Postman)

Antes de iniciar, **execute sua aplicação Spring Boot**, garantindo que ela esteja rodando em:
👉 `http://localhost:9090`

---

## ⚠️ 1. Erro 404 — `ResourceNotFoundException`

### 🧭 Objetivo

Verificar o tratamento de erros quando um funcionário inexistente é buscado.

### 🧾 Passos

1. Abra o **Postman** e crie uma nova requisição.
2. **Método:** `GET`
3. **URL:** `http://localhost:9090/api/funcionarios/99999`
   *(Use uma matrícula que **não exista** no banco de dados.)*
4. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `404 Not Found`
* **Body (JSON):**

  ```json
  {
      "statusCode": 404,
      "message": "Funcionário não encontrado com a matrícula: 99999",
      "details": "uri=/api/funcionarios/99999",
      "timestamp": "..."
  }
  ```

---

## 🚫 2. Erro 400 — `MethodArgumentNotValidException` (Falha na Validação)

### 🧭 Objetivo

Testar a validação dos campos obrigatórios com a anotação `@Valid`.

### 🧾 Passos

1. Abra o **Postman**.
2. **Método:** `POST`
3. **URL:** `http://localhost:9090/api/folhapagamento`
4. Vá até a aba **Body** → selecione **raw** → escolha o tipo **JSON**.
5. Cole o seguinte corpo (JSON inválido):

   ```json
   {
       "matricula": null,
       "mesReferencia": "2025-11-01",
       "diasFalta": -5
   }
   ```
6. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

  ```json
  {
      "statusCode": 400,
      "message": "Erro de validação. Verifique os campos da requisição.",
      "details": "uri=/api/folhapagamento; Erros=[ 'matricula': A matrícula não pode ser nula, 'diasFalta': Dias de falta não pode ser um valor negativo ]",
      "timestamp": "..."
  }
  ```

> 💡 *A ordem dos erros em `details` pode variar.*

---

## 🧾 3. Erro 400 — `HttpMessageNotReadableException` (JSON Malformado)

### 🧭 Objetivo

Validar o tratamento de erros para JSONs com erro de sintaxe.

### 🧾 Passos

1. Mantenha a requisição **POST** para `http://localhost:9090/api/folhapagamento`.
2. No **Body**, selecione **raw → JSON**.
3. Cole o JSON abaixo, contendo uma vírgula extra:

   ```json
   {
       "matricula": 123,
       "mesReferencia": "2025-11-01",
       "diasFalta": 2,  <-- vírgula extra!
   }
   ```
4. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

  ```json
  {
      "statusCode": 400,
      "message": "Requisição JSON mal formatada ou inválida.",
      "details": "uri=/api/folhapagamento",
      "timestamp": "..."
  }
  ```

---

## ⚙️ 4. Erro 400 — `MethodArgumentNotValidException` (Valor Negativo)

### 🧭 Objetivo

Confirmar que a anotação `@Min(0)` do DTO `GerarFolhaRequest` está validando corretamente e que o `GlobalExceptionHandler` captura o erro.

### 🧾 Passos

1. Abra o **Postman**.

2. **Método:** `POST`

3. **URL:** `http://localhost:9090/api/folhapagamento`

4. Vá até **Body → raw → JSON**.

5. Cole o JSON com valor negativo:

   ```json
   {
       "matricula": 1,
       "mesReferencia": "2025-11-01",
       "diasFalta": -5
   }
   ```

   > 🔸 *Use uma matrícula existente — o erro será validado antes da busca no banco.*

6. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

  ```json
  {
      "statusCode": 400,
      "message": "Erro de validação. Verifique os campos da requisição.",
      "details": "uri=/api/folhapagamento; Erros=[ 'diasFalta': Dias de falta não pode ser um valor negativo ]",
      "timestamp": "..."
  }
  ```

> 💬 *A mensagem em `details` será exatamente a definida na anotação `@Min` do DTO.*

---

## 🔴 5. Erro 400 — `InvalidDataException` (Dados Inválidos - Criação)

### 🧭 Objetivo

Testar a validação de dados inválidos ao criar um novo funcionário.

### 🧾 Passos (Exemplo 1: Nome vazio)

1. Abra o **Postman**.
2. **Método:** `POST`
3. **URL:** `http://localhost:9090/api/funcionarios`
4. Vá até **Body → raw → JSON**.
5. Cole o seguinte JSON:

   ```json
   {
       "nome": "",
       "cpf": "12345678901",
       "dataNascimento": "1990-01-01",
       "cargo": "Desenvolvedor",
       "dataAdmissao": "2024-01-01",
       "salarioBruto": 5000.00,
       "cargaHorariaSemanal": 40,
       "grauInsalubridade": "NENHUM",
       "possuiPericulosidade": false
   }
   ```
6. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

  ```json
  {
      "statusCode": 400,
      "message": "O nome do funcionário é obrigatório.",
      "details": "uri=/api/funcionarios",
      "timestamp": "..."
  }
  ```

---
## 🔴 6. Erro 400 — `InvalidDataException` (Dados Inválidos - Edição)

### 🧭 Objetivo

Testar a validação de dados inválidos ao atualizar um funcionário existente.

### 🧾 Passos (Exemplo: Data de admissão antes do nascimento)

1. Abra o **Postman**.
2. **Método:** `PUT`
3. **URL:** `http://localhost:9090/api/funcionarios/1`
   *(Use uma matrícula existente no banco de dados.)*
4. Vá até **Body → raw → JSON**.
5. Cole o seguinte JSON:

   ```json
   {
       "nome": "Roberto Alves",
       "cpf": "98765432100",
       "dataNascimento": "1995-01-01",
       "cargo": "Coordenador",
       "dataAdmissao": "1990-01-01",
       "salarioBruto": 7000.00,
       "cargaHorariaSemanal": 40,
       "grauInsalubridade": "NENHUM",
       "possuiPericulosidade": false
   }
   ```
6. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

  ```json
  {
      "statusCode": 400,
      "message": "A data de admissão não pode ser anterior à data de nascimento.",
      "details": "uri=/api/funcionarios/1",
      "timestamp": "..."
  }
  ```


---

## ⚠️ 7. Erro 409 — `DuplicateCpfException` (CPF Duplicado - Criação)

### 🧭 Objetivo

Verificar o tratamento de erro quando se tenta criar um funcionário com CPF já cadastrado.

### 🧾 Passos

1. **Pré-requisito:** Certifique-se de que existe um funcionário com CPF `12345678901` no banco de dados. Se não existir, crie um primeiro.

2. Abra o **Postman**.
3. **Método:** `POST`
4. **URL:** `http://localhost:9090/api/funcionarios`
5. Vá até **Body → raw → JSON**.
6. Cole o seguinte JSON (usando o mesmo CPF existente):

   ```json
   {
       "nome": "Teste Duplicado",
       "cpf": "12345678901",
       "dataNascimento": "1988-05-15",
       "cargo": "Analista",
       "dataAdmissao": "2024-01-01",
       "salarioBruto": 4000.00,
       "cargaHorariaSemanal": 40,
       "grauInsalubridade": "NENHUM",
       "possuiPericulosidade": false
   }
   ```
7. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `409 Conflict`
* **Body (JSON):**

  ```json
  {
      "statusCode": 409,
      "message": "Já existe um funcionário cadastrado com o CPF: 12345678901",
      "details": "uri=/api/funcionarios",
      "timestamp": "..."
  }
  ```

---

## ⚠️ 8. Erro 409 — `DuplicateCpfException` (CPF Duplicado - Edição)

### 🧭 Objetivo

Verificar o tratamento de erro quando se tenta atualizar um funcionário usando um CPF que já pertence a outro funcionário.

### 🧾 Passos

1. **Pré-requisito:** Certifique-se de que existem dois funcionários:
   - Funcionário com matrícula `1` e CPF `11111111111`
   - Funcionário com matrícula `2` e CPF `22222222222`

2. Abra o **Postman**.
3. **Método:** `PUT`
4. **URL:** `http://localhost:9090/api/funcionarios/1`
5. Vá até **Body → raw → JSON**.
6. Cole o seguinte JSON (tentando alterar o CPF do funcionário 1 para o CPF do funcionário 2):

   ```json
   {
       "nome": "Funcionário Atualizado",
       "cpf": "22222222222",
       "dataNascimento": "1990-01-01",
       "cargo": "Desenvolvedor Senior",
       "dataAdmissao": "2023-01-01",
       "salarioBruto": 9000.00,
       "cargaHorariaSemanal": 40,
       "grauInsalubridade": "NENHUM",
       "possuiPericulosidade": false
   }
   ```
7. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `409 Conflict`
* **Body (JSON):**

  ```json
  {
      "statusCode": 409,
      "message": "Já existe outro funcionário cadastrado com o CPF: 22222222222",
      "details": "uri=/api/funcionarios/1",
      "timestamp": "..."
  }
  ```
  ---

## ⚠️ 1. Erro 404 — ResourceNotFoundException (Folha de Pagamento Não Encontrada)

### 🧭 Objetivo

Verificar o tratamento de erros quando uma folha de pagamento inexistente é buscada, deletada ou atualizada.

### 🧾 Passos

1. Abra o **Postman** e crie uma nova requisição.
2. **Método:** `GET`
3. **URL:** `http://localhost:9090/api/folhapagamento/999 (ID inexistente)`
4. Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `404 Not Found`
* **Body (JSON):**

```json
{
    "statusCode": 404,
    "message": "Folha de pagamento com id 999 não encontrada.",
    "details": "uri=/api/folhapagamento/999",
    "timestamp": "..."
}
```

> 💡 *Observação: Mesma exceção será lançada para `DELETE` e `PATCH` com ID inexistente, mudando apenas a URL:*

**DELETE:** `http://localhost:9090/api/folhapagamento/999`
**PATCH:** `http://localhost:9090/api/folhapagamento/999/dias-falta`

---

## ⚠️ 2. Erro 400 — `MethodArgumentNotValidException` (Falha na Validação DTO)

### 🧭 Objetivo

Testar a validação de campos obrigatórios ou valores inválidos no DTO **GerarFolhaRequest**.

### 🧾 Passos

1.Abra o Postman.
2. **Método:** `POST`
3. **URL:** `http://localhost:9090/api/folhapagamento`
4. Cole o JSON inválido:

```json
{
    "matricula": null,
    "mesReferencia": "2025-11-01",
    "diasFalta": -5
}
```

Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

```json
{
    "statusCode": 400,
    "message": "Erro de validação. Verifique os campos da requisição.",
    "details": "uri=/api/folhapagamento; Erros=[ 'matricula': A matrícula não pode ser nula, 'diasFalta': Dias de falta não pode ser negativo ]",
    "timestamp": "..."
}
```

> 💡 *A ordem dos erros em `details` pode variar.*

---

## ⚠️ 3. Erro 400 — `HttpMessageNotReadableException` (JSON Malformado)

### 🧭 Objetivo

Testar tratamento de erro para JSON com sintaxe incorreta.

### 🧾 Passos

1. Requisição **POST** para `http://localhost:9090/api/folhapagamento`
2. Cole o JSON abaixo (vírgula extra):

```json
{
    "matricula": 123,
    "mesReferencia": "2025-11-01",
    "diasFalta": 2,  <-- vírgula extra!
}
```

Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** `400 Bad Request`
* **Body (JSON):**

```json
{
    "statusCode": 400,
    "message": "Requisição JSON mal formatada ou inválida.",
    "details": "uri=/api/folhapagamento",
    "timestamp": "..."
}
```

---


## ⚠️ 4. Erro 404 — `ResourceNotFoundException` (Funcionário Não Encontrado)

### 🧭 Objetivo

Testar a exceção lançada quando se tenta gerar ou atualizar uma folha para uma matrícula inexistente.

### 🧾 Passos

Requisição **POST** para `http://localhost:9090/api/folhapagamento`

**Exemplo:**

```json
{
    "matricula": 999,
    "mesReferencia": "2025-11-01",
    "diasFalta": 2
}
```


Clique em **Send**.

### ✅ Resultado Esperado

* **Status:** 404 Not Found
* **Body (JSON):**

```json
{
    "statusCode": 404,
    "message": "Funcionário com matrícula 999 não encontrado.",
    "details": "uri=/api/folhapagamento",
    "timestamp": "..."
}
```

