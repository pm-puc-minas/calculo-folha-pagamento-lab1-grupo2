
const AppState = {
    todosFuncionarios: [],
    funcionariosFiltrados: []
};


async function inicializar() {

    if (typeof AuthService !== 'undefined') {
        AuthService.inicializar();
    }

    configurarEventos();
    await carregarFuncionarios();
}


function configurarEventos() {
    const searchInput = document.getElementById('searchInput');
    const sortSelect = document.getElementById('sortSelect');

    searchInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            filtrarFuncionarios();
        }
    });


    sortSelect.addEventListener('change', function() {
        filtrarFuncionarios();
    });

    searchInput.addEventListener('input', function() {
        filtrarFuncionarios();
    });
}


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


function filtrarFuncionarios() {
    const searchTerm = document.getElementById('searchInput').value.trim().toLowerCase();
    const sortBy = document.getElementById('sortSelect').value;

    let funcionariosFiltrados = [...AppState.todosFuncionarios];

    if (searchTerm) {
        funcionariosFiltrados = aplicarFiltroBusca(funcionariosFiltrados, searchTerm);
    }


    if (sortBy) {
        funcionariosFiltrados = aplicarOrdenacao(funcionariosFiltrados, sortBy);
    }

    AppState.funcionariosFiltrados = funcionariosFiltrados;
    exibirFuncionarios(funcionariosFiltrados);
}


function aplicarFiltroBusca(funcionarios, searchTerm) {
    return funcionarios.filter(func => {
        const nome = (func.nome || '').toLowerCase();
        const cpf = (func.cpf || '').replace(/\D/g, '');
        const matricula = (func.idPessoa || '').toString();
        const searchClean = searchTerm.replace(/\D/g, '');

        if (nome.includes(searchTerm)) {
            return true;
        }

        if (searchClean && cpf.includes(searchClean)) {
            return true;
        }

        if (matricula.includes(searchTerm)) {
            return true;
        }

        return false;
    });
}


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


function editarFuncionario(matricula) {
    URLUtils.redirecionar(`FormularioFuncionario.html?matricula=${matricula}`);
}


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

document.addEventListener('DOMContentLoaded', inicializar);
