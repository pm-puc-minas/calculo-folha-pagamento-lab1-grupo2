
# 📄 Documentação Técnica: Implementação da Folha de Pagamento

Este documento descreve a arquitetura final implementada para a funcionalidade de **geração e atualização da folha de pagamento**, com foco em como o tratamento de datas e a lógica **"UPSERT" (Update/Insert)** funcionam.

---

## 1. Funcionalidade Principal: Endpoint UPSERT

O núcleo da funcionalidade reside no endpoint **`POST /api/folhapagamento`**.  
Ele foi projetado para:

- Criar uma nova folha se ela **não existir**.  
- Atualizar uma folha existente se ela **já existir** para a matrícula e mês de referência informados.

### Lógica de Implementação

```java
FolhaDePagamento folha = folhaPagamentoRepository
    .findByMatriculaAndMesReferencia(request.matricula(), request.mesReferencia())
    .orElse(new FolhaDePagamento());

folha.setDiasFalta(request.diasFalta());
// ... (outros cálculos)
folhaPagamentoRepository.save(folha);
````

Se o `findBy` encontrar um registro, ele será atualizado.
Se não encontrar, o `.orElse()` cria um novo objeto que será persistido.

---

## 2. Componentes da Implementação

Para que essa lógica funcione corretamente — especialmente com o banco de dados **SQLite** — vários componentes foram implementados para garantir a manipulação consistente de **datas (`LocalDate`)**.

---

### 🧩 A. DTO (GerarFolhaRequest) — A Entrada

O **DTO `GerarFolhaRequest`** utiliza anotações do **Jackson** para garantir que o JSON de entrada seja interpretado corretamente.

* **Campo:** `LocalDate mesReferencia`
  Mantido como `LocalDate` em Java.

* **Anotação:**

  ```java
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  ```

  Força o Jackson a **ler e escrever** a data no formato `AAAA-MM-DD`, evitando conversões automáticas para timestamp numérico.

---

### ⚙️ B. Conversor (LocalDateAttributeConverter) — A Camada de Persistência

Foi identificado que o driver JDBC do SQLite, em conjunto com o dialeto Hibernate, **não converte `LocalDate` de forma confiável**, tendendo a usar timestamps numéricos (ex: `1759287600000`).

Para resolver isso, foi implementado um **`AttributeConverter`** do JPA.

#### Função

O `LocalDateAttributeConverter` instrui o Hibernate a converter corretamente o tipo `LocalDate`:

* **`convertToDatabaseColumn`** → converte `LocalDate` em `String ("yyyy-MM-dd")` antes de salvar.
* **`convertToEntityAttribute`** → converte a `String` lida do banco de volta em `LocalDate`.

---

### 🧱 C. Entidades (@Convert) — Aplicação do Conversor

O `AttributeConverter` foi aplicado em todos os campos `LocalDate` das entidades, garantindo consistência total na leitura e escrita no banco de dados:

* **FolhaDePagamento**

  ```java
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate mesReferencia;
  ```

* **Pessoa**

  ```java
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate dataNascimento;
  ```

* **Funcionario**

  ```java
  @Convert(converter = LocalDateAttributeConverter.class)
  private LocalDate dataAdmissao;
  ```

---

### ⚙️ D. Configuração (`application.properties`)

As seguintes propriedades são mantidas para dar suporte à arquitetura:

```properties
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jackson.serialization.write-dates-as-timestamps=false
```

* **SQLiteDialect:** Garante compatibilidade entre o Hibernate e o SQLite.
* **write-dates-as-timestamps=false:** Assegura que as respostas JSON usem strings de data (`yyyy-MM-dd`) em vez de timestamps numéricos.

---

## 3. Resultado e Próximos Passos

### ✅ Resultado Atual

Com esta implementação:

* O JSON é lido e enviado no formato `yyyy-MM-dd`.
* O `AttributeConverter` garante SQL correto (ex: `WHERE MesReferencia = '2025-10-01'`).
* A lógica **UPSERT** funciona corretamente, atualizando `diasFalta` em registros existentes.
* Todos os campos de data (`DataNascimento`, `DataAdmissao`, etc.) são lidos do banco sem erros.

---

### 🚀 Próximos Passos

A funcionalidade de salvar `diasFalta` está **completa**.
O próximo passo é modificar o serviço de cálculo (`ICalculadora`) para que ele utilize o valor de `diasFalta` ao **recalcular o salário bruto e líquido** do funcionário, aplicando os **descontos apropriados**.


