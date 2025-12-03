package com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.ItemFolha;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class CalculoInsalubridade implements com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos.ICalculoFolha {

    private static final BigDecimal SALARIO_MINIMO = new BigDecimal("1380.60");

    private static final Map<String, BigDecimal> PERCENTUAIS = Map.of(
            "BAIXO", new BigDecimal("0.10"),
            "MEDIO", new BigDecimal("0.20"),
            "ALTO",  new BigDecimal("0.40")
    );

    @Override
    public ItemFolha calcular(Funcionario funcionario) {
        ItemFolha item = new ItemFolha();
        item.setTipo("PROVENTO"); // Define como provento

        // Corrigido: tratar corretamente funcionário nulo, grau vazio ou 'Nenhum'
        if (funcionario == null || funcionario.getGrauInsalubridade() == null || 
            funcionario.getGrauInsalubridade().trim().isEmpty() || 
            funcionario.getGrauInsalubridade().trim().equalsIgnoreCase("Nenhum")) {
            item.setDesc("Adicional de Insalubridade");
            item.setValor(BigDecimal.ZERO);
            return item;
        }

        String grauNormalizado = normalizarGrau(funcionario.getGrauInsalubridade());
        BigDecimal percentual = PERCENTUAIS.getOrDefault(grauNormalizado, BigDecimal.ZERO);

        // Validação: se o grau não foi identificado, retorna zero
        if (grauNormalizado.isEmpty() || percentual.equals(BigDecimal.ZERO)) {
            item.setDesc("Adicional de Insalubridade - Grau não identificado");
            item.setValor(BigDecimal.ZERO);
            return item;
        }

        item.setDesc(
            "Adicional de Insalubridade - " + grauNormalizado +
            " (" + percentual.multiply(BigDecimal.valueOf(100)).stripTrailingZeros().toPlainString() + "%)"
        );

        BigDecimal valor = SALARIO_MINIMO
            .multiply(percentual)
            .setScale(2, RoundingMode.HALF_UP);

        item.setValor(valor);
        return item;
    }

    private String normalizarGrau(String grau) {
        if (grau == null || grau.trim().isEmpty()) {
            return "";
        }

        String g = grau
                .trim()
                .toUpperCase()
                .replace("Á", "A")
                .replace("À", "A")
                .replace("Â", "A")
                .replace("Ã", "A")
                .replace("É", "E")
                .replace("Ê", "E")
                .replace("Í", "I")
                .replace("Ó", "O")
                .replace("Ô", "O")
                .replace("Õ", "O")
                .replace("Ú", "U");

        if (g.contains("BAIX") || g.contains("MIN")) return "BAIXO";
        if (g.contains("MED")) return "MEDIO";
        if (g.contains("ALT") || g.contains("MAX")) return "ALTO";

        return "";
    }
}