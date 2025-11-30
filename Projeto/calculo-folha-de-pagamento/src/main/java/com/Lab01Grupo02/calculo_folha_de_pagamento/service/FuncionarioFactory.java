package com.Lab01Grupo02.calculo_folha_de_pagamento.service;

import com.Lab01Grupo02.calculo_folha_de_pagamento.controller.dto.FuncionarioRequestDTO;
import com.Lab01Grupo02.calculo_folha_de_pagamento.model.Funcionario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class FuncionarioFactory {

    public static Funcionario criarDoDTO(FuncionarioRequestDTO dto) {
        Funcionario f = new Funcionario();

        // 1. Preenchendo dados básicos (Pessoa)
        f.setNome(dto.nome);
        f.setCpf(dto.cpf);

        // Conversão de Data de Nascimento (Proteção contra erro)
        try {
            f.setDataNascimento(LocalDate.parse(dto.dataNascimento));
        } catch (Exception e) {
            // Pode lançar erro ou definir data padrão/nula dependendo da regra
            throw new RuntimeException("Data de nascimento inválida: " + dto.dataNascimento);
        }

        // 2. Preenchendo dados do Funcionário (Seu Model atual)
        f.setCargo(dto.cargo);

        // Conversão de Salário (String -> BigDecimal)
        if (dto.salarioBruto != null) {
            f.setSalarioBruto(new BigDecimal(dto.salarioBruto));
        } else {
            f.setSalarioBruto(BigDecimal.ZERO); // Regra de negócio: Salário padrão
        }

        // Conversão de Data de Admissão
        if (dto.dataAdmissao != null) {
            f.setDataAdmissao(LocalDate.parse(dto.dataAdmissao));
        } else {
            f.setDataAdmissao(LocalDate.now()); // Regra: Se não passar, assume hoje
        }

        // Tratamento de Insalubridade (Valores Padrão)
        if (dto.grauInsalubridade != null && !dto.grauInsalubridade.isEmpty()) {
            f.setGrauInsalubridade(dto.grauInsalubridade);
        } else {
            f.setGrauInsalubridade("NENHUM"); // Define valor padrão se vier nulo
        }

        // Tratamento de Carga Horária
        f.setCargaHorariaSemanal(dto.cargaHorariaSemanal != null ? dto.cargaHorariaSemanal : 44); // Padrão 44h

        // Tratamento de Periculosidade
        f.setPossuiPericulosidade(Boolean.TRUE.equals(dto.possuiPericulosidade));

        return f;
    }
}