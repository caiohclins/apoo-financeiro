package br.com.ufrpe.apoo.financeiro.gestaoacesso.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    @Order(1)
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/api/auth/**", "/actuator/health/**")
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return http.build();
    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain protectedFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                    .jwkSetUri("http://keycloak:8080/realms/financeiro/protocol/openid-connect/certs")
                )
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        return http.build();
    }
}