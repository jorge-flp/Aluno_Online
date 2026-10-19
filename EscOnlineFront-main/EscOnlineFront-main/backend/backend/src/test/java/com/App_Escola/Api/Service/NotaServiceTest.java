package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.NotaModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.AlunoRepository;
import com.App_Escola.Api.Repository.DisciplinaRepository;
import com.App_Escola.Api.Repository.NotaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotaService – testes unitários")
class NotaServiceTest {

    @Mock
    private NotaRepository notaRepository;

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private DisciplinaRepository disciplinaRepository;

    @InjectMocks
    private NotaService notaService;

    private AlunoModel aluno;
    private DisciplinaModel disciplina;
    private NotaModel nota;

    @BeforeEach
    void setUp() {
        TurmaModel turma = new TurmaModel(1, "1°", "Turma A", List.of());

        aluno = new AlunoModel(
                100,
                "João Silva",
                "52998224725",
                "joao@escola.com",
                LocalDate.of(2010, 5, 15),
                turma
        );

        disciplina = new DisciplinaModel(1, "Matemática");

        nota = new NotaModel(1, aluno, disciplina, 1, 8.5);
    }

    // --------------------------------------------------------
    // listarTodos
    // --------------------------------------------------------

    @Test
    @DisplayName("listarTodos – retorna todas as notas")
    void listarTodos_retornaLista() {
        NotaModel outraNota = new NotaModel(2, aluno, disciplina, 2, 7.0);
        when(notaRepository.findAll()).thenReturn(List.of(nota, outraNota));

        List<NotaModel> resultado = notaService.listarTodos();

        assertThat(resultado).hasSize(2);
        verify(notaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("listarTodos – retorna lista vazia quando não há notas")
    void listarTodos_listaVazia() {
        when(notaRepository.findAll()).thenReturn(List.of());

        assertThat(notaService.listarTodos()).isEmpty();
    }

    // --------------------------------------------------------
    // buscarPorAluno
    // --------------------------------------------------------

    @Test
    @DisplayName("buscarPorAluno – retorna notas do aluno pela matrícula")
    void buscarPorAluno_retornaNotas() {
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(List.of(nota));

        List<NotaModel> resultado = notaService.buscarPorAluno(100);

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getValor()).isEqualTo(8.5);
        verify(notaRepository, times(1)).findByAluno_Matricula(100);
    }

    @Test
    @DisplayName("buscarPorAluno – retorna lista vazia quando aluno não tem notas")
    void buscarPorAluno_semNotas() {
        when(notaRepository.findByAluno_Matricula(999)).thenReturn(List.of());

        assertThat(notaService.buscarPorAluno(999)).isEmpty();
    }

    // --------------------------------------------------------
    // buscarPorId
    // --------------------------------------------------------

    @Test
    @DisplayName("buscarPorId – retorna nota quando ID existe")
    void buscarPorId_encontrada() {
        when(notaRepository.findById(1)).thenReturn(Optional.of(nota));

        NotaModel resultado = notaService.buscarPorId(1);

        assertThat(resultado.getIdNota()).isEqualTo(1);
        assertThat(resultado.getValor()).isEqualTo(8.5);
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando ID não existe")
    void buscarPorId_naoEncontrada_lancaExcecao() {
        when(notaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notaService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Nota não encontrada");
    }

    // --------------------------------------------------------
    // salvar
    // --------------------------------------------------------

    @Test
    @DisplayName("salvar – busca aluno e disciplina antes de persistir")
    void salvar_persisteComRelacionamentos() {
        AlunoModel alunoRef = new AlunoModel();
        alunoRef.setMatricula(100);

        DisciplinaModel disciplinaRef = new DisciplinaModel();
        disciplinaRef.setIdDisciplina(1);

        NotaModel notaEntrada = new NotaModel(null, alunoRef, disciplinaRef, 1, 9.0);

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(disciplina));
        when(notaRepository.save(notaEntrada)).thenReturn(
                new NotaModel(5, aluno, disciplina, 1, 9.0)
        );

        NotaModel resultado = notaService.salvar(notaEntrada);

        assertThat(resultado.getIdNota()).isEqualTo(5);
        assertThat(resultado.getAluno().getNome()).isEqualTo("João Silva");
        assertThat(resultado.getDisciplina().getNome()).isEqualTo("Matemática");
        verify(alunoRepository, times(1)).findById(100);
        verify(disciplinaRepository, times(1)).findById(1);
        verify(notaRepository, times(1)).save(notaEntrada);
    }

    @Test
    @DisplayName("salvar – lança RuntimeException quando aluno não existe")
    void salvar_alunoNaoEncontrado_lancaExcecao() {
        AlunoModel alunoRef = new AlunoModel();
        alunoRef.setMatricula(999);

        DisciplinaModel disciplinaRef = new DisciplinaModel();
        disciplinaRef.setIdDisciplina(1);

        NotaModel notaEntrada = new NotaModel(null, alunoRef, disciplinaRef, 1, 7.0);

        when(alunoRepository.findById(999)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notaService.salvar(notaEntrada))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aluno não encontrado");

        verify(notaRepository, never()).save(any());
    }

    @Test
    @DisplayName("salvar – lança RuntimeException quando disciplina não existe")
    void salvar_disciplinaNaoEncontrada_lancaExcecao() {
        AlunoModel alunoRef = new AlunoModel();
        alunoRef.setMatricula(100);

        DisciplinaModel disciplinaRef = new DisciplinaModel();
        disciplinaRef.setIdDisciplina(99);

        NotaModel notaEntrada = new NotaModel(null, alunoRef, disciplinaRef, 1, 7.0);

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notaService.salvar(notaEntrada))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Disciplina não encontrada");

        verify(notaRepository, never()).save(any());
    }

    // --------------------------------------------------------
    // deletar
    // --------------------------------------------------------

    @Test
    @DisplayName("deletar – remove nota existente com sucesso")
    void deletar_removeComSucesso() {
        when(notaRepository.findById(1)).thenReturn(Optional.of(nota));
        doNothing().when(notaRepository).delete(nota);

        assertThatCode(() -> notaService.deletar(1))
                .doesNotThrowAnyException();

        verify(notaRepository, times(1)).delete(nota);
    }

    @Test
    @DisplayName("deletar – lança RuntimeException quando nota não existe")
    void deletar_naoEncontrada_lancaExcecao() {
        when(notaRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> notaService.deletar(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Nota não encontrada");

        verify(notaRepository, never()).delete(any());
    }
}
