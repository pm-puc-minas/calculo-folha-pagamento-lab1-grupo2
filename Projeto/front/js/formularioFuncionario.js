
const FormState = {
    modoEdicao: false,
    matriculaAtual: null
};


async function inicializar() {
    if (typeof AuthService !== 'undefined') {
        AuthService.inicializar();
    }

    configurarEventos();
    verificarModoEdicao();
}

function configurarEventos() {
    const form = document.getElementById('funcionarioForm');
    const cpfInput = document.getElementById('cpf');

    // Adicionar máscara de CPF
    cpfInput.addEventListener('input', function() {
        FormatUtils.aplicarMascaraCPF(this);
    });

    // Evento de submit do formulário
    form.addEventListener('submit', salvarFuncionario);
}


function verificarModoEdicao() {
    const matricula = URLUtils.getParametro('matricula');

    if (matricula) {
        FormState.modoEdicao = true;
        FormState.matriculaAtual = parseInt(matricula);
        carregarFuncionario(FormState.matriculaAtual);
    }
}

async function carregarFuncionario(matricula) {
    try {
        const funcionario = await FuncionarioClient.buscarPorMatricula(matricula);

        preencherFormulario(funcionario);
        atualizarTituloParaEdicao();

    } catch (error) {
        console.error('Erro ao carregar funcionário:', error);
        URLUtils.alerta('Erro ao carregar dados do funcionário. Redirecionando para a lista...');
        URLUtils.redirecionar('gerenciamentoFuncionarioRH.html');
    }
}


function preencherFormulario(funcionario) {
    // Dados pessoais
    document.getElementById('nome').value = funcionario.nome || '';
    document.getElementById('cpf').value = FormatUtils.formatarCPF(funcionario.cpf) || '';
    document.getElementById('nascimento').value = funcionario.dataNascimento || '';

    // Dados do contrato
    document.getElementById('cargo').value = funcionario.cargo || '';
    document.getElementById('admissao').value = funcionario.dataAdmissao || '';
    document.getElementById('salario').value = funcionario.salarioBruto || '';
    document.getElementById('cargaHoraria').value = funcionario.cargaHorariaSemanal || 40;
    document.getElementById('insalubridade').value = funcionario.grauInsalubridade || 'NENHUM';
    document.getElementById('periculosidade').value = funcionario.possuiPericulosidade ? 'true' : 'false';
}


function atualizarTituloParaEdicao() {
    document.querySelector('.form-header h2').textContent = 'Editar Funcionário';
    document.querySelector('.form-header p').textContent = 'Atualize os dados do colaborador.';
    document.querySelector('.btn-save').textContent = 'Atualizar Funcionário';
}

function coletarDadosFormulario() {
    return {
        nome: document.getElementById('nome').value.trim(),
        cpf: document.getElementById('cpf').value,
        dataNascimento: document.getElementById('nascimento').value,
        cargo: document.getElementById('cargo').value.trim(),
        dataAdmissao: document.getElementById('admissao').value,
        salario: document.getElementById('salario').value,
        cargaHoraria: document.getElementById('cargaHoraria').value,
        insalubridade: document.getElementById('insalubridade').value,
        periculosidade: document.getElementById('periculosidade').value
    };
}


function validarFormulario(dados) {
    if (!ValidationUtils.validarCPF(dados.cpf)) {
        return {
            valido: false,
            mensagem: 'CPF inválido! Por favor, verifique o número digitado.',
            campo: 'cpf'
        };
    }
    if (!ValidationUtils.validarNumeroPositivo(dados.salario)) {
        return {
            valido: false,
            mensagem: 'O salário deve ser maior que zero!',
            campo: 'salario'
        };
    }

    if (!ValidationUtils.validarCargaHoraria(dados.cargaHoraria)) {
        return {
            valido: false,
            mensagem: 'A carga horária deve estar entre 1 e 44 horas!',
            campo: 'cargaHoraria'
        };
    }

    if (!ValidationUtils.validarDataNaoFutura(dados.dataNascimento)) {
        return {
            valido: false,
            mensagem: 'A data de nascimento não pode ser futura!',
            campo: 'nascimento'
        };
    }

    if (!ValidationUtils.validarDataNaoFutura(dados.dataAdmissao)) {
        return {
            valido: false,
            mensagem: 'A data de admissão não pode ser futura!',
            campo: 'admissao'
        };
    }

    if (!ValidationUtils.validarDataAdmissao(dados.dataNascimento, dados.dataAdmissao)) {
        return {
            valido: false,
            mensagem: 'A data de admissão deve ser posterior à data de nascimento!',
            campo: 'admissao'
        };
    }

    return { valido: true };
}

function montarDTO(dados) {
    return {
        nome: dados.nome,
        cpf: FormatUtils.limparCPF(dados.cpf),
        dataNascimento: dados.dataNascimento,
        cargo: dados.cargo,
        dataAdmissao: dados.dataAdmissao,
        salarioBruto: dados.salario,
        grauInsalubridade: dados.insalubridade,
        cargaHorariaSemanal: parseInt(dados.cargaHoraria),
        possuiPericulosidade: dados.periculosidade === 'true'
    };
}


async function salvarFuncionario(event) {
    event.preventDefault();

    const dados = coletarDadosFormulario();

    const validacao = validarFormulario(dados);
    if (!validacao.valido) {
        URLUtils.alerta(validacao.mensagem);
        if (validacao.campo) {
            document.getElementById(validacao.campo).focus();
        }
        return;
    }
    const funcionarioDTO = montarDTO(dados);

    try {
        if (FormState.modoEdicao) {
            await FuncionarioClient.atualizar(FormState.matriculaAtual, funcionarioDTO);
            URLUtils.alerta('Funcionário atualizado com sucesso!');
        } else {
            await FuncionarioClient.criar(funcionarioDTO);
            URLUtils.alerta('Funcionário cadastrado com sucesso!');
        }

        URLUtils.redirecionar('gerenciamentoFuncionarioRH.html');

    } catch (error) {
        console.error('Erro ao salvar funcionário:', error);
        tratarErroSalvamento(error);
    }
}

function tratarErroSalvamento(error) {
    let mensagemErro = 'Erro ao salvar funcionário. Tente novamente.';

    if (error.message.includes('CPF')) {
        mensagemErro = 'CPF já cadastrado no sistema!';
    } else if (error.message.includes('404')) {
        mensagemErro = 'Funcionário não encontrado!';
    } else if (error.message.includes('idade')) {
        mensagemErro = 'O funcionário deve ter no mínimo 16 anos de idade!';
    } else if (error.message) {
        mensagemErro = error.message;
    }

    URLUtils.alerta(mensagemErro);
}

function cancelarCadastro() {
    if (URLUtils.confirmar("Deseja cancelar? Os dados não salvos serão perdidos.")) {
        URLUtils.redirecionar("gerenciamentoFuncionarioRH.html");
    }
}

document.addEventListener('DOMContentLoaded', inicializar);
