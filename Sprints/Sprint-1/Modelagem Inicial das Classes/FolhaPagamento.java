package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.ItemFolha;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.time.YearMonth;


@Data
public class FolhaPagamento {

    private BigDecimal salarioBruto;
    private BigDecimal totalProventos;
    private BigDecimal totalDescontos;
    private BigDecimal salarioLiquido;

    private BigDecimal baseINSS;
    private BigDecimal baseIRRF;
    private BigDecimal baseFGTS;

    private BigDecimal valorVT;
    private BigDecimal valorVA;
    private BigDecimal valorPericulosidade;
    private BigDecimal valorInsalubridade;

    private YearMonth mesReferencia;
    private BigDecimal salarioHora;

    private List<ItemFolha> itens = new ArrayList<>();

    public void adicionarItem(ItemFolha item) {
        itens.add(item); // CORRIGIDO
    }
}
