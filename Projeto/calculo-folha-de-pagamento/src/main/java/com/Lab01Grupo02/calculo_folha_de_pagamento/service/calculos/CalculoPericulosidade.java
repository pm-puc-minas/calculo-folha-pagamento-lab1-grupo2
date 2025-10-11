package main.java.com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;

/*
public abstract class CalculoPericulosidade implements ICalculadora {


    @Override
    public FolhaDePagamento calcularFolha(double salarioBase, double ignorado, String descricao) {
       
        BigDecimal salarioBaseBD = BigDecimal.valueOf(salarioBase);
        BigDecimal percentualPericulosidade = BigDecimal.valueOf(0.30);

        
        BigDecimal adicionalPericulosidade = salarioBaseBD
                .multiply(percentualPericulosidade)
                .setScale(2, RoundingMode.HALF_UP);

        FolhaDePagamento folha = new FolhaDePagamento();
        //folha.setDescricao(descricao);
        folha.setTotalProvento(adicionalPericulosidade);
        folha.setSalarioLiquido(adicionalPericulosidade); // se ainda não há descontos

        return folha;
    }
}

 */
