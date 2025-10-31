package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.FuncionarioRepository;

// Pacote de Exceções
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Camada de Serviço (Lógica de Negócio) para operações relacionadas a Funcionários.
 * Utiliza o FuncionarioRepository para acessar os dados e lança exceções
 * customizadas em caso de falhas de negócio (ex: não encontrado).
 */
@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    /**
     * Busca um funcionário pela sua MATRÍCULA (Chave Primária).
     *
     * @param matricula O ID (matrícula) do funcionário.
     * @return O objeto Funcionario encontrado.
     * @throws ResourceNotFoundException Se o funcionário não for encontrado.
     */
    public Funcionario buscarPorMatricula(Integer matricula) {
        // O método findById() do repositório retorna um Optional.
        // Usamos .orElseThrow() para retornar o Funcionario se ele existir,
        // ou lançar nossa exceção customizada se o Optional estiver vazio.
        return funcionarioRepository.findById(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com a matrícula: " + matricula));
    }

    /**
     * Busca um funcionário pelo seu CPF (busca exata).
     *
     * @param cpf O CPF do funcionário (pode conter pontos ou traços).
     * @return O objeto Funcionario encontrado.
     * @throws ResourceNotFoundException Se o CPF for nulo, vazio ou não encontrado.
     */
    public Funcionario buscarPorCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            // Lançamos a exceção se a busca for inválida (CPF vazio)
            throw new ResourceNotFoundException("O CPF para busca não pode ser nulo ou vazio.");
        }

        // Boa prática: Limpar o CPF (remover pontos e traços) antes de buscar no banco
        String cpfLimpo = cpf.replaceAll("[.-]", "");

        return funcionarioRepository.findByCpf(cpfLimpo)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário não encontrado com o CPF informado."));
    }

    /**
     * Busca funcionários por parte do nome (ignorando maiúsculas/minúsculas).
     *
     * IMPORTANTE: Este método NÃO lança ResourceNotFoundException.
     * Pois uma busca que retorna 0 é considera uma busca com sucesso.
     *
     * @param nome O termo de busca (parte do nome).
     * @return Uma Lista (possivelmente vazia) de funcionários que correspondem.
     */
    public List<Funcionario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            // Retorna uma lista vazia se a busca for nula ou vazia
            return List.of();
        }
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }
}