package com.App_Escola.Api.Config;

import com.App_Escola.Api.Model.UsuarioModel;
import com.App_Escola.Api.Repository.UsuarioRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        UsuarioModel usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() ->
                    new UsernameNotFoundException("Usuário não encontrado"));

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                    new SimpleGrantedAuthority(
                        "ROLE_" + usuario.getRole().name()
                    )
                )
                .build();
    }
}