package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.util.List;

/**
 * NOTA: Esta é a interface principal do seu serviço de cálculo.
 * Ela define o contrato para o "orquestrador" ou "gerente" da folha de pagamento.
 * Sua única responsabilidade é definir um método que possa processar uma folha
 * completa para um funcionário.
 */
public interface ICalculadora {

    /**
     * NOTA: Este é o método que será chamado pela sua aplicação.
     * Ele recebe o funcionário e deve retornar a lista completa de itens
     * da folha (proventos e descontos).
     * @param funcionario O funcionário para o qual a folha será calculada.
     * @return Uma lista de ItemFolha, representando cada linha do holerite.
     */
    List<ItemFolha> calcularFolhaCompleta(Funcionario funcionario);
}