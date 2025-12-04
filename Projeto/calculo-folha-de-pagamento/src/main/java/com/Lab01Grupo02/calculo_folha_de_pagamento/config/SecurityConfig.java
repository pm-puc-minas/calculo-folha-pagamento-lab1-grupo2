package com.Lab01Grupo02.calculo_folha_de_pagamento.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

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

                // 2. Habilita o CORS com a configuração personalizada
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // 3. Configura as regras de autorização
                .authorizeHttpRequests(authorize -> authorize
                        // Permite acesso a QUALQUER requisição (/)
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    /**
     * Configuração do CORS para permitir requisições do frontend.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições de qualquer origem (para desenvolvimento)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));

        // Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Permite envio de credenciais (cookies, headers de autenticação)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}