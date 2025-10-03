package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE;

import com.Lab01Grupo02.calculo_folha_de_pagamento.DAO.DAO;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class RelatorioService {
    //private FolhaDePagamento folhaDePagamento = new FolhaDePagamento();

    private Gson gson = new Gson();

    /**
     * MÉTODO DA ROTA (API)
     * Ponto de entrada que lida com a requisição web.
     */
    public String listarTodasAsFolhas(Request request, Response response) {
        // 1. Chama o método para buscar os dados no banco
        List<FolhaDePagamento> folhas = buscarTodasAsFolhas();

        // 2. Define o tipo de conteúdo da resposta como JSON
        response.type("application/json");

        // 3. Verifica se a lista não está vazia
        if (folhas != null && !folhas.isEmpty()) {
            response.status(200); // 200 OK
            // 4. Converte a lista de objetos Java para uma String no formato JSON
            return gson.toJson(folhas);
        } else {
            response.status(404); // 404 Not Found
            return "{\"message\": \"Nenhuma folha de pagamento encontrada.\"}";
        }
    }


    /**
     * MÉTODO DE ACESSO AO BANCO DE DADOS (DAO)
     * Especialista em buscar todas as folhas de pagamento na tabela.
     */
    public List<FolhaDePagamento> buscarTodasAsFolhas() { // Definir como Public para efetuar os testes
        // Cria uma lista vazia para armazenar os resultados
        List<FolhaDePagamento> listaFolhas = new ArrayList<>();
        // SQL para selecionar TUDO da tabela. Adapte o nome "folha_de_pagamento" se for diferente.
        String sql = "SELECT * FROM FOLHA_PAGAMENTO";

        try (Connection conn = DAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();


            // Loop de busca de resultado
            while (rs.next()) {
                // Para cada linha encontrada...
                FolhaDePagamento folhaDePagamento = new FolhaDePagamento();

                // Verifique se TODAS estas linhas existem e estão corretas
                folhaDePagamento.setId_Folha(rs.getInt("ID_Folha"));

                // Criar o objeto da folha de pagamento
                folhaDePagamento.setMatricula(rs.getInt("matricula_Funcionario"));
                folhaDePagamento.setMesReferencia(LocalDate.parse(rs.getString("MesReferencia")));
                folhaDePagamento.setSalarioBruto(BigDecimal.valueOf(rs.getDouble("SalarioBrutoCalculo")));
                folhaDePagamento.setTotalProvento(rs.getBigDecimal("TotalProventos"));
                folhaDePagamento.setTotalDesconto(rs.getBigDecimal("TotalDescontos"));
                folhaDePagamento.setSalarioLiquido(rs.getBigDecimal("SalarioLiquido"));

                // Adiciona o objeto preenchido a lista
                listaFolhas.add(folhaDePagamento);
            }

            rs.close();
        } catch (Exception e) {
            System.out.println("Erro ao buscar as folhas de pagamento no banco:");
            e.printStackTrace();
            return null; // Retorna null em caso de erro de banco
        }

        // Retorna a lista completa
        return listaFolhas;
    }
}
