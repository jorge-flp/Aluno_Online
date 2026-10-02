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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotaService – testes unitários")
class NotaServiceTest {
    @Mock private NotaRepository notaRepository;
    @Mock private AlunoRepository alunoRepository;
    @Mock private DisciplinaRepository disciplinaRepository;
    @InjectMocks private NotaService notaService;

    private AlunoModel aluno;
    private DisciplinaModel disciplina;
    private NotaModel notaBase;

    @BeforeEach void setUp() {
        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        aluno = new AlunoModel(100, "Maria", "52998224725", "maria@escola.com", LocalDate.of(2012, 3, 20), turma);
        disciplina = new DisciplinaModel(1, "Matemática");
        notaBase = new NotaModel(1, aluno, disciplina, 1, 8.0);
    }

    @Test void listarTodos_retornaLista() {
        when(notaRepository.findAll()).thenReturn(List.of(notaBase));
        List<NotaModel> resultado = notaService.listarTodos();
        assertThat(resultado).hasSize(1);
    }

    @Test void buscarPorAluno_retornaListaPorMatricula() {
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(List.of(notaBase));
        List<NotaModel> resultado = notaService.buscarPorAluno(100);
        assertThat(resultado).hasSize(1);
    }

    @Test void buscarPorId_existente_retornaNotaModel() {
        when(notaRepository.findById(1)).thenReturn(Optional.of(notaBase));
        NotaModel resultado = notaService.buscarPorId(1);
        assertThat(resultado.getIdNota()).isEqualTo(1);
    }

    @Test void buscarPorId_inexistente_lancaExcecao() {
        when(notaRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> notaService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Nota não encontrada");
    }

    @Test void salvar_comAlunoEDisciplinaValidos_retornaNota() {
        NotaModel nova = new NotaModel(null, aluno, disciplina, 1, 8.0);
        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(disciplina));
        when(notaRepository.save(any())).thenReturn(notaBase);
        NotaModel resultado = notaService.salvar(nova);
        assertThat(resultado.getIdNota()).isEqualTo(1);
    }

    @Test void salvar_alunoInexistente_lancaExcecao() {
        NotaModel nova = new NotaModel(null, new AlunoModel(99, "X", "00000000000", null, null, null), disciplina, 1, 8.0);
        when(alunoRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> notaService.salvar(nova))
                .isInstanceOf(RuntimeException.class).hasMessage("Aluno não encontrado");
    }

    @Test void deletar_existente_removeSemExcecao() {
        when(notaRepository.findById(1)).thenReturn(Optional.of(notaBase));
        doNothing().when(notaRepository).delete(notaBase);
        assertThatCode(() -> notaService.deletar(1)).doesNotThrowAnyException();
    }

    @Test void deletar_inexistente_lancaExcecao() {
        when(notaRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> notaService.deletar(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Nota não encontrada");
    }
}
