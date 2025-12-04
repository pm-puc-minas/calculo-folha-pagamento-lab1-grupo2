/**
 * Utils.js
 * Utilitários para formatação, validação e manipulação de dados
 * Não faz chamadas à API - apenas operações locais
 */

/**
 * Utilitários de Formatação
 */
class FormatUtils {
    /**
     * Formata CPF para o padrão XXX.XXX.XXX-XX
     * @param {string} cpf - CPF sem formatação
     * @returns {string} CPF formatado
     */
    static formatarCPF(cpf) {
        if (!cpf) return '';
        cpf = cpf.replace(/\D/g, '');
        return cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4');
    }

    /**
     * Remove formatação do CPF
     * @param {string} cpf - CPF formatado
     * @returns {string} CPF limpo (apenas números)
     */
    static limparCPF(cpf) {
        if (!cpf) return '';
        return cpf.replace(/\D/g, '');
    }

    /**
     * Aplica máscara no CPF enquanto o usuário digita
     * @param {HTMLInputElement} input - Elemento input
     */
    static aplicarMascaraCPF(input) {
        let valor = input.value.replace(/\D/g, '');

        if (valor.length <= 11) {
            valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
            valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
            valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        }

        input.value = valor;
    }

    /**
     * Formata data para exibição (DD/MM/YYYY)
     * @param {string} data - Data no formato ISO (YYYY-MM-DD)
     * @returns {string} Data formatada
     */
    static formatarData(data) {
        if (!data) return '';
        const [ano, mes, dia] = data.split('-');
        return `${dia}/${mes}/${ano}`;
    }

    /**
     * Converte data para formato ISO (YYYY-MM-DD)
     * @param {string} data - Data no formato DD/MM/YYYY
     * @returns {string} Data no formato ISO
     */
    static dataParaISO(data) {
        if (!data) return '';
        const [dia, mes, ano] = data.split('/');
        return `${ano}-${mes}-${dia}`;
    }

    /**
     * Formata valor monetário
     * @param {number} valor - Valor numérico
     * @returns {string} Valor formatado (R$ X.XXX,XX)
     */
    static formatarMoeda(valor) {
        if (valor === null || valor === undefined) return 'R$ 0,00';
        return `R$ ${Number(valor).toLocaleString('pt-BR', {
            minimumFractionDigits: 2,
            maximumFractionDigits: 2
        })}`;
    }
}

/**
 * Utilitários de Validação
 */
class ValidationUtils {
    /**
     * Valida CPF (validação básica de formato)
     * @param {string} cpf - CPF com ou sem formatação
     * @returns {boolean} True se válido
     */
    static validarCPF(cpf) {
        cpf = cpf.replace(/\D/g, '');

        // Verifica se tem 11 dígitos
        if (cpf.length !== 11) {
            return false;
        }

        // Verifica se todos os dígitos são iguais (ex: 111.111.111-11)
        if (/^(\d)\1{10}$/.test(cpf)) {
            return false;
        }

        return true;
    }

    /**
     * Valida se o valor é um número positivo
     * @param {*} valor - Valor a ser validado
     * @returns {boolean} True se válido
     */
    static validarNumeroPositivo(valor) {
        const num = parseFloat(valor);
        return !isNaN(num) && num > 0;
    }

    /**
     * Valida se a carga horária está dentro dos limites legais
     * @param {number} horas - Carga horária semanal
     * @returns {boolean} True se válido
     */
    static validarCargaHoraria(horas) {
        const h = parseInt(horas);
        return !isNaN(h) && h > 0 && h <= 44;
    }

    /**
     * Valida se a data não é futura
     * @param {string} data - Data no formato ISO (YYYY-MM-DD)
     * @returns {boolean} True se válido
     */
    static validarDataNaoFutura(data) {
        if (!data) return false;
        const dataInformada = new Date(data);
        const hoje = new Date();
        hoje.setHours(0, 0, 0, 0);
        return dataInformada <= hoje;
    }

    /**
     * Valida se a data de admissão é posterior à data de nascimento
     * @param {string} dataNascimento - Data de nascimento (YYYY-MM-DD)
     * @param {string} dataAdmissao - Data de admissão (YYYY-MM-DD)
     * @returns {boolean} True se válido
     */
    static validarDataAdmissao(dataNascimento, dataAdmissao) {
        if (!dataNascimento || !dataAdmissao) return false;
        const nascimento = new Date(dataNascimento);
        const admissao = new Date(dataAdmissao);
        return admissao > nascimento;
    }
}

/**
 * Utilitários de URL
 */
class URLUtils {
    /**
     * Obtém parâmetro da URL
     * @param {string} param - Nome do parâmetro
     * @returns {string|null} Valor do parâmetro ou null
     */
    static getParametro(param) {
        const urlParams = new URLSearchParams(window.location.search);
        return urlParams.get(param);
    }

    /**
     * Redireciona para uma URL
     * @param {string} url - URL de destino
     */
    static redirecionar(url) {
        window.location.href = url;
    }

    /**
     * Confirma ação com o usuário
     * @param {string} mensagem - Mensagem de confirmação
     * @returns {boolean} True se confirmado
     */
    static confirmar(mensagem) {
        return confirm(mensagem);
    }

    /**
     * Exibe alerta ao usuário
     * @param {string} mensagem - Mensagem do alerta
     */
    static alerta(mensagem) {
        alert(mensagem);
    }
}
