package com.Lab01Grupo02.calculo_folha_de_pagamento.controller;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Usuario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para operações CRUD de usuários.
 * Fornece endpoints para gerenciamento de usuários do sistema.
 */
@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Cadastra um novo usuário.
     * 
     * Rota: POST /api/usuarios
     * 
     * @param usuario Dados do usuário a ser cadastrado
     * @return Usuario cadastrado com ID gerado
     */
    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario usuarioCadastrado = usuarioService.cadastrarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCadastrado);
    }

    /**
     * Lista todos os usuários cadastrados.
     * 
     * Rota: GET /api/usuarios
     * 
     * @return Lista de todos os usuários
     */
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodosUsuarios() {
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Busca um usuário pelo ID.
     * 
     * Rota: GET /api/usuarios/{id}
     * 
     * @param id ID do usuário
     * @return Usuario encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable int id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    /**
     * Atualiza dados de um usuário existente.
     * 
     * Rota: PUT /api/usuarios/{id}
     * 
     * @param id ID do usuário
     * @param dadosAtualizados Novos dados do usuário
     * @return Usuario atualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, 
                                                   @Valid @RequestBody Usuario dadosAtualizados) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, dadosAtualizados);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    /**
     * Ativa um usuário desativado.
     * 
     * Rota: PATCH /api/usuarios/{id}/ativar
     * 
     * @param id ID do usuário
     * @return Usuario ativado
     */
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<Usuario> ativarUsuario(@PathVariable int id) {
        Usuario usuarioAtivado = usuarioService.ativarUsuario(id);
        return ResponseEntity.ok(usuarioAtivado);
    }

    /**
     * Desativa um usuário (soft delete).
     * 
     * Rota: PATCH /api/usuarios/{id}/desativar
     * 
     * @param id ID do usuário
     * @return Usuario desativado
     */
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<Usuario> desativarUsuario(@PathVariable int id) {
        Usuario usuarioDesativado = usuarioService.desativarUsuario(id);
        return ResponseEntity.ok(usuarioDesativado);
    }
}
