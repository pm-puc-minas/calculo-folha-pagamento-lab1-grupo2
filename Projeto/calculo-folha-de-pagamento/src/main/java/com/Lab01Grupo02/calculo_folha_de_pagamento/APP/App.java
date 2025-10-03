// Teste de conexão Com o BD
package com.Lab01Grupo02.calculo_folha_de_pagamento.APP;

import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.RelatorioService;

import static spark.Spark.*;

public class App {
    public static void main(String[] args) {
       // Configurando a Rota do servidor.
        port(8080);

        //Intanciar o service
        RelatorioService relatorio  = new RelatorioService();

        // Configuração das rotas GET
        get("/listarTodasAsFolhas", relatorio::listarTodasAsFolhas);
    }
}