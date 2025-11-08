# Sistema de Login - Documentação da API

Este documento descreve os endpoints implementados para o sistema de autenticação e gerenciamento de usuários.

## 🔐 Endpoints de Autenticação (`/api/auth`)

### 1. Efetuar Login
**POST** `/api/auth/login`

Autentica um usuário no sistema usando email e senha.

#### Requisição:
```json
{
  "email": "usuario@exemplo.com",
  "senha": "123456"
}
```

#### Resposta (Sucesso - 200):
```json
{
  "idUsuario": 1,
  "nome": "João Silva",
  "email": "usuario@exemplo.com",
  "mensagem": "Login realizado com sucesso",
  "sucesso": true
}
```

#### Possíveis Erros:
- **401 Unauthorized**: Email ou senha incorretos
- **403 Forbidden**: Usuário desativado
- **404 Not Found**: Usuário não encontrado
- **400 Bad Request**: Dados de entrada inválidos

### 2. Verificar Email
**GET** `/api/auth/verificar-email?email=usuario@exemplo.com`

Verifica se um email já está cadastrado no sistema.

#### Resposta:
```json
{
  "existe": true,
  "mensagem": "Email já está cadastrado no sistema"
}
```

### 3. Health Check
**GET** `/api/auth/health`

Verifica se o serviço de autenticação está funcionando.

#### Resposta:
```json
{
  "status": "online",
  "servico": "LoginService",
  "mensagem": "Serviço de autenticação está funcionando corretamente"
}
```

## 👥 Endpoints de Gerenciamento de Usuários (`/api/usuarios`)

### 1. Cadastrar Usuário
**POST** `/api/usuarios`

Cadastra um novo usuário no sistema.

#### Requisição:
```json
{
  "nome": "João Silva",
  "cpf": "12345678901",
  "email": "joao@exemplo.com",
  "senha": "123456"
}
```

#### Resposta (201 Created):
```json
{
  "idPessoa": 1,
  "nome": "João Silva",
  "cpf": "12345678901",
  "email": "joao@exemplo.com",
  "ativo": true
}
```

### 2. Listar Todos os Usuários
**GET** `/api/usuarios`

Retorna lista de todos os usuários cadastrados.

### 3. Buscar Usuário por ID
**GET** `/api/usuarios/{id}`

Busca um usuário específico pelo ID.

### 4. Atualizar Usuário
**PUT** `/api/usuarios/{id}`

Atualiza dados de um usuário existente.

### 5. Ativar Usuário
**PATCH** `/api/usuarios/{id}/ativar`

Ativa um usuário desativado.

### 6. Desativar Usuário
**PATCH** `/api/usuarios/{id}/desativar`

Desativa um usuário (soft delete).

## 🚨 Tratamento de Exceções

O sistema possui tratamento global de exceções que retorna respostas padronizadas:

### Estrutura de Erro:
```json
{
  "status": 404,
  "message": "Usuário com email usuario@exemplo.com não encontrado.",
  "details": "uri=/api/auth/login"
}
```

### Tipos de Exceções:
- **InvalidCredentialsException** → HTTP 401
- **UserDeactivatedException** → HTTP 403  
- **ResourceNotFoundException** → HTTP 404
- **DataIntegrityViolationException** → HTTP 400
- **MethodArgumentNotValidException** → HTTP 400

## 🧪 Testando a API

### Exemplo com cURL:
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"usuario@exemplo.com","senha":"123456"}'

# Cadastrar usuário
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"João Silva","cpf":"12345678901","email":"joao@exemplo.com","senha":"123456"}'
```

### Exemplo com Postman:
1. Criar requisição POST para `http://localhost:8080/api/auth/login`
2. Definir Header: `Content-Type: application/json`
3. No Body (raw/JSON), colocar os dados de login
4. Enviar e verificar resposta

## 📝 Validações Implementadas

### LoginRequest:
- Email: obrigatório e formato válido
- Senha: obrigatória, mínimo 6 caracteres

### Usuario:
- Nome: obrigatório
- CPF: obrigatório  
- Email: obrigatório, formato válido, único
- Senha: obrigatória, mínimo 6 caracteres
