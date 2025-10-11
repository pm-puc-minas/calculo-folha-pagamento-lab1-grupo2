package test.java.com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.*;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NOTA: Esta é a classe de teste dedicada exclusivamente ao módulo CalculoFGTS.
 */
class CalculoFGTSTest {

    // A instância do nosso módulo que será testada.
    private CalculoFGTS calculoFGTS;

    // NOTA: O método com @BeforeEach roda ANTES de cada teste.
    // Isso garante que cada teste comece com uma instância "limpa" do objeto,
    // evitando que um teste influencie o outro.
    @BeforeEach
    void setUp() {
        calculoFGTS = new CalculoFGTS();
    }

    @Test
    @DisplayName("Deve calcular o FGTS corretamente para um salário válido")
    void deveCalcularFgtsParaSalarioValido() {
        // Arrange: Criamos o cenário do teste.
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("5000.00"));

        // O valor que esperamos como resultado (5000 * 0.08)
        BigDecimal valorEsperado = new BigDecimal("400.00").setScale(2, RoundingMode.HALF_UP);

        // Act: Executamos o método que queremos testar.
        ItemFolha resultado = calculoFGTS.calcular(funcionario);

        // Assert: Verificamos se o resultado foi o esperado.
        assertNotNull(resultado, "O resultado não deveria ser nulo.");
        assertEquals("FGTS", resultado.getDesc(), "A descrição do item está incorreta.");
        assertEquals("Provento", resultado.getTipo(), "O tipo do item deveria ser 'Provento'.");

        assertEquals(0, valorEsperado.compareTo(resultado.getValor()), "O valor do FGTS calculado está incorreto.");
    }

    @Test
    @DisplayName("Deve retornar FGTS zerado para um funcionário com salário bruto nulo")
    void deveRetornarZeroParaSalarioNulo() {
        // Arrange
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(null); // Cenário de teste com salário nulo
        BigDecimal valorEsperado = BigDecimal.ZERO;

        // Act
        ItemFolha resultado = calculoFGTS.calcular(funcionario);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()), "O valor do FGTS deveria ser zero para salário nulo.");
    }

    @Test
    @DisplayName("Deve retornar FGTS zerado para um objeto funcionário nulo")
    void deveLidarComFuncionarioNuloSemLancarExcecao() {
        // Arrange
        Funcionario funcionario = null; // Testando o "null safety" do método
        BigDecimal valorEsperado = BigDecimal.ZERO;

        // Act
        ItemFolha resultado = calculoFGTS.calcular(funcionario);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()), "O valor do FGTS deveria ser zero quando o funcionário é nulo.");
    }

    @Test
    @DisplayName("Deve calcular o FGTS corretamente para um salário com casas decimais")
    void deveCalcularFgtsParaSalarioComDecimais() {
        // Arrange
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("2550.75"));

        // Valor esperado: 2550.75 * 0.08 = 204.06
        BigDecimal valorEsperado = new BigDecimal("204.06");

        // Act
        ItemFolha resultado = calculoFGTS.calcular(funcionario);

        // Assert
        assertNotNull(resultado);
        assertEquals(0, valorEsperado.compareTo(resultado.getValor()), "O cálculo para salário com decimais falhou.");
    }
}