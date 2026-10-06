package com.App_Escola.Api.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

<<<<<<< HEAD
    @Value("${app.frontend.url:http://localhost:5173}")
=======
    @Value("${app.frontend.url:https://esconlinefront-school-portal.vercel.app}")
>>>>>>> 1bdd618 (Configurando cors)
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

<<<<<<< HEAD
        registry.addMapping("/**")
                .allowedOrigins(
                        frontendUrl,
                        "http://localhost:5173"
                )
                .allowedMethods(
                        "GET",
                        "POST",
                        "PUT",
                        "PATCH",
                        "DELETE",
                        "OPTIONS"
                )
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
=======
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of(
                        frontendUrl,
                        "http://localhost:5173"
                )
        );

        configuration.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        configuration.setAllowCredentials(true);

        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
>>>>>>> 1bdd618 (Configurando cors)
    }
}