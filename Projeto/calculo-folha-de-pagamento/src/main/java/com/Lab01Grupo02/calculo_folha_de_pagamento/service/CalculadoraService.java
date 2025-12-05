package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class CalculadoraService implements ICalculadora {

    // NOTA: A lista de todos os módulos de cálculo que o sistema suporta.
    // Todos são do tipo genérico 'CalculoFolha', permitindo tratá-los da mesma forma.
    private final List<ICalculoFolha> modulosDeCalculo;

    // Definição padrão de dias no mês para cálculo de salário
    private static final int DIAS_MES_COMERCIAL = 30;

    public CalculadoraService() {
        // NOTA: Para adicionar um novo cálculo (ex: IRRF), basta criar a classe
        // e adicioná-la nesta lista. O resto do sistema se adapta automaticamente.
        this.modulosDeCalculo = Collections.unmodifiableList(Arrays.<ICalculoFolha>asList(
                // new CalculoFGTS(), // Removido: FGTS não deve entrar na folha
                new CalculoINSS(),
                new CalculoIRRF(),
                CalculoBeneficio.valeTransporte(new BigDecimal("150.00")), // exemplo de VT
                CalculoBeneficio.valeAlimentacao(22, new BigDecimal("25.00")), // exemplo de VA
                //new CalculoInsalubridade(),
                new CalculoPericulosidade()
        ));
    }

    /**
     * Calcular a folha de pagamento completa, agora incluindo o desconto por faltas.
     */
    @Override
    public List<ItemFolha> calcularFolhaCompleta(Funcionario funcionario, int diasFalta) {
        List<ItemFolha> itensDaFolha = new ArrayList<>();

        // NOTA: Adiciona o Salário Bruto como o item base da folha.
        ItemFolha itemSalario = new ItemFolha();
        itemSalario.setDesc("Salário Bruto");
        itemSalario.setTipo("PROVENTO");
        itemSalario.setValor(funcionario.getSalarioBruto());
        itensDaFolha.add(itemSalario);

        // -- NOVA LOGICA DE FALTAS --
        // Calcula o desconto por faltas e adiciona à folha.
        if (diasFalta > 0) {
            // Calcula o valor de 1 dia de trabalho
            BigDecimal valorDia = funcionario.getSalarioBruto()
                    .divide(new BigDecimal(DIAS_MES_COMERCIAL), 2, RoundingMode.HALF_UP);

            // Calcula o desconto total
            BigDecimal descontoFaltas = valorDia.multiply(new BigDecimal(diasFalta));

            // Adiciona o item de desconto
            ItemFolha itemFaltas = new ItemFolha();
            itemFaltas.setDesc("Faltas (DSR) " + diasFalta + "d");
            itemFaltas.setTipo("DESCONTO");
            itemFaltas.setValor(descontoFaltas);
            itensDaFolha.add(itemFaltas);
        }

        // NOTA: Este laço itera sobre a lista de especialistas.
        // Ele chama o método "calcular" de cada um, sem precisar saber
        // qual cálculo específico está sendo executado. Isso demonstra o poder da modularidade.
        for (ICalculoFolha modulo : this.modulosDeCalculo) {
            ItemFolha itemCalculado = modulo.calcular(funcionario);

            // VERIFICAÇÃO DE SEGURANÇA:
            // Só adiciona na lista se o cálculo retornou um item válido.
            // Isso protege caso um imposto retorne 'null' (ex: isento de IRRF).
            if (itemCalculado != null) {
                itensDaFolha.add(itemCalculado);
            }
        }

        return itensDaFolha;
    }
}
