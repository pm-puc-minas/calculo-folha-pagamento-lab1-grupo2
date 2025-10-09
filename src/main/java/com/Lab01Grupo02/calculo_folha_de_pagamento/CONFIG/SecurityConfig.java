package com.Lab01Grupo02.calculo_folha_de_pagamento.CONFIG;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * METODO NAO VAI SER USADO, PODE SER QUE USAMOS EM OUTRAS ROTAS
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso PÚBLICO a TODAS as rotas que começam com /api/
                        .requestMatchers("/api/**").permitAll()
                        // Exige autenticação para qualquer outra rota (se houver)
                        .anyRequest().authenticated()
                );
        return http.build();
    }
    */
}