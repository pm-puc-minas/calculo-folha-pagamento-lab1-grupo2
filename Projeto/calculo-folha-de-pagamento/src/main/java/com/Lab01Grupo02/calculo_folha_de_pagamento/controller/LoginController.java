package com.Lab01Grupo02.calculo_folha_de_pagamento.controller;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.LoginRequest;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.LoginResponse;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;

/**
 * Controller REST para operações de autenticação.
 * Fornece endpoints para login de usuários.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (para desenvolvimento)
public class LoginController {

    private static final String MENSAGEM_KEY = "mensagem";
    private static final String EXISTE_KEY = "existe";
    
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Endpoint para efetuar login do usuário.
     * 
     * Rota: POST /api/auth/login
     * 
     * @param loginRequest Dados de login (email e senha)
     * @return LoginResponse com dados do usuário autenticado ou erro
     * 
     * Exemplo de requisição:
     * POST /api/auth/login
     * {
     *   "email": "usuario@exemplo.com",
     *   "senha": "123456"
     * }
     * 
     * Exemplo de resposta (sucesso):
     * {
     *   "idUsuario": 1,
     *   "nome": "João Silva",
     *   "email": "usuario@exemplo.com",
     *   "mensagem": "Login realizado com sucesso",
     *   "sucesso": true
     * }
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = loginService.efetuarLogin(loginRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para verificar se um email já está cadastrado.
     * 
     * Rota: GET /api/auth/verificar-email?email=usuario@exemplo.com
     * 
     * @param email Email a ser verificado
     * @return JSON indicando se o email existe
     * 
     * Exemplo de resposta:
     * {
     *   "existe": true,
     *   "mensagem": "Email já está cadastrado no sistema"
     * }
     */    @GetMapping("/verificar-email")
    public ResponseEntity<Map<String, Object>> verificarEmail(@RequestParam String email) {
        boolean existe = loginService.existeEmailCadastrado(email);
        
        if (existe) {
            return ResponseEntity.ok(Map.of(
                EXISTE_KEY, true,
                MENSAGEM_KEY, "Email já está cadastrado no sistema"
            ));
        } else {
            return ResponseEntity.ok(Map.of(
                EXISTE_KEY, false,
                MENSAGEM_KEY, "Email disponível para cadastro"
            ));
        }
    }

    /**
     * Endpoint para validar se o serviço de autenticação está funcionando.
     * 
     * Rota: GET /api/auth/health
     * 
     * @return Mensagem de status do serviço
     */    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "online",
            "servico", "LoginService",
            MENSAGEM_KEY, "Serviço de autenticação está funcionando corretamente"
        ));
    }
}
