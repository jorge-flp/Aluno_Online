package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.ResponsavelModel;
import com.App_Escola.Api.Repository.ResponsavelRepository;

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
@DisplayName("ResponsavelService – testes unitários")
class ResponsavelServiceTest {

    @Mock private ResponsavelRepository repository;
    @InjectMocks private ResponsavelService service;

    private ResponsavelModel responsavelBase;

    @BeforeEach
    void setUp() {
        responsavelBase = new ResponsavelModel(1, "João Silva",
                "52998224725", "joao@email.com", "(11) 91234-5678");
    }

    @Test
    @DisplayName("listarTodos – retorna page com responsáveis")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        when(repository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(responsavelBase), pageable, 1));

        Page<ResponsavelModel> resultado = service.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("buscarPorId – retorna responsável quando existe")
    void buscarPorId_existente_retornaResponsavel() {
        when(repository.findById(1)).thenReturn(Optional.of(responsavelBase));

        ResponsavelModel resultado = service.buscarPorId(1);

        assertThat(resultado.getIdResponsavel()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("João Silva");
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando não existe")
    void buscarPorId_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Responsável não encontrado");
    }

    @Test
    @DisplayName("salvar – persiste e retorna responsável")
    void salvar_dadosValidos_retornaResponsavel() {
        ResponsavelModel novo = new ResponsavelModel(null, "Ana", "11144477735",
                "ana@email.com", "(21) 90000-1111");
        when(repository.save(novo)).thenReturn(responsavelBase);

        ResponsavelModel resultado = service.salvar(novo);

        assertThat(resultado.getIdResponsavel()).isEqualTo(1);
        verify(repository).save(novo);
    }

    @Test
    @DisplayName("atualizar – modifica campos e retorna responsável atualizado")
    void atualizar_existente_retornaAtualizado() {
        ResponsavelModel novos = new ResponsavelModel(null, "João Atualizado",
                "52998224725", "joao.novo@email.com", "(11) 99999-0000");
        ResponsavelModel atualizado = new ResponsavelModel(1, "João Atualizado",
                "52998224725", "joao.novo@email.com", "(11) 99999-0000");

        when(repository.findById(1)).thenReturn(Optional.of(responsavelBase));
        when(repository.save(any())).thenReturn(atualizado);

        ResponsavelModel resultado = service.atualizar(1, novos);

        assertThat(resultado.getNome()).isEqualTo("João Atualizado");
        verify(repository).findById(1);
        verify(repository).save(any());
    }

    @Test
    @DisplayName("atualizar – lança exceção quando não existe")
    void atualizar_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.atualizar(99, responsavelBase))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Responsável não encontrado");
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
                .hasMessage("Responsável não encontrado");
        verify(repository, never()).deleteById(any());
    }
}
