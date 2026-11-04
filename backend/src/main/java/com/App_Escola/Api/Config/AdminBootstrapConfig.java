package com.App_Escola.Api.Config;

import com.App_Escola.Api.Model.UsuarioModel;
import com.App_Escola.Api.Model.UsuarioModel.PerfilUsuario;
import com.App_Escola.Api.Repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Locale;

@Configuration
public class AdminBootstrapConfig {

    @Bean
    public ApplicationRunner criarAdminInicial(
            UsuarioRepository repository,
            PasswordEncoder encoder,

            @Value("${app.bootstrap-admin.nome:Administrador}")
            String nome,

            @Value("${app.bootstrap-admin.email:}")
            String email,

            @Value("${app.bootstrap-admin.senha:}")
            String senha

    ) {

        return args -> {

            if (email.isBlank() || senha.isBlank()) {
                return;
            }

            String emailNormalizado = email
                    .trim()
                    .toLowerCase(Locale.ROOT);

            if (repository.existsByEmailIgnoreCase(emailNormalizado)) {
                return;
            }

            UsuarioModel admin = new UsuarioModel();

            admin.setNome(nome);
            admin.setEmail(emailNormalizado);
            admin.setSenha(encoder.encode(senha));
            admin.setPerfil(PerfilUsuario.ADMIN);
            admin.setPerfilId(null);

            repository.save(admin);
        };
    }
}