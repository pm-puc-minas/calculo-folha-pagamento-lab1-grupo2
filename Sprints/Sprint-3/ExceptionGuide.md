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

