package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.CoordenadorModel;
import com.App_Escola.Api.Repository.CoordenadorRepository;

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
@DisplayName("CoordenadorService – testes unitários")
class CoordenadorServiceTest {

    @Mock private CoordenadorRepository repository;
    @InjectMocks private CoordenadorService service;

    private CoordenadorModel base;

    @BeforeEach
    void setUp() {
        base = new CoordenadorModel(1L, "Carla Mendes", "carla@escola.com", "(11) 91111-2222");
    }

    @Test
    @DisplayName("listarTodos – retorna page com coordenadores")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(base), pageable, 1));

        Page<CoordenadorModel> resultado = service.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("Carla Mendes");
        verify(repository).findAll(pageable);
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
    @DisplayName("buscarPorId – retorna coordenador quando existe")
    void buscarPorId_existente_retornaCoordenador() {
        when(repository.findById(1)).thenReturn(Optional.of(base));

        CoordenadorModel resultado = service.buscarPorId(1);

        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNome()).isEqualTo("Carla Mendes");
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando não existe")
    void buscarPorId_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Coordenador não encontrado");
    }

    @Test
    @DisplayName("salvar – persiste e retorna coordenador")
    void salvar_dadosValidos_retornaCoordenador() {
        CoordenadorModel novo = new CoordenadorModel(null, "Novo", "novo@escola.com", "(11) 90000-0000");
        when(repository.save(novo)).thenReturn(base);

        CoordenadorModel resultado = service.salvar(novo);

        assertThat(resultado.getId()).isEqualTo(1L);
        verify(repository).save(novo);
    }

    @Test
    @DisplayName("atualizar – modifica campos e retorna coordenador atualizado")
    void atualizar_existente_retornaAtualizado() {
        CoordenadorModel novos = new CoordenadorModel(null, "Carla Atualizada", "carla.nova@escola.com", "(11) 99999-0000");
        CoordenadorModel atualizado = new CoordenadorModel(1L, "Carla Atualizada", "carla.nova@escola.com", "(11) 99999-0000");

        when(repository.findById(1)).thenReturn(Optional.of(base));
        when(repository.save(any())).thenReturn(atualizado);

        CoordenadorModel resultado = service.atualizar(1, novos);

        assertThat(resultado.getNome()).isEqualTo("Carla Atualizada");
        verify(repository).save(any());
    }

    @Test
    @DisplayName("atualizar – lança exceção quando não existe")
    void atualizar_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, base))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Coordenador não encontrado");
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
                .hasMessage("Coordenador não encontrado");
        verify(repository, never()).deleteById(any());
    }
}
