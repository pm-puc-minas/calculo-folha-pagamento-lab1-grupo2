// Teste de conexão Com o BD
package com.Lab01Grupo02.calculo_folha_de_pagamento.APP;

import com.Lab01Grupo02.calculo_folha_de_pagamento.DAO.DAO;

import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try(Connection conn = DAO.getConnection()){
            if(conn == null){
                System.out.println("Banco de Dados Concetado");
            }
        }catch (SQLException e){
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}