## 📚 Sumário

1. [🏛️ Refatoração Arquitetural: Implementação do Padrão Singleton no DAO](#️-refatoração-arquitetural-implementação-do-padrão-singleton-no-dao)
   - [📋 Contexto](#-contexto)
   - [🔄 Comparativo: Antes vs. Depois](#-comparativo-antes-vs-depois)
   - [🛠️ Detalhes da Implementação](#️-detalhes-da-implementação)
   - [💻 Exemplo de Código (Simplificado)](#-exemplo-de-código-simplificado)
   - [🚀 Como Usar](#-como-usar)

2. [🚀 Arquitetura de Cálculo: Pattern Strategy](#-arquitetura-de-cálculo-pattern-strategy)
   - [🎯 Objetivo](#-objetivo)
   - [🧩 Contexto: Problema vs Solução](#-contexto-problema-vs-solução)
   - [🏗️ Estrutura das Classes](#️-estrutura-das-classes)
   - [💻 Exemplo de Código – Contexto (CalculadoraService)](#-exemplo-de-código--contexto-calculadoraservice)
   - [🛠️ Como adicionar um novo cálculo (ex: Taxa Sindical)](#️-como-adicionar-um-novo-cálculo-ex-taxa-sindical)
   - [🏆 Benefícios Obtidos](#-benefícios-obtidos)

3. [🏭 Arquitetura de Criação: Pattern Factory](#-arquitetura-de-criação-pattern-factory)
   - [🎯 Objetivo](#-objetivo-1)
   - [🔄 Fluxo de Dados: O que mudou?](#-fluxo-de-dados-o-que-mudou)
   - [🏗️ Novos Componentes](#️-novos-componentes)
     - [DTO – FuncionarioRequestDTO](#-1-dto--funcionariorequestdto)
     - [Factory – FuncionarioFactory](#-2-factory--funcionariofactory)
   - [💻 Implementação Prática](#-implementação-prática)
   - [🏆 Benefícios Obtidos](#-benefícios-obtidos-1)


---

# 🏛️ Refatoração Arquitetural: Implementação do Padrão Singleton no DAO

## 📋 Contexto
Este documento descreve a refatoração realizada na camada de acesso a dados (`com.Lab01Grupo02.calculo_folha_de_pagamento.DAO`).  
O objetivo foi otimizar o gerenciamento de conexões com o banco de dados SQLite e garantir a integridade da instância de acesso.

---

## 🔄 Comparativo: Antes vs. Depois

A implementação original utilizava uma abordagem de **Classe Utilitária (Static Helper)**, enquanto a nova implementação adota o **Padrão de Projeto Singleton**.  
Abaixo estão as diferenças técnicas fundamentais:

| Característica | ❌ Abordagem Anterior (Estática) | ✅ Nova Abordagem (Singleton) |
|--------------|-----------------------------------|--------------------------------|
| **Controle de Instância** | Inexistente. O construtor padrão era público, permitindo múltiplas instâncias (`new DAO()`). | **Rigoroso.** O construtor é `private` e impede criação externa. |
| **Gerenciamento de Conexão** | **Custoso.** O método `getConnection()` abria uma nova conexão física a cada chamada. | **Eficiente.** A conexão é armazenada e reutilizada. |
| **Padrão de Projeto** | Nenhum (apenas métodos estáticos). | **Singleton (GoF).** Garante instância única e ponto global de acesso. |

---

## 🛠️ Detalhes da Implementação

Para adequar a classe ao padrão **Singleton**, foram aplicadas as seguintes regras de design:

1. **Construtor Privado:** O construtor foi definido como `private` para impedir a criação direta de instâncias (`new DAO()`).
2. **Instância Estática Única:** Foi criado o atributo `private static DAO instance` para armazenar a única instância da classe.
3. **Acesso Global Controlado:** O método `getInstance()` gerencia a criação com *Lazy Initialization*.
4. **Reutilização de Recursos:** A conexão com o SQLite é verificada antes de abrir uma nova. Se já existir uma conexão ativa, ela é reutilizada.

---

## 💻 Exemplo de Código (Simplificado)

```java
public class DAO {
    private static DAO instance;      // Guarda a instância única
    private Connection connection;    // Guarda a conexão para reuso

    // Construtor privado: bloqueia o "new DAO()"
    private DAO() {
        // Configuração inicial
    }

    // Ponto de acesso global (Thread-Safe)
    public static synchronized DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    // Retorna a conexão existente ou cria uma nova se necessário
    public Connection getConnection() {
        if (connection == null) {
            // Criação da conexão com o SQLite
        }
        return connection;
    }
}
````

---

## 🚀 Como Usar

Com essa alteração, toda chamada ao banco de dados na camada **Service** ou **Controller** deve ser atualizada.

### ❌ Como era

```java
Connection conn = DAO.getConnection(); // Criava uma nova conexão sempre
```

### ✅ Como ficou

```java
Connection conn = DAO.getInstance().getConnection(); // Reutiliza a conexão
```

Refatoração baseada nos conceitos de **Design Patterns do Gang of Four (GoF)** — Padrões Criacionais.

---

# 🚀 Arquitetura de Cálculo: Pattern Strategy

## 🎯 Objetivo

Este documento detalha a implementação do **Design Pattern Strategy** na camada de serviço (`CalculadoraService`).  
O objetivo desta refatoração foi **desacoplar a lógica de orquestração da folha de pagamento das regras específicas de cada imposto ou benefício** (INSS, IRRF, FGTS, etc.).

---

## 🧩 Contexto: Problema vs Solução

### ❌ Antes (como não fazer)

Em uma abordagem tradicional e difícil de manter, o método de cálculo de salário seria uma sequência extensa de condicionais:

```java
// Abordagem ruim (alto acoplamento e violação do Open/Closed Principle)
public void calcularFolha(Funcionario f) {
    calcularINSS(f); // Método privado gigante
    calcularFGTS(f); // Outro método privado gigante
    // Se surgir um novo imposto, essa classe precisa ser editada (alto risco de erro).
}
````

Esse modelo gera:

* Alto acoplamento
* Baixa manutenibilidade
* Violação dos princípios do SOLID (especialmente o Open/Closed)

---

### ✅ Depois (Arquitetura atual com Strategy)

Foi adotado o padrão **Strategy**, em que definimos uma *família de algoritmos* (cálculos), encapsulamos cada um em uma classe separada e os tornamos intercambiáveis.

O `CalculadoraService` (Contexto) **não conhece a regra interna de cada imposto ou benefício**, ele apenas sabe que existem módulos de cálculo que devem ser executados sequencialmente.

---

## 🏗️ Estrutura das Classes

A implementação segue contratos claros:

### 🔹 Interface – *Strategy*

**`ICalculoFolha`**

Define o contrato:

```
calcular(Funcionario) -> ItemFolha
```

Garante que todo cálculo:

* Receba um `Funcionario`
* Retorne um `ItemFolha` (linha do holerite)

---

### 🔹 Implementações Concretas – *Concrete Strategies*

**Pacote:** `service.calculos`

Contém classes como:

* `CalculoINSS`
* `CalculoIRRF`
* `CalculoFGTS`
* `CalculoBeneficio`
* `CalculoPericulosidade`

Cada classe possui **apenas a regra de negócio específica** daquele cálculo.

---

### 🔹 Contexto – *Orquestrador*

**`CalculadoraService`**

Responsável por:

* Manter uma lista de estratégias: `List<ICalculoFolha>`
* Orquestrar a execução sequencial de cada cálculo

---

## 💻 Exemplo de Código – Contexto (CalculadoraService)

A mágica acontece no **loop polimórfico**:
O serviço itera sobre a lista de estratégias sem conhecer suas implementações concretas.

```java
@Service
public class CalculadoraService implements ICalculadora {

    private final List<ICalculoFolha> modulosDeCalculo;

    public CalculadoraService() {
        // Injeção das estratégias ativas
        this.modulosDeCalculo = Arrays.asList(
            new CalculoINSS(),
            new CalculoIRRF(),
            new CalculoFGTS(),
            new CalculoPericulosidade()
        );
    }

    @Override
    public List<ItemFolha> calcularFolhaCompleta(Funcionario funcionario, int diasFalta) {
        List<ItemFolha> holerite = new ArrayList<>();

        // ... lógica de faltas ...

        // Execução polimórfica
        for (ICalculoFolha modulo : this.modulosDeCalculo) {
            ItemFolha item = modulo.calcular(funcionario);
            if (item != null) {
                holerite.add(item);
            }
        }

        return holerite;
    }
}
```

---

## 🛠️ Como adicionar um novo cálculo (ex: Taxa Sindical)

Graças ao **Open/Closed Principle (SOLID)**, você não precisa alterar a lógica central do método `calcularFolhaCompleta`.

Siga estes 3 passos:

### ✅ 1. Crie a nova classe

Arquivo: `CalculoSindical.java`
Pacote: `service.calculos`

---

### ✅ 2. Implemente a interface

```java
public class CalculoSindical implements ICalculoFolha {

    @Override
    public ItemFolha calcular(Funcionario f) {
        double valor = /* sua lógica aqui */;
        return new ItemFolha("Taxa Sindical", "DESCONTO", valor);
    }
}
```

---

### ✅ 3. Registre no `CalculadoraService`

Apenas adicione na lista de estratégias:

```java
this.modulosDeCalculo = Arrays.asList(
    new CalculoINSS(),
    new CalculoIRRF(),
    new CalculoFGTS(),
    new CalculoPericulosidade(),
    new CalculoSindical() // <- novo cálculo
);
```

✅ Nenhuma outra modificação é necessária.

---

## 🏆 Benefícios Obtidos

* ✅ **Testabilidade**
  Cada cálculo pode ser testado individualmente (ex: testar apenas o `CalculoINSS`)

* ✅ **Manutenibilidade**
  Cada classe segue o **Single Responsibility Principle**

* ✅ **Extensibilidade**
  Novos cálculos são adicionados via novas classes, sem alterar código já existente

* ✅ **Arquitetura profissional**
  Uso correto de:

  * Strategy Pattern
  * SOLID
  * Baixo acoplamento
  * Alto grau de coesão

---

# 🏭 Arquitetura de Criação: Pattern Factory

## 🎯 Objetivo

Este documento detalha a implementação do **Design Pattern Factory** no fluxo de cadastro de funcionários.  
O objetivo foi **retirar a responsabilidade de instanciação e conversão de dados do `FuncionarioController`**, centralizando regras de criação, conversão de tipos e valores padrão em uma classe dedicada.

---

## 🔄 Fluxo de Dados: O que mudou?

### ❌ Antes (Acoplamento Rígido)

O Controller recebia diretamente a entidade de domínio (`Funcionario`).

**Problemas principais:**
- O Spring tentava converter JSON diretamente para tipos como `LocalDate` e `BigDecimal`, o que frequentemente causava erros de formatação (`400 Bad Request`).
- Regras de negócio como *"Se não informar carga horária, assumir 44h"* ficavam espalhadas pelo Controller ou Service.
- Forte acoplamento entre API e Model.

---

### ✅ Depois (Criação Controlada via Factory)

Foi introduzida uma **camada intermediária (DTO)** e uma **Factory**:

1. A **API recebe**: `FuncionarioRequestDTO` (dados em formato bruto, geralmente `String`)
2. O **Controller delega** para: `FuncionarioFactory`
3. A **Factory retorna**: `Funcionario` (objeto válido, tipado e consistente)

✅ Separação clara entre **camada de transporte** e **camada de domínio**.

---

## 🏗️ Novos Componentes

### 1. DTO – `FuncionarioRequestDTO`

**Pacote:** `controller.dto`

Objeto simples (POJO) usado apenas para transportar dados da requisição (JSON) até o backend.

Características:
- Datas e valores monetários permanecem como `String` (evita falhas imediatas na desserialização).
- Não possui lógica de negócio.
- Contém apenas atributos e getters/setters.

---

### 2. Factory – `FuncionarioFactory`

**Pacote:** `service`

Classe responsável por **fabricar instâncias válidas de `Funcionario`**, encapsulando:

- ✅ Conversão de tipos (`String → LocalDate`, `String → BigDecimal`)
- ✅ Aplicação de **valores padrão**
- ✅ Validação básica e tratamento de erros
- ✅ Centralização de regras de criação

---

## 💻 Implementação Prática

### 📄 `FuncionarioFactory.java`

```java
public class FuncionarioFactory {

    public static Funcionario criarDoDTO(FuncionarioRequestDTO dto) {
        Funcionario f = new Funcionario();

        // ATRIBUTOS DIRETOS
        f.setNome(dto.nome);
        f.setCpf(dto.cpf);

        // CONVERSÃO SEGURA DE DATA
        try {
            f.setDataNascimento(LocalDate.parse(dto.dataNascimento));
        } catch (Exception e) {
            throw new RuntimeException("Data de nascimento inválida. Formato esperado: yyyy-MM-dd");
        }

        // REGRA DE NEGÓCIO: VALORES PADRÃO
        // Se a carga horária for nula, o sistema define 44h automaticamente
        f.setCargaHorariaSemanal(
            dto.cargaHorariaSemanal != null 
            ? dto.cargaHorariaSemanal 
            : 44
        );

        // Se grau de insalubridade for nulo, define como "NENHUM"
        f.setGrauInsalubridade(
            dto.grauInsalubridade != null 
            ? dto.grauInsalubridade 
            : "NENHUM"
        );

        return f;
    }
}
````

---

### 📄 Uso no Controller – `FuncionarioController.java`

Agora o Controller atua apenas como **despachante**, delegando a responsabilidade de criação para a Factory:

```java
@PostMapping
public ResponseEntity<Funcionario> criarFuncionario(@RequestBody FuncionarioRequestDTO dto) {

    // 1. A Factory cria o objeto de domínio pronto
    Funcionario novoFuncionario = FuncionarioFactory.criarDoDTO(dto);

    // 2. O Service apenas persiste (sem conversões ou regras extras)
    Funcionario salvo = funcionarioService.salvarFuncionario(novoFuncionario);

    return ResponseEntity.status(201).body(salvo);
}
```

---

## 🏆 Benefícios Obtidos

✅ **Controller mais limpo**
Sem código de conversão (`parse`, `new BigDecimal`, etc.)

✅ **Centralização de regras de criação**
Se a carga horária padrão mudar de 44h para 40h, a alteração acontece em **apenas um lugar**

✅ **Separação de responsabilidades (SRP)**
Cada camada agora tem uma função clara:

* DTO → Transporte
* Factory → Criação e validação
* Model → Regra de negócio
* Controller → Orquestração

✅ **Mais resiliência contra erros de entrada**

* Datas inválidas são tratadas antes de atingir o banco
* Menor risco de erro 400 por causa de conversão automática do Spring


