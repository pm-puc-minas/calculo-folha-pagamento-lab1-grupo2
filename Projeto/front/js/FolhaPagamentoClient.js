class FolhaPagamentoClient {

    static async gerarFolha(matricula, mesReferencia, diasFalta = 0) {
        const requestBody = {
            matricula: Number(matricula),
            mesReferencia: mesReferencia,
            diasFalta: Number(diasFalta)
        };

        const response = await fetch(`${API_BASE_URL}/folhapagamento`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            const error = await response.json().catch(() => null);
            throw new Error(error?.message || `Erro ${response.status}`);
        }

        return await response.json();
    }

    static async buscarTodas() {
        const response = await fetch(`${API_BASE_URL}/folhapagamento`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) {
            const error = await response.json().catch(() => null);
            throw new Error(error?.message || `Erro ao buscar folhas: ${response.status}`);
        }

        return await response.json();
    }

    static async buscarPorId(id) {
        const response = await fetch(`${API_BASE_URL}/folhapagamento/${id}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) {
            const error = await response.json().catch(() => null);
            throw new Error(error?.message || `Erro ao buscar folha: ${response.status}`);
        }

        return await response.json();
    }

    static async buscarPorFuncionario(matricula) {
        const response = await fetch(`${API_BASE_URL}/folhapagamento/funcionario/${matricula}`, {
            method: "GET",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) {
            const error = await response.json().catch(() => null);
            throw new Error(error?.message || `Erro ao buscar folhas do funcionário: ${response.status}`);
        }

        return await response.json();
    }

    static async deletarFolha(id) {
        const response = await fetch(`${API_BASE_URL}/folhapagamento/${id}`, {
            method: "DELETE",
            headers: { "Content-Type": "application/json" }
        });

        if (!response.ok) {
            const error = await response.json().catch(() => null);
            throw new Error(error?.message || `Erro ao deletar folha: ${response.status}`);
        }
    }
}
