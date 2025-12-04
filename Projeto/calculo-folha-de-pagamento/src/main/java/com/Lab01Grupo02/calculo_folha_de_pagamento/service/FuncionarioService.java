package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.jpa.FuncionarioRepository;

// Pacote de Exceções
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.ResourceNotFoundException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.DuplicateCpfException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.InvalidDataException;
import com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException.BusinessRuleException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<Funcionario> buscarPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            // Retorna uma lista vazia se a busca for nula ou vazia
            return List.of();
        }
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    /**
     * Busca TODOS os funcionários cadastrados no banco de dados.
     *
     * @return Uma Lista de todos os funcionários.
     */
    @Transactional(readOnly = true)
    public List<Funcionario> buscarTodos() {
        // O método findAll() do repositório busca todos os registros.
        // Se não houver nenhum, ele retorna uma lista vazia (o que está correto).
        return funcionarioRepository.findAll();
    }

    /**
     * NOVO METODO: Atualizar a carga horária semanal do funcionario.
     * @param matricula A matricula do funcionario a ser atualizado.
     * @param novaCargaHoraria O novo valor para a carga horária semanal.
     * @return O Funcionario com os dados atualizados.
     * @throws ResourceNotFoundException Se o funcionário não for encontrado.
     */
    @Transactional
    public Funcionario atualizarCargaHoraria(Integer matricula, int novaCargaHoraria){
        // -- 1. Busca o Funcionario com o metodo de busca --
        Funcionario funcionario = buscarPorMatricula(matricula);

        // -- 2. Modificar objeto --
        funcionario.setCargaHorariaSemanal(novaCargaHoraria);

        // -- 3. Salvar o objeto modificado de volta no banco
        return funcionario;
    }

    /**
     * Salva um novo funcionário no banco de dados.
     *
     * @param funcionario O objeto Funcionario a ser salvo.
     * @return O Funcionario salvo com o ID gerado.
     * @throws InvalidDataException Se os dados do funcionário forem inválidos.
     * @throws DuplicateCpfException Se o CPF já estiver cadastrado.
     */
    @Transactional
    public Funcionario salvarFuncionario(Funcionario funcionario) {
        // Validações antes de salvar
        validarDadosFuncionario(funcionario);

        // Limpar o CPF (remover pontos e traços) antes de salvar
        if (funcionario.getCpf() != null) {
            String cpfLimpo = funcionario.getCpf().replaceAll("[.-]", "");
            funcionario.setCpf(cpfLimpo);

            // Verifica se o CPF já existe no banco
            Optional<Funcionario> funcionarioExistente = funcionarioRepository.findByCpf(cpfLimpo);
            if (funcionarioExistente.isPresent()) {
                throw new DuplicateCpfException("Já existe um funcionário cadastrado com o CPF: " + cpfLimpo);
            }
        }

        return funcionarioRepository.save(funcionario);
    }

    /**
     * Atualiza um funcionário existente no banco de dados.
     *
     * @param matricula A matrícula do funcionário a ser atualizado.
     * @param funcionarioAtualizado O objeto Funcionario com os novos dados.
     * @return O Funcionario atualizado.
     * @throws ResourceNotFoundException Se o funcionário não for encontrado.
     * @throws InvalidDataException Se os dados do funcionário forem inválidos.
     * @throws DuplicateCpfException Se o CPF já estiver cadastrado para outro funcionário.
     */
    @Transactional
    public Funcionario atualizarFuncionario(Integer matricula, Funcionario funcionarioAtualizado) {
        // Verifica se o funcionário existe
        Funcionario funcionarioExistente = buscarPorMatricula(matricula);

        // Validações antes de atualizar
        validarDadosFuncionario(funcionarioAtualizado);

        // Atualiza os campos (dados de Pessoa)
        funcionarioExistente.setNome(funcionarioAtualizado.getNome());

        // Limpar o CPF antes de atualizar
        if (funcionarioAtualizado.getCpf() != null) {
            String cpfLimpo = funcionarioAtualizado.getCpf().replaceAll("[.-]", "");

            // Verifica se o CPF está sendo alterado e se já existe para outro funcionário
            if (!cpfLimpo.equals(funcionarioExistente.getCpf())) {
                Optional<Funcionario> funcionarioComMesmoCpf = funcionarioRepository.findByCpf(cpfLimpo);
                if (funcionarioComMesmoCpf.isPresent() &&
                    funcionarioComMesmoCpf.get().getIdPessoa() != funcionarioExistente.getIdPessoa()) {
                    throw new DuplicateCpfException("Já existe outro funcionário cadastrado com o CPF: " + cpfLimpo);
                }
            }

            funcionarioExistente.setCpf(cpfLimpo);
        }

        funcionarioExistente.setDataNascimento(funcionarioAtualizado.getDataNascimento());

        // Atualiza os campos específicos de Funcionario
        funcionarioExistente.setCargo(funcionarioAtualizado.getCargo());
        funcionarioExistente.setDataAdmissao(funcionarioAtualizado.getDataAdmissao());
        funcionarioExistente.setSalarioBruto(funcionarioAtualizado.getSalarioBruto());
        funcionarioExistente.setGrauInsalubridade(funcionarioAtualizado.getGrauInsalubridade());
        funcionarioExistente.setCargaHorariaSemanal(funcionarioAtualizado.getCargaHorariaSemanal());
        funcionarioExistente.setPossuiPericulosidade(funcionarioAtualizado.isPossuiPericulosidade());

        // O save() funciona tanto para inserir quanto para atualizar
        return funcionarioRepository.save(funcionarioExistente);
    }

    /**
     * Valida os dados de um funcionário antes de salvar ou atualizar.
     *
     * @param funcionario O objeto Funcionario a ser validado.
     * @throws InvalidDataException Se algum dado for inválido.
     */
    private void validarDadosFuncionario(Funcionario funcionario) {
        // Validação de campos obrigatórios
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            throw new InvalidDataException("O nome do funcionário é obrigatório.");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            throw new InvalidDataException("O CPF do funcionário é obrigatório.");
        }

        // Validação básica de CPF (apenas formato)
        String cpfLimpo = funcionario.getCpf().replaceAll("[.-]", "");
        if (!cpfLimpo.matches("\\d{11}")) {
            throw new InvalidDataException("O CPF deve conter exatamente 11 dígitos numéricos.");
        }

        if (funcionario.getDataNascimento() == null) {
            throw new InvalidDataException("A data de nascimento é obrigatória.");
        }

        // Validação: data de nascimento não pode ser futura
        if (funcionario.getDataNascimento().isAfter(LocalDate.now())) {
            throw new InvalidDataException("A data de nascimento não pode ser no futuro.");
        }

        // Validação: idade mínima de 16 anos
        int idade = funcionario.getIdade();
        if (idade < 16) {
            throw new InvalidDataException("O funcionário deve ter no mínimo 16 anos de idade.");
        }

        if (funcionario.getCargo() == null || funcionario.getCargo().trim().isEmpty()) {
            throw new InvalidDataException("O cargo do funcionário é obrigatório.");
        }

        if (funcionario.getDataAdmissao() == null) {
            throw new InvalidDataException("A data de admissão é obrigatória.");
        }

        // Validação: data de admissão não pode ser futura
        if (funcionario.getDataAdmissao().isAfter(LocalDate.now())) {
            throw new InvalidDataException("A data de admissão não pode ser no futuro.");
        }

        // Validação: data de admissão não pode ser antes do nascimento
        if (funcionario.getDataAdmissao().isBefore(funcionario.getDataNascimento())) {
            throw new InvalidDataException("A data de admissão não pode ser anterior à data de nascimento.");
        }

        if (funcionario.getSalarioBruto() == null) {
            throw new InvalidDataException("O salário bruto é obrigatório.");
        }

        // Validação: salário deve ser positivo
        if (funcionario.getSalarioBruto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidDataException("O salário bruto deve ser maior que zero.");
        }

        // Validação: salário máximo razoável (exemplo: R$ 1.000.000,00)
        if (funcionario.getSalarioBruto().compareTo(new BigDecimal("1000000.00")) > 0) {
            throw new InvalidDataException("O salário bruto informado excede o valor máximo permitido.");
        }

        // Validação: carga horária semanal
        if (funcionario.getCargaHorariaSemanal() <= 0) {
            throw new InvalidDataException("A carga horária semanal deve ser maior que zero.");
        }
        // Validação: grau de insalubridade
        if (funcionario.getGrauInsalubridade() != null && !funcionario.getGrauInsalubridade().trim().isEmpty()) {
            String grau = funcionario.getGrauInsalubridade().toUpperCase();
            if (!grau.equals("NENHUM") && !grau.equals("MINIMO") &&
                !grau.equals("MEDIO") && !grau.equals("MAXIMO")) {
                throw new InvalidDataException(
                    "Grau de insalubridade inválido. Valores permitidos: NENHUM, MINIMO, MEDIO, MAXIMO.");
            }
        }
    }

    /**
     * Exclui um funcionário do banco de dados pela matrícula.
     *
     * @param matricula A matrícula do funcionário a ser excluído.
     * @throws ResourceNotFoundException Se o funcionário não for encontrado.
     */
    @Transactional
    public void excluirFuncionario(Integer matricula) {
        // Verifica se o funcionário existe antes de tentar excluir
        Funcionario funcionario = buscarPorMatricula(matricula);
        funcionarioRepository.delete(funcionario);
    }
}