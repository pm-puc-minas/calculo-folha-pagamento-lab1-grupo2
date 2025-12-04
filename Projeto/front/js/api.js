/**
 * API Service - Centraliza todas as chamadas HTTP para o backend
 * Base URL: http://localhost:9090/api
 */

const API_BASE_URL = 'http://localhost:9090/api';

/**
 * Serviço de API para Funcionários
 */
const FuncionarioAPI = {
    /**
     * Busca todos os funcionários
     * @returns {Promise<Array>} Lista de funcionários
     */
    async buscarTodos() {
        try {
            const response = await fetch(`${API_BASE_URL}/funcionarios`);

            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao buscar funcionários:', error);
            throw error;
        }
    },

    /**
     * Busca um funcionário por matrícula
     * @param {number} matricula - Matrícula do funcionário
     * @returns {Promise<Object>} Dados do funcionário
     */
    async buscarPorMatricula(matricula) {
        try {
            const response = await fetch(`${API_BASE_URL}/funcionarios/${matricula}`);

            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Funcionário não encontrado');
                }
                throw new Error(`Erro HTTP: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao buscar funcionário:', error);
            throw error;
        }
    },

    /**
     * Busca funcionário por CPF
     * @param {string} cpf - CPF do funcionário (com ou sem formatação)
     * @returns {Promise<Object>} Dados do funcionário
     */
    async buscarPorCpf(cpf) {
        try {
            const cpfLimpo = cpf.replace(/\D/g, '');
            const response = await fetch(`${API_BASE_URL}/funcionarios/cpf?valor=${cpfLimpo}`);

            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Funcionário não encontrado');
                }
                throw new Error(`Erro HTTP: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao buscar funcionário por CPF:', error);
            throw error;
        }
    },

    /**
     * Busca funcionários por nome (busca parcial)
     * @param {string} nome - Nome ou parte do nome
     * @returns {Promise<Array>} Lista de funcionários encontrados
     */
    async buscarPorNome(nome) {
        try {
            const response = await fetch(`${API_BASE_URL}/funcionarios/nome?termo=${encodeURIComponent(nome)}`);

            if (!response.ok) {
                throw new Error(`Erro HTTP: ${response.status}`);
            }

            return await response.json();
        } catch (error) {
            console.error('Erro ao buscar funcionários por nome:', error);
            throw error;
        }
    },

    /**
     * Cria um novo funcionário
     * @param {Object} funcionarioDTO - Dados do funcionário (FuncionarioRequestDTO)
     * @returns {Promise<Object>} Funcionário criado
     */
    async criar(funcionarioDTO) {
        try {
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
        } catch (error) {
            console.error('Erro ao criar funcionário:', error);
            throw error;
        }
    },

    /**
     * Atualiza um funcionário existente
     * @param {number} matricula - Matrícula do funcionário
     * @param {Object} funcionario - Dados atualizados do funcionário
     * @returns {Promise<Object>} Funcionário atualizado
     */
    async atualizar(matricula, funcionario) {
        try {
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
        } catch (error) {
            console.error('Erro ao atualizar funcionário:', error);
            throw error;
        }
    },

    /**
     * Atualiza apenas a carga horária de um funcionário
     * @param {number} matricula - Matrícula do funcionário
     * @param {number} cargaHoraria - Nova carga horária semanal
     * @returns {Promise<Object>} Funcionário atualizado
     */
    async atualizarCargaHoraria(matricula, cargaHoraria) {
        try {
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
        } catch (error) {
            console.error('Erro ao atualizar carga horária:', error);
            throw error;
        }
    },

    /**
     * Exclui um funcionário
     * @param {number} matricula - Matrícula do funcionário a ser excluído
     * @returns {Promise<void>}
     */
    async excluir(matricula) {
        try {
            const response = await fetch(`${API_BASE_URL}/funcionarios/${matricula}`, {
                method: 'DELETE'
            });

            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error('Funcionário não encontrado');
                }
                throw new Error(`Erro HTTP: ${response.status}`);
            }

            // DELETE retorna 204 No Content, não tem body para parsear
            return;
        } catch (error) {
            console.error('Erro ao excluir funcionário:', error);
            throw error;
        }
    }
};

/**
 * Utilitários para formatação de dados
 */
const FormatUtils = {
    /**
     * Formata CPF para o padrão XXX.XXX.XXX-XX
     * @param {string} cpf - CPF sem formatação
     * @returns {string} CPF formatado
     */
    formatarCPF(cpf) {
        if (!cpf) return '';
        cpf = cpf.replace(/\D/g, '');
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    },

    /**
     * Remove formatação do CPF
     * @param {string} cpf - CPF formatado
     * @returns {string} CPF limpo (apenas números)
     */
    limparCPF(cpf) {
        if (!cpf) return '';
        return cpf.replace(/\D/g, '');
    },

    /**
     * Formata data para exibição (DD/MM/YYYY)
     * @param {string} data - Data no formato ISO (YYYY-MM-DD)
     * @returns {string} Data formatada
     */
    formatarData(data) {
        if (!data) return '';
        const [ano, mes, dia] = data.split('-');
        return `${dia}/${mes}/${ano}`;
    },

    /**
     * Converte data para formato ISO (YYYY-MM-DD)
     * @param {string} data - Data no formato DD/MM/YYYY
     * @returns {string} Data no formato ISO
     */
    dataParaISO(data) {
        if (!data) return '';
        const [dia, mes, ano] = data.split('/');
        return `${ano}-${mes}-${dia}`;
    },

    /**
     * Formata valor monetário
     * @param {number} valor - Valor numérico
     * @returns {string} Valor formatado (R$ X.XXX,XX)
     */
    formatarMoeda(valor) {
        if (valor === null || valor === undefined) return 'R$ 0,00';
        return `R$ ${Number(valor).toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
    }
};
