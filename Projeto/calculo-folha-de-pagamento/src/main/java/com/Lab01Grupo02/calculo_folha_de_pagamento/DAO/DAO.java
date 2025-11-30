package com.Lab01Grupo02.calculo_folha_de_pagamento.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

    // 1. Atributo estático que vai guardar a instância única da classe (Singleton)
    private static DAO instance;

    // Atributo para manter a conexão aberta e ser reutilizada
    private Connection connection;

    private static final String URL = "jdbc:sqlite:rh_database.sqlite";

    // 2. Construtor privado para impedir que façam "new DAO()" fora daqui
    private DAO() {
        try {
            // Carregar o driver apenas uma vez na criação da instância
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver JDBC do SQLite não encontrado!");
            e.printStackTrace();
        }
    }

    // 3. Método público estático para fornecer o ponto de acesso global
    public static synchronized DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    // Agora este método não é mais estático. Você chama ele a partir da instância.
    public Connection getConnection() throws SQLException {
        // Verifica se a conexão está fechada ou nula para abrir uma nova
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL);
        }
        // Se já estiver aberta, retorna a mesma conexão (Singleton da conexão)
        return connection;
    }
}