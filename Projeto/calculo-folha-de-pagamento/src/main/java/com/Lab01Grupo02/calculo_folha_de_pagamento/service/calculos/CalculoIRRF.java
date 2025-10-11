package main.java.com.Lab01Grupo02.calculo_folha_de_pagamento.service.calculos;
/*
import java.math.BigDecimal;
import java.math.RoundingMode;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.calculos.ICalculadora;

public class CalculoIRRF implements ICalculadora {

    private static final BigDecimal DEDUCAO_DEPENDENTE = new BigDecimal("189.59");

    // Dinir bd na ass
    public FolhaDePagamento calcularFolha(double salarioBruto, double numeroDependentes, String descricao) {
        BigDecimal salarioBrutoBD = BigDecimal.valueOf(salarioBruto);
        BigDecimal dependentesBD = BigDecimal.valueOf(numeroDependentes);
        BigDecimal descontoINSS = BigDecimal.ZERO;
        BigDecimal salarioBase = salarioBrutoBD.subtract(descontoINSS);
        BigDecimal deducaoDependentes = DEDUCAO_DEPENDENTE.multiply(dependentesBD);
        BigDecimal baseCalculo = salarioBase.subtract(deducaoDependentes);

        BigDecimal aliquota = BigDecimal.ZERO;
        BigDecimal deducaoIR = BigDecimal.ZERO;

        if (baseCalculo.compareTo(new BigDecimal("1903.98")) <= 0) {
            aliquota = BigDecimal.ZERO;
            deducaoIR = BigDecimal.ZERO;
        } else if (baseCalculo.compareTo(new BigDecimal("2826.65")) <= 0) {
            aliquota = new BigDecimal("0.075");
            deducaoIR = new BigDecimal("142.80");
        } else if (baseCalculo.compareTo(new BigDecimal("3751.05")) <= 0) {
            aliquota = new BigDecimal("0.15");
            deducaoIR = new BigDecimal("354.80");
        } else if (baseCalculo.compareTo(new BigDecimal("4664.68")) <= 0) {
            aliquota = new BigDecimal("0.225");
            deducaoIR = new BigDecimal("636.13");
        } else {
            aliquota = new BigDecimal("0.275");
            deducaoIR = new BigDecimal("869.36");
        }

        BigDecimal irrf = baseCalculo.multiply(aliquota).subtract(deducaoIR).setScale(2, RoundingMode.HALF_UP);

        if (irrf.compareTo(BigDecimal.ZERO) < 0) {
            irrf = BigDecimal.ZERO;
        }

        FolhaDePagamento folha = new FolhaDePagamento();
        // folha.setDescricao(descricao);
        folha.setTotalProvento(irrf);
        folha.setSalarioLiquido(salarioBrutoBD.subtract(irrf));

        return folha;
    }

    @Override
    public FolhaDePagamento calcularFolha(BigDecimal salarioBruto) {
        return null;
    }
}

 */
