package com.App_Escola.Api.Config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.POST, "/alunos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/alunos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/alunos/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/turmas/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/turmas/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/turmas/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/professores/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/professores/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/professores/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/alunos/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/turmas/**")
                        .authenticated()

                        .requestMatchers(HttpMethod.GET, "/professores/**")
                        .authenticated()

                        .anyRequest().authenticated())

                .httpBasic(withDefaults());
        return http.build();
    }
}