# 📄 Documentação Geral de Responsabilidades

Especificação de responsabilidade de cada arquivo e como eles trabalham juntos no fluxo de tratamento de erros do sistema.

---

## 🔁 Fluxo de um Erro **404 (Não Encontrado)**

1. O **`FuncionarioService`** tenta buscar um funcionário por ID (ex: `123`) no banco.
2. O **`FuncionarioRepository`** retorna um `Optional.empty()`.
3. O **`FuncionarioService`** detecta o `empty()` e lança uma nova exceção:  
   ```java
   throw new ResourceNotFoundException("Funcionário 123 não encontrado");
   ```
4. A exceção sobe até o **`FuncionarioController`**.
5. O **`GlobalExceptionHandler`** (por causa do `@ControllerAdvice`) intercepta a exceção **antes** que ela chegue ao Spring Boot.
6. Ele encontra o método:
   ```java
   @ExceptionHandler(ResourceNotFoundException.class)
   ```
   que é compatível com a exceção lançada.
7. O handler cria um objeto `new ErrorResponse(...)` preenchendo-o com os dados relevantes:
   - Status: `404`
   - Mensagem: `"Funcionário 123 não encontrado"`
8. O handler retorna um `ResponseEntity` contendo o `ErrorResponse` no corpo (`body`) e o status `HttpStatus.NOT_FOUND`.
9. O **Spring Boot** converte o objeto `ErrorResponse` em **JSON** e o envia ao cliente.

---

## 🗂️ Arquivos e Responsabilidades

| Nome do Arquivo | Responsabilidade Principal | Detalhes da Relação |
|------------------|-----------------------------|----------------------|
| **`ErrorResponse.java`** | 🧱 **Definir o Contrato (Estrutura)** | É uma classe DTO (ou `record`) "passiva". Define os campos do JSON de erro. Não possui lógica — apenas representa os dados. |
| **`ResourceNotFoundException.java`** | 🚨 **Sinalizar um Problema** | É o "sinalizador". Sua única responsabilidade é ser **lançada** pela camada de Serviço (`FuncionarioService`) para indicar que um recurso não foi encontrado. |
| **`GlobalExceptionHandler.java`** | 🌐 **Formatar a Resposta** | É o "tradutor". Captura exceções (como a `ResourceNotFoundException`) e as **traduz** para respostas HTTP amigáveis, usando `ErrorResponse` como modelo para o JSON final. |

---

## 🧩 Resumo do Fluxo

```mermaid
sequenceDiagram
    participant Controller
    participant Service
    participant Repository
    participant Handler
    participant Cliente

    Controller->>Service: buscarFuncionarioPorId(123)
    Service->>Repository: findById(123)
    Repository-->>Service: Optional.empty()
    Service-->>Service: throw new ResourceNotFoundException()
    Service-->>Controller: Exceção lançada
    Controller-->>Handler: Intercepta a exceção
    Handler-->>Handler: Cria ErrorResponse(404, "Funcionário 123 não encontrado")
    Handler-->>Cliente: Retorna JSON (status 404)
```

# 🧾 Documentação do Commit [c0069de](https://github.com/pm-puc-minas/calculo-folha-pagamento-lab1-grupo2/commit/c0069de1c130ba4eb02ef6650e631ca88361a79d)


## 🧩 Maven

* Adicionado o **`spring-boot-starter-validation`** às dependências do projeto.

---

## 📥 GerarFolhaRequest

* Adicionadas duas anotações **`@NotNull`** para garantir que os campos **`matricula`** e **`mesReferencia`** não sejam omitidos no JSON de requisição.
* Incluída a anotação **`@Min(0)`**, que assegura que valores negativos não sejam informados.

---

## 🧠 FolhaDePagamentoController

* No método **`gerarOuAtualizarFolhaPagamento`**, foi adicionada a anotação **`@Valid`** para ativar a validação automática dos campos da requisição.

---

## ⚙️ GlobalExceptionHandler

* Adicionados **handlers** à classe para capturar a exceção **`MethodArgumentNotValidException`**, lançada pelo **`@Valid`**.
* Essa exceção é tratada e transformada em um objeto **`ErroResponse`**, retornando mensagens de erro mais claras e padronizadas.


---
