package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



/**
 * NOTA: Classe no qual acontece toda a logica do sistema de calculo.
 * Ela implementa a interface ICalculadora e utiliza
 * uma lista de módulos que implementam CalculoFolha.
 */

//Não teria sido melhor ter importado a interface ao invés de usar esse implements sujo?
 @Service
public class CalculadoraService implements com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.ICalculadora{

    // NOTA: A lista de todos os módulos de cálculo que o sistema suporta.
    // Todos são do tipo genérico 'CalculoFolha', permitindo tratá-los da mesma forma.
    private final List<ICalculoFolha> modulosDeCalculo;

    public CalculadoraService() {
        // NOTA: Para adicionar um novo cálculo (ex: IRRF), basta criar a classe
        // e adicioná-la nesta lista. O resto do sistema se adapta automaticamente.
        this.modulosDeCalculo = Collections.unmodifiableList(Arrays.<ICalculoFolha>asList(
                new CalculoFGTS(),
                new CalculoINSS(),
                new CalculoIRRF(),
                CalculoBeneficio.valeTransporte(new BigDecimal("150.00")), // exemplo de VT
                CalculoBeneficio.valeAlimentacao(22, new BigDecimal("25.00")), // exemplo de VA
                new CalculoInsalubridade(),
                new CalculoPericulosidade()
        ));
    }

    @Override
    public List<ItemFolha> calcularFolhaCompleta(Funcionario funcionario) {
        List<ItemFolha> itensDaFolha = new ArrayList<>();

        // NOTA: Adiciona o Salário Bruto como o item base da folha.
        ItemFolha itemSalario = new ItemFolha();
        itemSalario.setDesc("Salário Bruto");
        itemSalario.setTipo("Provento");
        itemSalario.setValor(funcionario.getSalarioBruto());
        itensDaFolha.add(itemSalario);

        // NOTA: Este laço itera sobre a lista de especialistas.
        // Ele chama o método "calcular" de cada um, sem precisar saber
        // qual cálculo específico está sendo executado. Isso demonstra o poder da modularidade.
        for (ICalculoFolha modulo : this.modulosDeCalculo) {
            ItemFolha itemCalculado = modulo.calcular(funcionario);
            itensDaFolha.add(itemCalculado);
        }

        return itensDaFolha;
    }
}
