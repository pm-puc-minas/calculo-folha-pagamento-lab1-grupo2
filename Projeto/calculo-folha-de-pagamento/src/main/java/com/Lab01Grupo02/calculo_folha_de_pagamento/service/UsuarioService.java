package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.ResourceNotFoundException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Usuario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service responsável por operações CRUD com usuários.
 * Complementa o LoginService com funcionalidades administrativas.
 */
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Cadastra um novo usuário no sistema.
     * 
     * @param usuario Dados do usuário a ser cadastrado
     * @return Usuario cadastrado com ID gerado
     * @throws DataIntegrityViolationException se email já existe
     */
    public Usuario cadastrarUsuario(Usuario usuario) {
        // Verificar se email já existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new DataIntegrityViolationException("Já existe um usuário cadastrado com o email: " + usuario.getEmail());
        }
        
        // Garantir que o usuário inicia como ativo
        usuario.setAtivo(true);
        
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Lista todos os usuários cadastrados.
     * 
     * @return Lista de todos os usuários
     */
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    /**
     * Busca um usuário pelo ID.
     * 
     * @param id ID do usuário
     * @return Usuario encontrado
     * @throws ResourceNotFoundException se usuário não existe
     */
    public Usuario buscarUsuarioPorId(int id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário com ID " + id + " não encontrado."));
    }
    
    /**
     * Ativa um usuário desativado.
     * 
     * @param id ID do usuário
     * @return Usuario ativado
     * @throws ResourceNotFoundException se usuário não existe
     */
    public Usuario ativarUsuario(int id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setAtivo(true);
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Desativa um usuário (não remove do banco, apenas marca como inativo).
     * 
     * @param id ID do usuário
     * @return Usuario desativado
     * @throws ResourceNotFoundException se usuário não existe
     */
    public Usuario desativarUsuario(int id) {
        Usuario usuario = buscarUsuarioPorId(id);
        usuario.setAtivo(false);
        return usuarioRepository.save(usuario);
    }
    
    /**
     * Atualiza dados de um usuário existente.
     * 
     * @param id ID do usuário
     * @param dadosAtualizados Novos dados do usuário
     * @return Usuario atualizado
     * @throws ResourceNotFoundException se usuário não existe
     */
    public Usuario atualizarUsuario(int id, Usuario dadosAtualizados) {
        Usuario usuarioExistente = buscarUsuarioPorId(id);
        
        // Atualizar apenas campos não nulos
        if (dadosAtualizados.getNome() != null) {
            usuarioExistente.setNome(dadosAtualizados.getNome());
        }
        if (dadosAtualizados.getCpf() != null) {
            usuarioExistente.setCpf(dadosAtualizados.getCpf());
        }
        if (dadosAtualizados.getEmail() != null && !dadosAtualizados.getEmail().equals(usuarioExistente.getEmail())) {
            // Verificar se novo email já existe
            if (usuarioRepository.existsByEmail(dadosAtualizados.getEmail())) {
                throw new DataIntegrityViolationException("Já existe um usuário com o email: " + dadosAtualizados.getEmail());
            }
            usuarioExistente.setEmail(dadosAtualizados.getEmail());
        }
        if (dadosAtualizados.getSenha() != null) {
            usuarioExistente.setSenha(dadosAtualizados.getSenha());
        }
        
        return usuarioRepository.save(usuarioExistente);
    }
}
