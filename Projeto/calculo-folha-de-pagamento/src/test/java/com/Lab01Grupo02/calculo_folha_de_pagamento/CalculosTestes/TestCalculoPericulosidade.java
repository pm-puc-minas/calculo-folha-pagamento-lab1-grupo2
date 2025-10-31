package test.java.com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.CalculoPericulosidade; 
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
class TestCalculoPericulosidade {

    private final CalculoPericulosidade calculadora = new CalculoPericulosidade();
    private static final int ESCALA = 2;

    @Test
    void deveCalcularPericulosidadeFuncionarioApto_N1() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("2500.00"));
        funcionario.setTemPericulosidade(true); 
        
        ItemFolha itemAdicional = calculadora.calcular(funcionario);

        BigDecimal esperado = new BigDecimal("750.00").setScale(ESCALA);

        assertEquals(esperado, itemAdicional.getValor());
        assertEquals("Provento", itemAdicional.getTipo());
    }

    @Test
    void deveCalcularPericulosidadeFuncionarioNaoApto_N2() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("3000.00"));
        funcionario.setTemPericulosidade(false); 

        ItemFolha itemAdicional = calculadora.calcular(funcionario);

        BigDecimal esperado = BigDecimal.ZERO.setScale(ESCALA);

        assertEquals(esperado, itemAdicional.getValor());
        assertEquals("Provento", itemAdicional.getTipo());
    }

    @Test
    void deveCalcularPericulosidadeSalarioMinimo_N3() {
        Funcionario funcionario = new Funcionario();
        funcionario.setSalarioBruto(new BigDecimal("1380.60"));
        funcionario.setTemPericulosidade(true); 

        ItemFolha itemAdicional = calculadora.calcular(funcionario);

        BigDecimal esperado = new BigDecimal("414.18").setScale(ESCALA);

        assertEquals(esperado, itemAdicional.getValor());
        assertEquals("Provento", itemAdicional.getTipo());
    }
}
 */
