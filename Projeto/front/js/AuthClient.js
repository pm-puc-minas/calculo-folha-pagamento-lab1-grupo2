/**
 * AuthClient.js
 * Cliente HTTP para comunicação com a API de Autenticação
 * Responsável apenas pelas requisições HTTP de login e autenticação
 */

class AuthClient {
    /**
     * Efetua login do usuário (RH ou Funcionário)
     * @param {string} identificador - Email (RH) ou CPF (Funcionário)
     * @param {string} senha - Senha do usuário
     * @returns {Promise<Object>} Dados do usuário autenticado
     */
    static async login(identificador, senha) {
        const response = await fetch(`${API_BASE_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                email: identificador,
                senha: senha
            })
        });

        if (!response.ok) {
            if (response.status === 401 || response.status === 403) {
                const errorData = await response.json().catch(() => null);
                throw new Error(errorData?.message || 'Email/CPF ou senha incorretos');
            }
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Verifica se um email já está cadastrado
     * @param {string} email - Email a ser verificado
     * @returns {Promise<Object>} Objeto com resultado da verificação
     */
    static async verificarEmail(email) {
        const response = await fetch(`${API_BASE_URL}/auth/verificar-email?email=${encodeURIComponent(email)}`);

        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }

    /**
     * Verifica o status do serviço de autenticação
     * @returns {Promise<Object>} Status do serviço
     */
    static async verificarSaude() {
        const response = await fetch(`${API_BASE_URL}/auth/health`);

        if (!response.ok) {
            throw new Error(`Erro HTTP: ${response.status}`);
        }

        return await response.json();
    }
}
