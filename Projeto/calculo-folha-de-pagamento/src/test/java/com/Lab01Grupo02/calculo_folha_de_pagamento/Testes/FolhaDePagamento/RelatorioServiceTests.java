package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.FolhaDePagamento;

import com.Lab01Grupo02.calculo_folha_de_pagamento.DAO.DAO;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.RelatorioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RelatorioServiceTests {

    @Autowired
    private RelatorioService service;

    // 1. ATUALIZAMOS O SQL PARA CRIAR AS DUAS TABELAS E INSERIR OS DADOS
    private static final String SQL_SETUP_DATABASE = """
        -- Apagamos as tabelas na ordem inversa da criação por causa da chave estrangeira
        DROP TABLE IF EXISTS FOLHA_PAGAMENTO;
        DROP TABLE IF EXISTS FUNCIONARIO;

        -- Criamos uma tabela FUNCIONARIO simples para o teste funcionar
        CREATE TABLE FUNCIONARIO (
            Matricula INTEGER PRIMARY KEY,
            Nome TEXT NOT NULL
        );

        -- Usamos a sua estrutura de tabela FOLHA_PAGAMENTO
        CREATE TABLE FOLHA_PAGAMENTO (
            ID_Folha INTEGER PRIMARY KEY AUTOINCREMENT,
            Matricula_Funcionario INTEGER NOT NULL,
            MesReferencia DATE NOT NULL,
            SalarioBrutoCalculo DECIMAL(10, 2) NOT NULL,
            TotalProventos DECIMAL(10, 2) NOT NULL,
            TotalDescontos DECIMAL(10, 2) NOT NULL,
            SalarioLiquido DECIMAL(10, 2) NOT NULL,
            CONSTRAINT fk_folha_funcionario FOREIGN KEY (Matricula_Funcionario)
                REFERENCES FUNCIONARIO (Matricula) ON DELETE RESTRICT
        );

        -- Inserimos um funcionário ANTES de inserir a folha de pagamento
        INSERT INTO FUNCIONARIO (Matricula, Nome) VALUES (100, 'Iago Adrien');

        -- Inserimos uma folha de pagamento para o funcionário que acabamos de criar
        INSERT INTO FOLHA_PAGAMENTO (Matricula_Funcionario, MesReferencia, SalarioBrutoCalculo, TotalProventos, TotalDescontos, SalarioLiquido)
        VALUES (100, '2025-09-30', 6000.00, 6500.00, 1500.00, 5000.00);
    """;

    @BeforeEach
    void setupTestDatabase() {
        try (Connection conn = DAO.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(SQL_SETUP_DATABASE);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao preparar o banco de dados de teste.", e);
        }
    }

    @Test
    @DisplayName("Deve retornar a folha de pagamento de teste do banco de dados")
    void buscarTodasAsFolhas_DeveRetornarListaComSucesso() {
        // Arrange
        //RelatorioService service = new RelatorioService();

        // Act
        List<FolhaDePagamento> resultado = service.buscarTodasAsFolhas();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());

        // Verifica os dados do registro que inserimos
        FolhaDePagamento folha = resultado.get(0);
        assertEquals(100, folha.getMatricula());
        assertEquals(LocalDate.of(2025, 9, 30), folha.getMesReferencia());

        // Para comparar BigDecimal, use o método compareTo ou crie um novo BigDecimal
        assertEquals(0, new BigDecimal("6000.00").compareTo(folha.getSalarioBruto()));
        assertEquals(0, new BigDecimal("5000.00").compareTo(folha.getSalarioLiquido()));
    }
}