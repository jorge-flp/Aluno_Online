package com.App_Escola.Api.Service;

import com.App_Escola.Api.DTO.*;
import com.App_Escola.Api.Model.*;
import com.App_Escola.Api.Model.UsuarioModel.PerfilUsuario;
import com.App_Escola.Api.Repository.UsuarioRepository;

import jakarta.persistence.EntityManager;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EntityManager entityManager;

    public AuthService(
            UsuarioRepository usuarioRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            EntityManager entityManager
    ) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.entityManager = entityManager;
    }

    // CADASTRO DE USUÁRIO

    @Transactional
    public UsuarioResponse cadastrar(CadastroUsuarioRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (request.perfil() == PerfilUsuario.ADMIN) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Não é permitido cadastrar ADMIN por essa rota."
            );
        }

        if (request.senha().getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "A senha excede o limite de bytes aceito pelo BCrypt."
            );
        }

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Já existe uma conta com esse e-mail."
            );
        }

        validarPerfilExistente(
                request.perfil(),
                request.perfilId()
        );

        if (usuarioRepository.existsByPerfilAndPerfilId(
                request.perfil(),
                request.perfilId()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Essa entidade escolar já possui uma conta."
            );
        }

        UsuarioModel usuario = new UsuarioModel();

        usuario.setNome(request.nome().trim());
        usuario.setEmail(email);

        // Nunca armazenar a senha original.
        usuario.setSenha(
                passwordEncoder.encode(request.senha())
        );

        usuario.setPerfil(request.perfil());
        usuario.setPerfilId(request.perfilId());

        UsuarioModel salvo = usuarioRepository.save(usuario);

        return new UsuarioResponse(
                salvo.getId(),
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getPerfil(),
                salvo.getPerfilId(),
                salvo.getCriadoEm()
        );
    }

    // VALIDAÇÃO DO PERFIL ESCOLAR

    private void validarPerfilExistente(
            PerfilUsuario perfil,
            Long perfilId
    ) {

        if (perfilId == null || perfilId <= 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID do perfil inválido."
            );
        }

        final Integer id;

        try {
            id = Math.toIntExact(perfilId);
        } catch (ArithmeticException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "ID do perfil inválido."
            );
        }

        boolean existe = switch (perfil) {

            case ALUNO ->
                    entityManager.find(AlunoModel.class, id) != null;

            case RESPONSAVEL ->
                    entityManager.find(ResponsavelModel.class, id) != null;

            case PROFESSOR ->
                    entityManager.find(ProfessorModel.class, id) != null;

            case DIRETOR ->
                    entityManager.find(DiretorModel.class, id) != null;

            case SECRETARIO ->
                    entityManager.find(SecretarioModel.class, id) != null;

            case COORDENADOR ->
                    entityManager.find(CoordenadorModel.class, id) != null;

            case ADMIN -> false;
        };

        if (!existe) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Não foi encontrada uma entidade para o perfil informado."
            );
        }
    }

    // LOGIN

    @Transactional(readOnly = true)
    public LoginResponse login(LoginRequest request) {

        String email = request.email()
                .trim()
                .toLowerCase(Locale.ROOT);

        UsuarioModel usuario = usuarioRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new BadCredentialsException(
                                "Credenciais inválidas."
                        )
                );

        if (!passwordEncoder.matches(
                request.senha(),
                usuario.getSenha()
        )) {
            throw new BadCredentialsException(
                    "Credenciais inválidas."
            );
        }

        String token = jwtService.gerarToken(usuario);

        return new LoginResponse(
                token,
                "Bearer",
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfil().name()
        );
    }
}