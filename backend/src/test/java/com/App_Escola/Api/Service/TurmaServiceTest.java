package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.TurmaRepository;

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
@DisplayName("TurmaService – testes unitários")
class TurmaServiceTest {

    @Mock private TurmaRepository turmaRepository;
    @InjectMocks private TurmaService turmaService;

    private TurmaModel turmaBase;

    @BeforeEach
    void setUp() {
        turmaBase = new TurmaModel(1, "5°", "5° Ano A", List.of());
    }

    @Test
    @DisplayName("listarTodos – retorna page com turmas")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        when(turmaRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(turmaBase), pageable, 1));

        Page<TurmaModel> resultado = turmaService.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("5° Ano A");
        verify(turmaRepository).findAll(pageable);
    }

    @Test
    @DisplayName("listarTodos – retorna page vazia")
    void listarTodos_semRegistros_retornaPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(turmaRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(), pageable, 0));

        assertThat(turmaService.listarTodos(pageable).getTotalElements()).isZero();
    }

    @Test
    @DisplayName("buscarPorId – retorna turma quando existe")
    void buscarPorId_existente_retornaTurma() {
        when(turmaRepository.findById(1)).thenReturn(Optional.of(turmaBase));

        TurmaModel resultado = turmaService.buscarPorId(1);

        assertThat(resultado.getIdTurma()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("5° Ano A");
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando não existe")
    void buscarPorId_inexistente_lancaExcecao() {
        when(turmaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> turmaService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Turma não encontrada");
    }

    @Test
    @DisplayName("salvar – persiste e retorna turma")
    void salvar_dadosValidos_retornaTurma() {
        TurmaModel nova = new TurmaModel(null, "6°", "6° Ano B", List.of());
        TurmaModel salva = new TurmaModel(2, "6°", "6° Ano B", List.of());
        when(turmaRepository.save(nova)).thenReturn(salva);

        TurmaModel resultado = turmaService.salvar(nova);

        assertThat(resultado.getIdTurma()).isEqualTo(2);
        verify(turmaRepository).save(nova);
    }

    @Test
    @DisplayName("atualizar – modifica campos e retorna turma atualizada")
    void atualizar_existente_retornaTurmaAtualizada() {
        TurmaModel novos = new TurmaModel(null, "5° Novo", "5° Ano B", List.of());
        TurmaModel atualizada = new TurmaModel(1, "5° Novo", "5° Ano B", List.of());

        when(turmaRepository.findById(1)).thenReturn(Optional.of(turmaBase));
        when(turmaRepository.save(any())).thenReturn(atualizada);

        TurmaModel resultado = turmaService.atualizar(1, novos);

        assertThat(resultado.getNome()).isEqualTo("5° Ano B");
        verify(turmaRepository).findById(1);
        verify(turmaRepository).save(any());
    }

    @Test
    @DisplayName("atualizar – lança exceção quando ID não existe")
    void atualizar_inexistente_lancaExcecao() {
        when(turmaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> turmaService.atualizar(99, turmaBase))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Turma não encontrada");
        verify(turmaRepository, never()).save(any());
    }

    @Test
    @DisplayName("deletar – remove turma existente")
    void deletar_existente_removeSemExcecao() {
        when(turmaRepository.existsById(1)).thenReturn(true);
        doNothing().when(turmaRepository).deleteById(1);

        assertThatCode(() -> turmaService.deletar(1)).doesNotThrowAnyException();

        verify(turmaRepository).existsById(1);
        verify(turmaRepository).deleteById(1);
    }

    @Test
    @DisplayName("deletar – lança exceção quando não existe")
    void deletar_inexistente_lancaExcecao() {
        when(turmaRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> turmaService.deletar(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Turma não encontrada");
        verify(turmaRepository, never()).deleteById(any());
    }
}
