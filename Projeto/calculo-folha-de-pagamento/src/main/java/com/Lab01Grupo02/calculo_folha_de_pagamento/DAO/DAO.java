package com.Lab01Grupo02.calculo_folha_de_pagamento.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    // URL para apontar para o banco de dados
    private static final String URL = "jdbc:sqlite:rh_database.sqlite";

    // Não é necessario o uso de User ou Password no SQLite

    public static Connection getConnection() throws SQLException {
        //Carregar o drive do SQLite
        try{
            Class.forName("org.sqlite.JDBC");
        }catch(ClassNotFoundException e){
            System.err.println("Driver JDBC do SQLite não encontrado!");
            e.printStackTrace();
        }

        return DriverManager.getConnection(URL);
    }
}