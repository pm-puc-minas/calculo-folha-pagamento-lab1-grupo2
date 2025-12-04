const API_FOLHA_URL = "http://localhost:9090/api/folhapagamento";

class FolhaPagamentoClient {

    static async gerarFolha(matricula, mesReferencia, diasFalta = 0) {

        const requestBody = {
            matricula: Number(matricula),
            mesReferencia: mesReferencia,
            diasFalta: Number(diasFalta)
        };

        const response = await fetch(API_FOLHA_URL, {
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
}
