package com.Lab01Grupo02.calculo_folha_de_pagamento.model.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Este conversor instrui o JPA/Hibernate sobre como converter
 * um objeto Java 'LocalDate' em um formato 'String' (TEXT) 'yyyy-MM-dd'
 * para o banco de dados (SQLite), e vice-versa.
 *
 * Isso força a serialização correta e impede o 'timestamp' numérico.
 */
@Converter(autoApply = false)
public class LocalDateAttributeConverter implements AttributeConverter<LocalDate, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Chamado quando o Java (ex: 'LocalDate') precisa ser convertido
     * para o formato do banco de dados (ex: 'String').
     */
    @Override
    public String convertToDatabaseColumn(LocalDate localDate) {
        // Se a data for nula, salve 'null' no banco
        return (localDate == null ? null : localDate.format(FORMATTER));
    }

    /**
     * Chamado quando o dado do banco (ex: 'String') precisa ser
     * convertido para o formato Java (ex: 'LocalDate').
     */
    @Override
    public LocalDate convertToEntityAttribute(String dbData) {
        // Se o dado do banco for nulo ou vazio, retorne 'null' no Java
        return (dbData == null || dbData.isEmpty() ? null : LocalDate.parse(dbData, FORMATTER));
    }
}