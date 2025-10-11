package main.java.com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalculoFGTS implements CalculoFolha {

    @Override
    public ItemFolha calcular(Funcionario funcionario) {

        // Se o funcionário não existir OU o salário dele for nulo,
        // retorne um item zerado.
        if (funcionario == null || funcionario.getSalarioBruto() == null) {
            ItemFolha itemVazio = new ItemFolha();
            itemVazio.setDesc("FGTS");
            itemVazio.setTipo("Provento");
            itemVazio.setValor(BigDecimal.ZERO);
            return itemVazio;
        }

        // NOTA: O restante do código só será executado se o funcionário e o salário forem válidos.
        BigDecimal salarioBruto = funcionario.getSalarioBruto();
        BigDecimal valorFgts = salarioBruto.multiply(new BigDecimal("0.08")).setScale(2, RoundingMode.HALF_UP);

        ItemFolha item = new ItemFolha();
        item.setDesc("FGTS");
        item.setTipo("Provento");
        item.setValor(valorFgts);
        return item;
    }
}