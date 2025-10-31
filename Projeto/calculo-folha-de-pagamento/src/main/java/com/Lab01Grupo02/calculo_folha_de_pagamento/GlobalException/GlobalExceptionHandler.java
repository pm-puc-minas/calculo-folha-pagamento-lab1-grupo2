package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Handler de Exceções Global (@ControllerAdvice).
 *
 * Esta classe intercepta exceções lançadas por qualquer Controller
 * e as transforma em uma resposta ResponseEntity<ErrorResponse> padronizada (JSON).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura exceções do tipo ResourceNotFoundException (HTTP 404).
     *
     * @param ex A exceção ResourceNotFoundException que foi lançada.
     * @param request O contexto da requisição web (usado para obter o URI).
     * @return Um ResponseEntity contendo o ErrorResponse JSON e o status HTTP 404.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),           // 404
                ex.getMessage(),                        // A mensagem definida (ex: "Funcionário não encontrado...")
                request.getDescription(false) // Detalhes (ex: "uri=/api/funcionarios/123")
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Captura todas as outras exceções genéricas (HTTP 500).
     * Este é o "catch-all" para erros inesperados (ex: NullPointerException).
     *
     * @param ex A exceção genérica (Exception) que foi lançada.
     * @param request O contexto da requisição web.
     * @return Um ResponseEntity contendo o ErrorResponse JSON e o status HTTP 500.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex,
                                                               WebRequest request) {

        // Mensagem genérica para não expor detalhes de infraestrutura ou código.
        String message = "Ocorreu um erro interno inesperado no servidor.";

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                message,
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}