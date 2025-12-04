// No script do final da página
function cancelarCadastro() {
    if(confirm("Deseja cancelar?")) {
        window.location.href = "funcionarios.html"; // Volta para a lista
    }
}

document.getElementById('funcionarioForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert("Salvo com sucesso!");
    window.location.href = "funcionarios.html"; // Volta para a lista
});

// Dentro da função iniciarCalculo(), no setTimeout final:
setTimeout(() => {
    alert("Redirecionando para os Relatórios...");
    window.location.href = "relatorio.html"; // Vai para a tela de relatórios
}, 1000);