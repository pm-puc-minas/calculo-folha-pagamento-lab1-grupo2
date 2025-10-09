package com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos;

import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import java.math.BigDecimal;

public interface ICalculadora {
    FolhaDePagamento calcularFolha(BigDecimal salarioBruto);

    FolhaDePagamento calcularFolha(double salarioBase, double ignorado, String descricao);
}
