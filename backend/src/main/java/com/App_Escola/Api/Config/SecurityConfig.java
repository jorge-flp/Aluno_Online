
package com.App_Escola.Api.Config;

import com.App_Escola.Api.Model.UsuarioModel;
import com.App_Escola.Api.Repository.UsuarioRepository;
import com.App_Escola.Api.Service.JwtService;

import io.jsonwebtoken.JwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Criptografia de senhas com BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Filtro responsável pela autenticação JWT
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(
            JwtService jwtService,
            UsuarioRepository usuarioRepository
    ) {
        return new JwtAuthenticationFilter(
                jwtService,
                usuarioRepository
        );
    }

    // Configuração principal do Spring Security
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtAuthenticationFilter jwtFilter
    ) throws Exception {

        http
            .cors(cors -> {})

            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .httpBasic(basic -> basic.disable())

            .formLogin(form -> form.disable())

            .logout(logout -> logout.disable())

            .authorizeHttpRequests(auth -> auth

                // Requisições CORS
                .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
                ).permitAll()

                // Login público
                .requestMatchers(
                    HttpMethod.POST,
                    "/auth/login"
                ).permitAll()

                // Cadastro permitido somente para ADMIN
                .requestMatchers(
                    HttpMethod.POST,
                    "/auth/usuarios"
                ).hasRole("ADMIN")

                // Endpoint de erro
                .requestMatchers("/error").permitAll()

                // Demais rotas exigem autenticação
                .anyRequest().authenticated()
            )

            .exceptionHandling(exception -> exception

                .authenticationEntryPoint(
                    (request, response, ex) ->
                        response.sendError(
                            HttpServletResponse.SC_UNAUTHORIZED,
                            "Autenticação necessária."
                        )
                )

                .accessDeniedHandler(
                    (request, response, ex) ->
                        response.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "Você não possui permissão para acessar este recurso."
                        )
                )
            )

            // Adiciona o filtro JWT antes do filtro padrão
            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // Filtro de autenticação JWT
    public static class JwtAuthenticationFilter
            extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UsuarioRepository usuarioRepository;

        public JwtAuthenticationFilter(
                JwtService jwtService,
                UsuarioRepository usuarioRepository
        ) {
            this.jwtService = jwtService;
            this.usuarioRepository = usuarioRepository;
        }

        @Override
        protected boolean shouldNotFilter(
                HttpServletRequest request
        ) {

            return request.getMethod().equalsIgnoreCase("OPTIONS")
                    || (
                        request.getMethod().equalsIgnoreCase("POST")
                        && request.getServletPath().equals("/auth/login")
                    );
        }

        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain chain
        ) throws ServletException, IOException {

            String authorization = request.getHeader(
                    HttpHeaders.AUTHORIZATION
            );

            // Sem token: deixa o Spring Security decidir o acesso
            if (authorization == null ||
                    !authorization.startsWith("Bearer ")) {

                chain.doFilter(request, response);
                return;
            }

            String token = authorization.substring(7);

            final String email;

            // 1. Valida exclusivamente o token JWT
            try {

                email = jwtService.validarToken(token);

            } catch (JwtException | IllegalArgumentException ex) {

                SecurityContextHolder.clearContext();

                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Token inválido ou expirado."
                );

                return;
            }

            // 2. Busca o usuário no banco
            // Erros de conexão ou consulta não serão tratados como JWT inválido.
            var usuarioOptional = usuarioRepository
                    .findByEmailIgnoreCase(email);

            if (usuarioOptional.isEmpty()) {

                SecurityContextHolder.clearContext();

                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Usuário não encontrado."
                );

                return;
            }

            UsuarioModel usuario = usuarioOptional.get();

            // 3. Define o perfil como autoridade do Spring Security
            String autoridade = "ROLE_" +
                    usuario.getPerfil().name();

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            usuario.getEmail(),
                            null,
                            List.of(
                                new SimpleGrantedAuthority(autoridade)
                            )
                    );

            authentication.setDetails(
                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            // 4. Continua a execução fora do try/catch do JWT
            chain.doFilter(request, response);
        }
    }
}
