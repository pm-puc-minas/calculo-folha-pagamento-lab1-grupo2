package com.Lab01Grupo02.calculo_folha_de_pagamento.Testes.FolhaDePagamento;

import com.Lab01Grupo02.calculo_folha_de_pagamento.INTERFACE.FolhaDePagamentoInter;
import com.Lab01Grupo02.calculo_folha_de_pagamento.MODEL.FolhaDePagamento;
import com.Lab01Grupo02.calculo_folha_de_pagamento.SERVICE.RelatorioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;

import static com.Lab01Grupo02.calculo_folha_de_pagamento.INTERFACE.FolhaDePagamentoInter.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

// Mockito Junit 5
@ExtendWith(MockitoExtension.class)
public class RelatorioTeste {
    // Gera um mock (um tester) para o Relatorio
    // Como nao temos conexão com o banco de dados, aqui ele será util
    @Mock
    private FolhaDePagamentoInter relatorio;

    // Gera uma intancia real para o nosso relatorio, mas injeta os mocks, por conta da citacao na nossa classe
    @InjectMocks
    private RelatorioService folhaDePagamento;

    // Testes
    @Test
    void precisaRetornarPessoaQuandoBuscarPelaMatricula(){
        // ARRANGE: Criar um objeto falso
        FolhaDePagamento relatorioFalso = new FolhaDePagamento();
        relatorioFalso.setMatricula(1349);
        relatorioFalso.setSalarioBruto(BigDecimal.valueOf(6000.00));
        relatorioFalso.setTotalProvento(BigDecimal.valueOf(6600.00));
        relatorioFalso.setTotalDesconto(BigDecimal.valueOf(1100.50));
        relatorioFalso.setSalarioLiquido(BigDecimal.valueOf(5499.50));
        relatorioFalso.setBaseINSS(BigDecimal.valueOf(6000.00));
        relatorioFalso.setBaseIRRF(BigDecimal.valueOf(5500.00));
        relatorioFalso.setBaseFGTS(BigDecimal.valueOf(6000.00));
        relatorioFalso.setValorVT(BigDecimal.valueOf(180.0));
        relatorioFalso.setValorVA(BigDecimal.valueOf(400.00));
        relatorioFalso.setValorPericulosidade(BigDecimal.valueOf(600.00));
        relatorioFalso.setValorInsalubridade(BigDecimal.valueOf(0.00));
        relatorioFalso.setMesReferencia(YearMonth.of(2023, 10));
        relatorioFalso.setSalarioHora(BigDecimal.valueOf(27.27));

        // Nesta linha quando o When do mock for chamado, e ele tentar encontrar o id
        // definido, ele retorna o nosso relatorio falso.
        when(relatorio.findById(1349)).thenReturn(Optional.of(relatorioFalso));

        // ACT: Verificar se o resultado foi o correto
        FolhaDePagamento funcionario1349 = folhaDePagamento.retornarFolhaPagamento(1349);

        // ASSERT: Verificar o resultado esperado
        assertNotNull(funcionario1349);
        assertEquals(1349,funcionario1349.getMatricula());
        assertEquals(BigDecimal.valueOf(6000.00),funcionario1349.getSalarioBruto());
        assertEquals(BigDecimal.valueOf(6600.00),funcionario1349.getTotalProvento());
        assertEquals(BigDecimal.valueOf(1100.50),funcionario1349.getTotalDesconto());
        assertEquals(BigDecimal.valueOf(5499.5),funcionario1349.getSalarioLiquido());
        assertEquals(BigDecimal.valueOf(6000.00),funcionario1349.getBaseINSS());
        assertEquals(BigDecimal.valueOf(6000.00),funcionario1349.getBaseFGTS());
        assertEquals(BigDecimal.valueOf(180.00),funcionario1349.getValorVT());
        assertEquals(BigDecimal.valueOf(400.00),funcionario1349.getValorVA());


    }
}

