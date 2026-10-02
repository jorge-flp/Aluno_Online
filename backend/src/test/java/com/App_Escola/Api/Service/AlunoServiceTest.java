package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.AlunoRepository;

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

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AlunoService – testes unitários")
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService alunoService;

    private AlunoModel alunoBase;
    private TurmaModel turmaBase;

    @BeforeEach
    void setUp() {
        turmaBase = new TurmaModel(1, "5°", "5° Ano A", List.of());
        alunoBase = new AlunoModel(
                100,
                "Maria Souza",
                "52998224725",
                "maria@escola.com",
                LocalDate.of(2012, 3, 20),
                turmaBase
        );
    }

    // -------------------------------------------------------
    // listarTodos
    // -------------------------------------------------------

    @Test
    @DisplayName("listarTodos – retorna page com alunos")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<AlunoModel> pagina = new PageImpl<>(List.of(alunoBase), pageable, 1);

        when(alunoRepository.findAll(pageable)).thenReturn(pagina);

        Page<AlunoModel> resultado = alunoService.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("Maria Souza");
        verify(alunoRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("listarTodos – retorna page vazia")
    void listarTodos_semRegistros_retornaPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(alunoRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(), pageable, 0));

        Page<AlunoModel> resultado = alunoService.listarTodos(pageable);

        assertThat(resultado.getTotalElements()).isZero();
        verify(alunoRepository).findAll(pageable);
    }

    // -------------------------------------------------------
    // buscarPorMatricula
    // -------------------------------------------------------

    @Test
    @DisplayName("buscarPorMatricula – retorna aluno quando existe")
    void buscarPorMatricula_existente_retornaAluno() {
        when(alunoRepository.findById(100)).thenReturn(Optional.of(alunoBase));

        AlunoModel resultado = alunoService.buscarPorMatricula(100);

        assertThat(resultado.getMatricula()).isEqualTo(100);
        assertThat(resultado.getNome()).isEqualTo("Maria Souza");
        verify(alunoRepository).findById(100);
    }

    @Test
    @DisplayName("buscarPorMatricula – lança RuntimeException quando não existe")
    void buscarPorMatricula_inexistente_lancaExcecao() {
        when(alunoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> alunoService.buscarPorMatricula(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Aluno não encontrado");
        verify(alunoRepository).findById(99);
    }

    // -------------------------------------------------------
    // salvar
    // -------------------------------------------------------

    @Test
    @DisplayName("salvar – persiste e retorna aluno com matrícula")
    void salvar_dadosValidos_retornaAluno() {
        AlunoModel semId = new AlunoModel(null, "Pedro", "11144477735",
                "pedro@escola.com", LocalDate.of(2013, 1, 10), turmaBase);

        when(alunoRepository.save(semId)).thenReturn(alunoBase);

        AlunoModel resultado = alunoService.salvar(semId);

        assertThat(resultado.getMatricula()).isEqualTo(100);
        verify(alunoRepository).save(semId);
    }

    // -------------------------------------------------------
    // deletar
    // -------------------------------------------------------

    @Test
    @DisplayName("deletar – remove aluno existente sem exceção")
    void deletar_existente_removeSemExcecao() {
        when(alunoRepository.existsById(100)).thenReturn(true);
        doNothing().when(alunoRepository).deleteById(100);

        assertThatCode(() -> alunoService.deletar(100)).doesNotThrowAnyException();

        verify(alunoRepository).existsById(100);
        verify(alunoRepository).deleteById(100);
    }

    @Test
    @DisplayName("deletar – lança RuntimeException quando não existe")
    void deletar_inexistente_lancaExcecao() {
        when(alunoRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> alunoService.deletar(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Aluno não encontrado");
        verify(alunoRepository, never()).deleteById(any());
    }
}
