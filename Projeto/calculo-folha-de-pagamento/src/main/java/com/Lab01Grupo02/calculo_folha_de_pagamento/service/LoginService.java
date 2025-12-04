package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.InvalidCredentialsException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.ResourceNotFoundException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.UserDeactivatedException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.*;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.FuncionarioRepository;
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
    private final FuncionarioRepository funcionarioRepository;

    public LoginService(UsuarioRepository usuarioRepository, FuncionarioRepository funcionarioRepository) {
        this.usuarioRepository = usuarioRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Efetua o login do usuário validando suas credenciais.
     * Suporta dois tipos de login:
     * 1. Login com EMAIL + SENHA (para usuários RH)
     * 2. Login com CPF + SENHA (para funcionários)
     *
     * @param loginRequest Dados de login (email/cpf e senha)
     * @return LoginResponse com dados do usuário autenticado
     * @throws InvalidCredentialsException quando email/cpf/senha estão incorretos
     * @throws UserDeactivatedException quando usuário/funcionário está desativado
     */
    public LoginResponse efetuarLogin(LoginRequest loginRequest) {
        String identificador = loginRequest.getEmail();
        String senha = loginRequest.getSenha();

        // Verifica se o identificador parece ser um email
        if (identificador.contains("@")) {
            // Tenta login como Usuario (RH)
            return efetuarLoginUsuario(identificador, senha);
        } else {
            // Tenta login como Funcionario (usando CPF)
            return efetuarLoginFuncionario(identificador, senha);
        }
    }

    /**
     * Login para usuários RH (usando email).
     */
    private LoginResponse efetuarLoginUsuario(String email, String senha) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);

        if (usuarioOptional.isEmpty()) {
            throw new InvalidCredentialsException("Email ou senha incorretos.");
        }

        Usuario usuario = usuarioOptional.get();

        if (!usuario.isAtivo()) {
            throw new UserDeactivatedException("Usuário está desativado. Entre em contato com o administrador.");
        }

        if (!validarSenha(senha, usuario.getSenha())) {
            throw new InvalidCredentialsException("Email ou senha incorretos.");
        }

        return new LoginResponse(
            usuario.getIdPessoa(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getTipo()
        );
    }

    /**
     * Login para funcionários (usando CPF).
     */
    private LoginResponse efetuarLoginFuncionario(String cpf, String senha) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByCpf(cpf);

        if (funcionarioOptional.isEmpty()) {
            throw new InvalidCredentialsException("CPF ou senha incorretos.");
        }

        Funcionario funcionario = funcionarioOptional.get();

        if (funcionario.getAtivo() == null || !funcionario.getAtivo()) {
            throw new UserDeactivatedException("Funcionário está desativado. Entre em contato com o RH.");
        }

        if (funcionario.getSenha() == null || funcionario.getSenha().isEmpty()) {
            throw new InvalidCredentialsException("Funcionário não possui senha cadastrada. Entre em contato com o RH.");
        }

        if (!validarSenha(senha, funcionario.getSenha())) {
            throw new InvalidCredentialsException("CPF ou senha incorretos.");
        }

        // Retorna com tipo FUNCIONARIO e matrícula
        return new LoginResponse(
            funcionario.getIdPessoa(),
            funcionario.getNome(),
            cpf,
            TipoUsuario.FUNCIONARIO,
            funcionario.getIdPessoa()
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
