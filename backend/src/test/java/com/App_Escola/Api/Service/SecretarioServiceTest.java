package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.SecretarioModel;
import com.App_Escola.Api.Repository.SecretarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("SecretarioService – testes unitários")
class SecretarioServiceTest {

    @Mock private SecretarioRepository repository;
    @InjectMocks private SecretarioService service;

    private SecretarioModel base;

    @BeforeEach
    void setUp() {
        base = new SecretarioModel(1, "Fernanda Lima", "fernanda@escola.com", "(31) 97777-3333");
    }

    @Test
    @DisplayName("listarTodos – retorna page com secretários")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(base), pageable, 1));

        Page<SecretarioModel> resultado = service.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("Fernanda Lima");
    }

    @Test
    @DisplayName("listarTodos – retorna page vazia")
    void listarTodos_semRegistros_retornaPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(), pageable, 0));

        assertThat(service.listarTodos(pageable).getTotalElements()).isZero();
    }

    @Test
    @DisplayName("buscarPorId – retorna secretário quando existe")
    void buscarPorId_existente_retornaSecretario() {
        when(repository.findById(1)).thenReturn(Optional.of(base));

        SecretarioModel resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Fernanda Lima");
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando não existe")
    void buscarPorId_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Secretário não encontrado");
    }

    @Test
    @DisplayName("salvar – persiste e retorna secretário")
    void salvar_dadosValidos_retornaSecretario() {
        SecretarioModel novo = new SecretarioModel(null, "Novo", "novo@escola.com", "(31) 90000-0000");
        when(repository.save(novo)).thenReturn(base);

        SecretarioModel resultado = service.salvar(novo);

        assertThat(resultado.getId()).isEqualTo(1);
        verify(repository).save(novo);
    }

    @Test
    @DisplayName("atualizar – modifica campos e retorna secretário atualizado")
    void atualizar_existente_retornaAtualizado() {
        SecretarioModel novos = new SecretarioModel(null, "Fernanda Atualizada", "fernanda.nova@escola.com", "(31) 99999-0000");
        SecretarioModel atualizado = new SecretarioModel(1, "Fernanda Atualizada", "fernanda.nova@escola.com", "(31) 99999-0000");

        when(repository.findById(1)).thenReturn(Optional.of(base));
        when(repository.save(any())).thenReturn(atualizado);

        SecretarioModel resultado = service.atualizar(1, novos);

        assertThat(resultado.getNome()).isEqualTo("Fernanda Atualizada");
        verify(repository).findById(1);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("atualizar – lança exceção quando não existe")
    void atualizar_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, base))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Secretário não encontrado");
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("deletar – remove quando existe")
    void deletar_existente_removeSemExcecao() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);

        assertThatCode(() -> service.deletar(1)).doesNotThrowAnyException();

        verify(repository).existsById(1);
        verify(repository).deleteById(1);
    }

    @Test
    @DisplayName("deletar – lança exceção quando não existe")
    void deletar_inexistente_lancaExcecao() {
        when(repository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Secretário não encontrado");
        verify(repository, never()).deleteById(any());
    }
}
