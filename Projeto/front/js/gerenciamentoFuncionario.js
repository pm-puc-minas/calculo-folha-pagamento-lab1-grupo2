/**
 * gerenciamentoFuncionario.js
 * Lógica específica da página de gerenciamento de funcionários
 * Responsável por: listagem, busca, filtros, ordenação e exclusão
 */

// Estado da aplicação
const AppState = {
    todosFuncionarios: [],
    funcionariosFiltrados: []
};

/**
 * Inicializa a página
 */
async function inicializar() {
    configurarEventos();
    await carregarFuncionarios();
}

/**
 * Configura eventos dos elementos da página
 */
function configurarEventos() {
    const searchInput = document.getElementById('searchInput');
    const sortSelect = document.getElementById('sortSelect');

    // Evento de Enter no campo de busca
    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            filtrarFuncionarios();
        }
    });

    // Evento de mudança no select de ordenação
    sortSelect.addEventListener('change', function() {
        filtrarFuncionarios();
    });

    // Evento de input para busca em tempo real
    searchInput.addEventListener('input', function() {
        filtrarFuncionarios();
    });
}

/**
 * Carrega todos os funcionários da API
 */
async function carregarFuncionarios() {
    try {
        AppState.todosFuncionarios = await FuncionarioClient.buscarTodos();
        AppState.funcionariosFiltrados = [...AppState.todosFuncionarios];
        exibirFuncionarios(AppState.funcionariosFiltrados);

        // Limpar filtros
        document.getElementById('searchInput').value = '';
        document.getElementById('sortSelect').value = '';
    } catch (error) {
        console.error('Erro ao carregar funcionários:', error);
        URLUtils.alerta('Erro ao carregar funcionários. Verifique se o backend está rodando na porta 9090.');
    }
}

/**
 * Exibe funcionários na tabela
 * @param {Array} funcionarios - Lista de funcionários
 */
function exibirFuncionarios(funcionarios) {
    const tbody = document.getElementById('tabela-funcionarios');
    tbody.innerHTML = '';

    if (funcionarios.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align: center;">Nenhum funcionário encontrado</td></tr>';
        return;
    }

    funcionarios.forEach(func => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${func.idPessoa || ''}</td>
            <td>${func.nome || ''}</td>
            <td>${FormatUtils.formatarCPF(func.cpf)}</td>
            <td>-</td>
            <td>${func.cargo || ''}</td>
            <td class="actions-cell">
                <button class="btn-action btn-edit" onclick="editarFuncionario(${func.idPessoa})">Editar</button>
                <button class="btn-action btn-delete" onclick="excluirFuncionario(${func.idPessoa})">Excluir</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

/**
 * Filtra e ordena funcionários
 */
function filtrarFuncionarios() {
    const searchTerm = document.getElementById('searchInput').value.trim().toLowerCase();
    const sortBy = document.getElementById('sortSelect').value;

    // Copia o array para não modificar o original
    let funcionariosFiltrados = [...AppState.todosFuncionarios];

    // Aplica filtro de busca
    if (searchTerm) {
        funcionariosFiltrados = aplicarFiltroBusca(funcionariosFiltrados, searchTerm);
    }

    // Aplica ordenação
    if (sortBy) {
        funcionariosFiltrados = aplicarOrdenacao(funcionariosFiltrados, sortBy);
    }

    AppState.funcionariosFiltrados = funcionariosFiltrados;
    exibirFuncionarios(funcionariosFiltrados);
}

/**
 * Aplica filtro de busca por nome, CPF ou matrícula
 * @param {Array} funcionarios - Lista de funcionários
 * @param {string} searchTerm - Termo de busca
 * @returns {Array} Funcionários filtrados
 */
function aplicarFiltroBusca(funcionarios, searchTerm) {
    return funcionarios.filter(func => {
        const nome = (func.nome || '').toLowerCase();
        const cpf = (func.cpf || '').replace(/\D/g, '');
        const matricula = (func.idPessoa || '').toString();
        const searchClean = searchTerm.replace(/\D/g, '');

        // Busca por nome
        if (nome.includes(searchTerm)) {
            return true;
        }

        // Busca por CPF (apenas números)
        if (searchClean && cpf.includes(searchClean)) {
            return true;
        }

        // Busca por matrícula
        if (matricula.includes(searchTerm)) {
            return true;
        }

        return false;
    });
}

/**
 * Aplica ordenação na lista de funcionários
 * @param {Array} funcionarios - Lista de funcionários
 * @param {string} sortBy - Campo para ordenar
 * @returns {Array} Funcionários ordenados
 */
function aplicarOrdenacao(funcionarios, sortBy) {
    return [...funcionarios].sort((a, b) => {
        if (sortBy === 'nome') {
            return (a.nome || '').localeCompare(b.nome || '');
        } else if (sortBy === 'matricula') {
            return (a.idPessoa || 0) - (b.idPessoa || 0);
        } else if (sortBy === 'cargo') {
            return (a.cargo || '').localeCompare(b.cargo || '');
        }
        return 0;
    });
}

/**
 * Redireciona para a página de edição do funcionário
 * @param {number} matricula - Matrícula do funcionário
 */
function editarFuncionario(matricula) {
    URLUtils.redirecionar(`FormularioFuncionario.html?matricula=${matricula}`);
}

/**
 * Exclui um funcionário após confirmação
 * @param {number} matricula - Matrícula do funcionário
 */
async function excluirFuncionario(matricula) {
    if (!URLUtils.confirmar("Tem certeza que deseja excluir este funcionário do sistema?")) {
        return;
    }

    try {
        await FuncionarioClient.excluir(matricula);
        URLUtils.alerta('Funcionário excluído com sucesso!');
        await carregarFuncionarios();
    } catch (error) {
        console.error('Erro ao excluir funcionário:', error);
        URLUtils.alerta('Erro ao excluir funcionário. Tente novamente.');
    }
}

// Inicializa quando o DOM estiver pronto
document.addEventListener('DOMContentLoaded', inicializar);
