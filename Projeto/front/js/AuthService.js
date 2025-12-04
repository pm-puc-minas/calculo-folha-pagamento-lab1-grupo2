/**
 * AuthService.js
 * Serviço de gerenciamento de autenticação e controle de acesso
 * Responsável por gerenciar sessão do usuário e controle de permissões
 * Não faz chamadas diretas à API - usa AuthClient para isso
 */

class AuthService {
    /**
     * Chave para armazenar dados do usuário no localStorage
     */
    static STORAGE_KEY = 'usuario_logado';

    /**
     * Tipos de usuário do sistema
     */
    static TIPO = {
        RH: 'RH',
        FUNCIONARIO: 'FUNCIONARIO'
    };

    /**
     * Efetua login e armazena dados do usuário
     * @param {string} identificador - Email (RH) ou CPF (Funcionário)
     * @param {string} senha - Senha do usuário
     * @returns {Promise<Object>} Dados do usuário autenticado
     */
    static async efetuarLogin(identificador, senha) {
        try {
            // Chama o AuthClient para fazer login na API
            const dadosUsuario = await AuthClient.login(identificador, senha);

            // Armazena os dados do usuário no localStorage
            this.salvarUsuario(dadosUsuario);

            return dadosUsuario;
        } catch (error) {
            throw error;
        }
    }

    /**
     * Salva dados do usuário no localStorage
     * @param {Object} usuario - Dados do usuário
     */
    static salvarUsuario(usuario) {
        localStorage.setItem(this.STORAGE_KEY, JSON.stringify(usuario));
    }

    /**
     * Obtém dados do usuário logado
     * @returns {Object|null} Dados do usuário ou null se não estiver logado
     */
    static getUsuarioLogado() {
        const dados = localStorage.getItem(this.STORAGE_KEY);
        return dados ? JSON.parse(dados) : null;
    }

    /**
     * Verifica se existe um usuário logado
     * @returns {boolean} True se há usuário logado
     */
    static estaLogado() {
        return this.getUsuarioLogado() !== null;
    }

    /**
     * Obtém o tipo do usuário logado
     * @returns {string|null} Tipo do usuário (RH ou FUNCIONARIO) ou null
     */
    static getTipoUsuario() {
        const usuario = this.getUsuarioLogado();
        return usuario ? usuario.tipo : null;
    }

    /**
     * Verifica se o usuário logado é do tipo RH
     * @returns {boolean} True se for RH
     */
    static isUsuarioRH() {
        return this.getTipoUsuario() === this.TIPO.RH;
    }

    /**
     * Verifica se o usuário logado é do tipo FUNCIONARIO
     * @returns {boolean} True se for Funcionário
     */
    static isUsuarioFuncionario() {
        return this.getTipoUsuario() === this.TIPO.FUNCIONARIO;
    }

    /**
     * Efetua logout removendo dados do localStorage
     */
    static logout() {
        localStorage.removeItem(this.STORAGE_KEY);
    }

    /**
     * Redireciona para a página apropriada com base no tipo de usuário
     */
    static redirecionarParaHome() {
        if (this.isUsuarioRH()) {
            window.location.href = 'home.html';
        } else if (this.isUsuarioFuncionario()) {
            window.location.href = 'inicioFuncionario.html';
        } else {
            window.location.href = 'login.html';
        }
    }

    /**
     * Verifica se a página atual requer permissão RH
     * Páginas com sufixo "RH" ou específicas exclusivas para RH
     * @returns {boolean} True se a página requer permissão RH
     */
    static paginaRequerRH() {
        const urlAtual = window.location.pathname;
        return urlAtual.includes('RH.html') ||
               urlAtual.includes('home.html') ||
               urlAtual.includes('FolhaPagamento.html') ||
               urlAtual.toLowerCase().includes('rh');
    }

    /**
     * Retorna a URL do relatório apropriado para o tipo de usuário
     * @returns {string} URL do relatório (relatorioRH.html ou relatorio.html)
     */
    static getRelatorioUrl() {
        return this.isUsuarioRH() ? 'relatorioRH.html' : 'relatorio.html';
    }

    /**
     * Protege a página atual verificando permissões
     * Redireciona para login se não estiver logado
     * Redireciona para página de funcionário se não tiver permissão RH
     */
    static protegerPagina() {
        // Verifica se está logado
        if (!this.estaLogado()) {
            alert('Você precisa estar logado para acessar esta página');
            window.location.href = 'login.html';
            return;
        }

        // Se a página requer RH e o usuário não é RH
        if (this.paginaRequerRH() && !this.isUsuarioRH()) {
            alert('Acesso negado. Esta página é exclusiva para RH');
            window.location.href = 'inicioFuncionario.html';
            return;
        }
    }

    /**
     * Obtém o nome do usuário logado
     * @returns {string|null} Nome do usuário
     */
    static getNomeUsuario() {
        const usuario = this.getUsuarioLogado();
        return usuario ? usuario.nome : null;
    }

    /**
     * Obtém o ID do usuário logado
     * @returns {number|null} ID do usuário
     */
    static getIdUsuario() {
        const usuario = this.getUsuarioLogado();
        return usuario ? usuario.idUsuario : null;
    }

    /**
     * Obtém a matrícula do funcionário logado (se aplicável)
     * @returns {number|null} Matrícula ou null
     */
    static getMatricula() {
        const usuario = this.getUsuarioLogado();
        return usuario ? usuario.matricula : null;
    }

    /**
     * Oculta elementos da página que requerem permissão RH
     * Adiciona classe CSS ou display:none em elementos com atributo data-rh-only
     */
    static ocultarElementosRH() {
        if (!this.isUsuarioRH()) {
            // Oculta todos os elementos com atributo data-rh-only="true"
            const elementosRH = document.querySelectorAll('[data-rh-only="true"]');
            elementosRH.forEach(elemento => {
                elemento.style.display = 'none';
            });

            // Oculta links para páginas RH
            const linksRH = document.querySelectorAll('a[href*="RH.html"], a[href*="home.html"], a[href*="FolhaPagamento.html"]');
            linksRH.forEach(link => {
                link.style.display = 'none';
            });
        }
    }

    /**
     * Atualiza links de navegação dinâmicos baseados no tipo de usuário
     * Deve ser chamado após o DOM estar carregado
     */
    static atualizarLinksRelatorio() {
        const linksRelatorio = document.querySelectorAll('a[data-relatorio-link="true"]');
        const urlRelatorio = this.getRelatorioUrl();

        linksRelatorio.forEach(link => {
            link.href = urlRelatorio;
        });
    }

    /**
     * Inicializa o sistema de autenticação na página
     * Deve ser chamado no carregamento de cada página protegida
     */
    static inicializar() {
        // Protege a página
        this.protegerPagina();

        // Oculta elementos RH se necessário
        this.ocultarElementosRH();

        // Atualiza links de relatório dinamicamente
        this.atualizarLinksRelatorio();

        // Adiciona informações do usuário na interface (se houver elemento apropriado)
        const elementoNomeUsuario = document.getElementById('nome-usuario');
        if (elementoNomeUsuario) {
            elementoNomeUsuario.textContent = this.getNomeUsuario();
        }

        const elementoTipoUsuario = document.getElementById('tipo-usuario');
        if (elementoTipoUsuario) {
            elementoTipoUsuario.textContent = this.isUsuarioRH() ? 'RH' : 'Funcionário';
        }
    }
}
