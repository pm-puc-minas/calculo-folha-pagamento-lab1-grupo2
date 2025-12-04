/**
 * FuncionarioClient.js
 * Cliente HTTP para comunicação com a API de Funcionários
 * Responsável apenas pelas requisições HTTP
 */

class FuncionarioClient {
    /**
     * Busca todos os funcionários
     * @returns {Promise<Array>} Lista de funcionários
     */
    static async buscarTodos() {
        const response = await fetch(`${API_BASE_URL}/funcionarios`);

        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Busca um funcionário por matrícula
     * @param {number} matricula - Matrícula do funcionário
     * @returns {Promise<Object>} Dados do funcionário
     */
    static async buscarPorMatricula(matricula) {
        const response = await fetch(`${API_BASE_URL}/funcionarios/${matricula}`);

        if (!response.ok) {
            if (response.status === 404) {
                throw new Error('Funcionário não encontrado');
            }
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Busca funcionário por CPF
     * @param {string} cpf - CPF do funcionário (com ou sem formatação)
     * @returns {Promise<Object>} Dados do funcionário
     */
    static async buscarPorCpf(cpf) {
        const cpfLimpo = cpf.replace(/\D/g, '');
        const response = await fetch(`${API_BASE_URL}/funcionarios/cpf?valor=${cpfLimpo}`);

        if (!response.ok) {
            if (response.status === 404) {
                throw new Error('Funcionário não encontrado');
            }
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Busca funcionários por nome (busca parcial)
     * @param {string} nome - Nome ou parte do nome
     * @returns {Promise<Array>} Lista de funcionários encontrados
     */
    static async buscarPorNome(nome) {
        const response = await fetch(`${API_BASE_URL}/funcionarios/nome?termo=${encodeURIComponent(nome)}`);

        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Cria um novo funcionário
     * @param {Object} funcionarioDTO - Dados do funcionário
     * @returns {Promise<Object>} Funcionário criado
     */
    static async criar(funcionarioDTO) {
        const response = await fetch(`${API_BASE_URL}/funcionarios`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(funcionarioDTO)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            const errorMessage = errorData?.message || `Erro HTTP: ${response.status}`;
            throw new Error(errorMessage);
        }

        return await response.json();
    }

    /**
     * Atualiza um funcionário existente
     * @param {number} matricula - Matrícula do funcionário
     * @param {Object} funcionario - Dados atualizados do funcionário
     * @returns {Promise<Object>} Funcionário atualizado
     */
    static async atualizar(matricula, funcionario) {
        const response = await fetch(`${API_BASE_URL}/funcionarios/${matricula}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(funcionario)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => null);
            const errorMessage = errorData?.message || `Erro HTTP: ${response.status}`;
            throw new Error(errorMessage);
        }

        return await response.json();
    }

    /**
     * Atualiza apenas a carga horária de um funcionário
     * @param {number} matricula - Matrícula do funcionário
     * @param {number} cargaHoraria - Nova carga horária semanal
     * @returns {Promise<Object>} Funcionário atualizado
     */
    static async atualizarCargaHoraria(matricula, cargaHoraria) {
        const response = await fetch(`${API_BASE_URL}/funcionarios/${matricula}/carga-horaria`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ cargaHoraria: cargaHoraria })
        });

        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Exclui um funcionário
     * @param {number} matricula - Matrícula do funcionário a ser excluído
     * @returns {Promise<void>}
     */
    static async excluir(matricula) {
        const response = await fetch(`${API_BASE_URL}/funcionarios/${matricula}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            if (response.status === 404) {
                throw new Error('Funcionário não encontrado');
            }
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        // DELETE retorna 204 No Content
        return;
    }
}
