package com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL;


import lombok.Data;
import java.math.BigDecimal;

@Data

public class ItemFolha {
    private String descricao;
    private BigDecimal valor;

}
