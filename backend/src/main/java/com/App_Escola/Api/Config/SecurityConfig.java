
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
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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

                // CORS
                .requestMatchers(
                    HttpMethod.OPTIONS, "/**"
                ).permitAll()

                // AUTENTICAÇÃO
                .requestMatchers(
                    HttpMethod.POST, "/auth/login"
                ).permitAll()

                .requestMatchers("/auth/usuarios/**")
                    .hasRole("ADMIN")

                // ALUNOS
                .requestMatchers(
                    HttpMethod.GET, "/alunos/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.POST, "/alunos/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.PUT, "/alunos/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.DELETE, "/alunos/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                // RESPONSÁVEIS
                .requestMatchers(
                    HttpMethod.GET, "/responsaveis/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.POST, "/responsaveis/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.PUT, "/responsaveis/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.DELETE, "/responsaveis/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                // RELACIONAMENTO ALUNO-RESPONSÁVEL
                .requestMatchers(
                    "/alunos-responsaveis/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                // PROFESSORES
                .requestMatchers(
                    HttpMethod.GET, "/professores/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR", "SECRETARIO"
                )

                .requestMatchers(
                    "/professores/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                // DIRETORES, SECRETÁRIOS E COORDENADORES
                .requestMatchers(
                    "/diretores/**",
                    "/secretarios/**",
                    "/coordenadores/**"
                ).hasRole("ADMIN")

                // TURMAS
                .requestMatchers(
                    HttpMethod.GET, "/turmas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR", "SECRETARIO"
                )

                .requestMatchers(
                    "/turmas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR", "SECRETARIO"
                )

                // DISCIPLINAS
                .requestMatchers(
                    HttpMethod.GET, "/disciplinas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR",
                    "SECRETARIO", "PROFESSOR"
                )

                .requestMatchers(
                    "/disciplinas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR"
                )

                // PROFESSOR, TURMA E DISCIPLINA
                .requestMatchers(
                    "/professores-turmas-disciplinas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR"
                )

                // NOTAS
                .requestMatchers(
                    "/notas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR"
                )

                // NOTAS ESCRITAS
                .requestMatchers(
                    "/NotasEscritas/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR"
                )

                // BOLETINS
                .requestMatchers(
                    HttpMethod.GET, "/boletins/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR", "SECRETARIO"
                )

                .requestMatchers(
                    HttpMethod.POST, "/boletins/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "COORDENADOR"
                )

                .requestMatchers(
                    HttpMethod.DELETE, "/boletins/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR"
                )

                // ATESTADOS
                .requestMatchers(
                    "/Atestados/**"
                ).hasAnyRole(
                    "ADMIN", "DIRETOR", "SECRETARIO"
                )

                // ERROS
                .requestMatchers("/error").permitAll()

                // QUALQUER OUTRA ROTA: SOMENTE ADMIN
                .anyRequest().hasRole("ADMIN")
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
                            "Você não possui permissão."
                        )
                )
            )

            .addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    // FILTRO JWT
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

            if (authorization == null ||
                    !authorization.startsWith("Bearer ")) {

                chain.doFilter(request, response);
                return;
            }

            String token = authorization.substring(7);

            final String email;

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

            String autoridade =
                    "ROLE_" + usuario.getPerfil().name();

            var authentication =
                    new UsernamePasswordAuthenticationToken(
                            usuario.getEmail(),
                            null,
                            List.of(
                                new SimpleGrantedAuthority(autoridade)
                            )
                    );

            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);

            // Continua fora do try/catch de validação JWT.
            chain.doFilter(request, response);
        }
    }
}
