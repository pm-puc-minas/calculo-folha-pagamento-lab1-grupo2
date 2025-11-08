package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.InvalidCredentialsException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.ResourceNotFoundException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.UserDeactivatedException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.LoginRequest;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.LoginResponse;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Usuario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service responsável pela lógica de autenticação de usuários.
 * Contém métodos para login e validação de credenciais.
 */
@Service
public class LoginService {

    private final UsuarioRepository usuarioRepository;

    public LoginService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Efetua o login do usuário validando suas credenciais.
     * 
     * @param loginRequest Dados de login (email e senha)
     * @return LoginResponse com dados do usuário autenticado
     * @throws InvalidCredentialsException quando email/senha estão incorretos
     * @throws UserDeactivatedException quando usuário está desativado
     * @throws ResourceNotFoundException quando usuário não existe
     */
    public LoginResponse efetuarLogin(LoginRequest loginRequest) {
        // 1. Buscar usuário pelo email
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(loginRequest.getEmail());
        
        if (usuarioOptional.isEmpty()) {
            throw new ResourceNotFoundException("Não foi encontrado usuário com o email: " + loginRequest.getEmail());
        }
        
        Usuario usuario = usuarioOptional.get();
        
        // 2. Verificar se o usuário está ativo
        if (!usuario.isAtivo()) {
            throw new UserDeactivatedException("Usuário está desativado. Entre em contato com o administrador.");
        }
        
        // 3. Validar senha
        if (!validarSenha(loginRequest.getSenha(), usuario.getSenha())) {
            throw new InvalidCredentialsException("Email ou senha incorretos.");
        }
        
        // 4. Login bem-sucedido - retornar dados do usuário
        return new LoginResponse(
            usuario.getIdPessoa(),
            usuario.getNome(),
            usuario.getEmail()
        );
    }
    
    /**
     * Valida se a senha fornecida corresponde à senha armazenada.
     * 
     * @param senhaFornecida Senha digitada pelo usuário
     * @param senhaArmazenada Senha armazenada no banco de dados
     * @return true se as senhas coincidem, false caso contrário
     */
    private boolean validarSenha(String senhaFornecida, String senhaArmazenada) {
        // Por enquanto, comparação simples de strings
        // TODO: Implementar hash de senha (BCrypt) em futuras versões
        return senhaFornecida != null && senhaFornecida.equals(senhaArmazenada);
    }
    
    /**
     * Verifica se um email já está cadastrado no sistema.
     * 
     * @param email Email a ser verificado
     * @return true se o email existe, false caso contrário
     */
    public boolean existeEmailCadastrado(String email) {
        return usuarioRepository.existsByEmail(email);
    }
    
    /**
     * Busca um usuário pelo email (para fins administrativos).
     * 
     * @param email Email do usuário
     * @return Usuario encontrado
     * @throws ResourceNotFoundException quando usuário não existe
     */
    public Usuario buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário com email " + email + " não encontrado."));
    }
}
