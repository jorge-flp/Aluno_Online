package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Repository.DisciplinaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DisciplinaService – testes unitários")
class DisciplinaServiceTest {

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private DisciplinaService disciplinaService;

    private DisciplinaModel matematica;

    @BeforeEach
    void setUp() {
        matematica = new DisciplinaModel(1, "Matemática");
    }

    // --------------------------------------------------------
    // listarTodos
    // --------------------------------------------------------

    @Test
    @DisplayName("listarTodos – retorna lista de disciplinas")
    void listarTodos_retornaLista() {
        DisciplinaModel portugues = new DisciplinaModel(2, "Português");
        when(disciplinaRepository.findAll()).thenReturn(List.of(matematica, portugues));

        List<DisciplinaModel> resultado = disciplinaService.listarTodos();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(DisciplinaModel::getNome)
                .containsExactly("Matemática", "Português");
        verify(disciplinaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("listarTodos – retorna lista vazia quando não há disciplinas")
    void listarTodos_listaVazia() {
        when(disciplinaRepository.findAll()).thenReturn(List.of());

        List<DisciplinaModel> resultado = disciplinaService.listarTodos();

        assertThat(resultado).isEmpty();
    }

    // --------------------------------------------------------
    // buscarPorId
    // --------------------------------------------------------

    @Test
    @DisplayName("buscarPorId – retorna disciplina quando ID existe")
    void buscarPorId_encontrada() {
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(matematica));

        DisciplinaModel resultado = disciplinaService.buscarPorId(1);

        assertThat(resultado.getNome()).isEqualTo("Matemática");
        assertThat(resultado.getIdDisciplina()).isEqualTo(1);
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando ID não existe")
    void buscarPorId_naoEncontrada_lancaExcecao() {
        when(disciplinaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> disciplinaService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Disciplina não encontrada");
    }

    // --------------------------------------------------------
    // salvar
    // --------------------------------------------------------

    @Test
    @DisplayName("salvar – persiste e retorna a disciplina")
    void salvar_retornaDisciplinaSalva() {
        when(disciplinaRepository.save(matematica)).thenReturn(matematica);

        DisciplinaModel resultado = disciplinaService.salvar(matematica);

        assertThat(resultado.getNome()).isEqualTo("Matemática");
        verify(disciplinaRepository, times(1)).save(matematica);
    }

    // --------------------------------------------------------
    // atualizar
    // --------------------------------------------------------

    @Test
    @DisplayName("atualizar – altera o nome e persiste")
    void atualizar_nomeAlterado() {
        DisciplinaModel novosDados = new DisciplinaModel(null, "Matemática Avançada");
        DisciplinaModel atualizada = new DisciplinaModel(1, "Matemática Avançada");

        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(matematica));
        when(disciplinaRepository.save(any(DisciplinaModel.class))).thenReturn(atualizada);

        DisciplinaModel resultado = disciplinaService.atualizar(1, novosDados);

        assertThat(resultado.getNome()).isEqualTo("Matemática Avançada");
        verify(disciplinaRepository, times(1)).save(any(DisciplinaModel.class));
    }

    @Test
    @DisplayName("atualizar – lança RuntimeException quando disciplina não existe")
    void atualizar_naoEncontrada_lancaExcecao() {
        when(disciplinaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> disciplinaService.atualizar(99, matematica))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Disciplina não encontrada");

        verify(disciplinaRepository, never()).save(any());
    }

    // --------------------------------------------------------
    // deletar
    // --------------------------------------------------------

    @Test
    @DisplayName("deletar – remove disciplina existente")
    void deletar_removeComSucesso() {
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(matematica));
        doNothing().when(disciplinaRepository).delete(matematica);

        assertThatCode(() -> disciplinaService.deletar(1))
                .doesNotThrowAnyException();

        verify(disciplinaRepository, times(1)).delete(matematica);
    }

    @Test
    @DisplayName("deletar – lança RuntimeException quando disciplina não existe")
    void deletar_naoEncontrada_lancaExcecao() {
        when(disciplinaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> disciplinaService.deletar(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Disciplina não encontrada");

        verify(disciplinaRepository, never()).delete(any());
    }
}
