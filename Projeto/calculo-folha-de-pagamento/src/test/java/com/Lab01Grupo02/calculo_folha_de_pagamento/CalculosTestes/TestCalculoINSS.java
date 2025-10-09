package com.Lab01Grupo02.calculo_folha_de_pagamento.CalculosTestes;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.CalculoINSS;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class TestCalculoINSS {

    private final CalculoINSS calculadora = new CalculoINSS() {
        @Override
        public FolhaDePagamento calcularFolha(BigDecimal salarioBruto) {
            return null;
        }
    };

    @Test
    void deveCalcularINSSParaSalarioAtePrimeiraFaixa() {
 
        BigDecimal salario = new BigDecimal("1300.00");

  
        BigDecimal desconto = calculadora.calcular(salario);

 
        assertEquals(new BigDecimal("97.50"), desconto);
    }

    @Test
    void deveCalcularINSSParaSegundaFaixa() {
 
        BigDecimal salario = new BigDecimal("2000.00");

      
        BigDecimal desconto = calculadora.calcular(salario);

        assertEquals(new BigDecimal("160.47"), desconto);
    }

    @Test
    void deveCalcularINSSParaTerceiraFaixa() {
   
        BigDecimal salario = new BigDecimal("3500.00");


        BigDecimal desconto = calculadora.calcular(salario);

   
        assertEquals(new BigDecimal("323.46"), desconto);
    }

    @Test
    void deveCalcularINSSParaQuartaFaixaComTeto() {
  
        BigDecimal salario = new BigDecimal("7507.49");

  
        BigDecimal desconto = calculadora.calcular(salario);

        assertEquals(new BigDecimal("877.24"), desconto);
    }

    @Test
    void deveAplicarTetoParaSalarioAcimaDoLimite() {
      
        BigDecimal salario = new BigDecimal("9000.00");

    
        BigDecimal desconto = calculadora.calcular(salario);

     
        assertEquals(new BigDecimal("877.24"), desconto);
    }

    @Test
    void deveCalcularAliquotaEfetivaCorretamente() {
        BigDecimal salario = new BigDecimal("7507.49");

        BigDecimal aliquota = calculadora.calcularAliquotaEfetiva(salario);


        assertEquals(new BigDecimal("11.68"), aliquota.setScale(2, BigDecimal.ROUND_HALF_UP));
    }
}
