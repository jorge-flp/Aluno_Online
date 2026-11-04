package com.App_Escola.Api.Config;

import com.App_Escola.Api.Service.JwtService;
import com.App_Escola.Api.Repository.UsuarioRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter(
            JwtService jwtService,
            UsuarioRepository usuarioRepository
    ) {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    FilterChain chain
            ) throws ServletException, IOException {

                String authorization = request.getHeader(
                        HttpHeaders.AUTHORIZATION
                );

                if (authorization != null &&
                        authorization.startsWith("Bearer ")) {

                    try {
                        String token = authorization.substring(7);

                        String email = jwtService.validarToken(token);

                        var usuario = usuarioRepository
                                .findByEmail(email)
                                .orElseThrow();

                        var authentication =
                                new UsernamePasswordAuthenticationToken(
                                        usuario.getEmail(),
                                        null,
                                        List.of(
                                                new SimpleGrantedAuthority(
                                                        "ROLE_" +
                                                        usuario.getPerfil().name()
                                                )
                                        )
                                );

                        SecurityContextHolder.getContext()
                                .setAuthentication(authentication);

                    } catch (Exception ex) {
                        SecurityContextHolder.clearContext();
                        response.sendError(
                                HttpServletResponse.SC_UNAUTHORIZED,
                                "Token inválido ou expirado"
                        );
                        return;
                    }
                }

                chain.doFilter(request, response);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            OncePerRequestFilter jwtFilter
    ) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/error").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}