package com.Lab01Grupo02.calculo_folha_de_pagamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração do Spring Security.
 * Esta classe desabilita a autenticação para TODOS os endpoints,
 * permitindo o teste da API sem precisar de login e senha.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desabilita o CSRF (Cross-Site Request Forgery)
                .csrf(csrf -> csrf.disable())

                // 2. Configura as regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso a QUALQUER requisição (/)
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}