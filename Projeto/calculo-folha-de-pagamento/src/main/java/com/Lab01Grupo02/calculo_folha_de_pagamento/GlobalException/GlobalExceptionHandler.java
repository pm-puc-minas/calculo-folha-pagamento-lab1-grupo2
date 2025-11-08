package com.Lab01Grupo02.calculo_folha_de_pagamento.GlobalException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * Handler de Exceções Global (@ControllerAdvice).
 *
 * Esta classe intercepta exceções lançadas por qualquer Controller
 * e as transforma em uma resposta ResponseEntity<ErrorResponse> padronizada (JSON).
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
     * Captura exceções do tipo InvalidCredentialsException (HTTP 401).
     *
     * @param ex A exceção InvalidCredentialsException que foi lançada.
     * @param request O contexto da requisição web.
     * @return Um ResponseEntity contendo o ErrorResponse JSON e o status HTTP 401.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex,
                                                                           WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED.value(),        // 401
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Captura exceções do tipo UserDeactivatedException (HTTP 403).
     *
     * @param ex A exceção UserDeactivatedException que foi lançada.
     * @param request O contexto da requisição web.
     * @return Um ResponseEntity contendo o ErrorResponse JSON e o status HTTP 403.
     */
    @ExceptionHandler(UserDeactivatedException.class)
    public ResponseEntity<ErrorResponse> handleUserDeactivatedException(UserDeactivatedException ex,
                                                                        WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.FORBIDDEN.value(),           // 403
                ex.getMessage(),
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Captura exeções de validação @valid
     * Retorna HTTP 400 (bad Request).
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        // Coleta todas as mensagens de erro dos campos
        String detalhes = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> String.format("'%s': %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.joining(", "));

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),       // 400
                "Erro de validação. Verifique os campos da requisição.",
                "uri=" + request.getDescription(false) + "; Erros=[ " + detalhes + " ]"
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Captura JSon mal formatado, Se o usuario enviar Json que não pode ser lido
     * Exemplo: { "cargaHoraria" : }
     * Retorna HTTP 400 (Bad Request).
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),       // 400
                "Requisição JSON mal formatada ou inválida.",
                request.getDescription(false)
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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