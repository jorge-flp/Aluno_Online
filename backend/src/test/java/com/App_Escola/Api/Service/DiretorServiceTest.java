package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.DiretorModel;
import com.App_Escola.Api.Repository.DiretorRepository;

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
@DisplayName("DiretorService – testes unitários")
class DiretorServiceTest {

    @Mock private DiretorRepository repository;
    @InjectMocks private DiretorService service;

    private DiretorModel base;

    @BeforeEach
    void setUp() {
        base = new DiretorModel(1, "Roberto Alves", "roberto@escola.com", "(21) 98888-1111");
    }

    @Test
    @DisplayName("listarTodos – retorna page com diretores")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(base), pageable, 1));

        Page<DiretorModel> resultado = service.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("Roberto Alves");
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
    @DisplayName("buscarPorId – retorna diretor quando existe")
    void buscarPorId_existente_retornaDiretor() {
        when(repository.findById(1)).thenReturn(Optional.of(base));

        DiretorModel resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Roberto Alves");
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando não existe")
    void buscarPorId_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Diretor não encontrado");
    }

    @Test
    @DisplayName("salvar – persiste e retorna diretor")
    void salvar_dadosValidos_retornaDiretor() {
        DiretorModel novo = new DiretorModel(null, "Novo Diretor", "novo@escola.com", "(21) 90000-0000");
        when(repository.save(novo)).thenReturn(base);

        DiretorModel resultado = service.salvar(novo);

        assertThat(resultado.getId()).isEqualTo(1);
        verify(repository).save(novo);
    }

    @Test
    @DisplayName("atualizar – modifica campos e retorna diretor atualizado")
    void atualizar_existente_retornaAtualizado() {
        DiretorModel novos = new DiretorModel(null, "Roberto Atualizado", "roberto.novo@escola.com", "(21) 99999-0000");
        DiretorModel atualizado = new DiretorModel(1, "Roberto Atualizado", "roberto.novo@escola.com", "(21) 99999-0000");

        when(repository.findById(1)).thenReturn(Optional.of(base));
        when(repository.save(any())).thenReturn(atualizado);

        DiretorModel resultado = service.atualizar(1, novos);

        assertThat(resultado.getNome()).isEqualTo("Roberto Atualizado");
        verify(repository).findById(1);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("atualizar – lança exceção quando não existe")
    void atualizar_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, base))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Diretor não encontrado");
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
                .hasMessage("Diretor não encontrado");
        verify(repository, never()).deleteById(any());
    }
}
