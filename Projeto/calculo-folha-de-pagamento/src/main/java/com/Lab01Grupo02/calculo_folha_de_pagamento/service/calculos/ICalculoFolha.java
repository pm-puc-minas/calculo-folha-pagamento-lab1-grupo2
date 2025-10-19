package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;

/**
 * NOTA: Esta é a interface que define o padrão para um "Módulo de Cálculo".
 * Ela garante que todo e qualquer cálculo individual (FGTS, INSS, etc.)
 * tenha a mesma estrutura e seja tratado da mesma forma pelo sistema,
 * promovendo a programação modular.
 */
public interface ICalculoFolha {

    /**
     * NOTA: Este é o método padrão que cada módulo de cálculo DEVE implementar.
     * Ele recebe o funcionário para ter acesso a todos os dados necessários
     * e retorna um único ItemFolha com o resultado do seu cálculo específico.
     * @param funcionario O funcionário sobre o qual o cálculo será aplicado.
     * @return Um ItemFolha, a representação de uma única linha no holerite.
     */
    ItemFolha calcular(Funcionario funcionario);
}