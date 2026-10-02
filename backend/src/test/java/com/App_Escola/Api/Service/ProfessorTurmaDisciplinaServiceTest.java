package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Model.ProfessorTurmaDisciplinaModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.DisciplinaRepository;
import com.App_Escola.Api.Repository.ProfessorRepository;
import com.App_Escola.Api.Repository.ProfessorTurmaDisciplinaRepository;
import com.App_Escola.Api.Repository.TurmaRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessorTurmaDisciplinaService – testes")
class ProfessorTurmaDisciplinaServiceTest {
    @Mock private ProfessorTurmaDisciplinaRepository relacionamentoRepository;
    @Mock private ProfessorRepository professorRepository;
    @Mock private TurmaRepository turmaRepository;
    @Mock private DisciplinaRepository disciplinaRepository;
    @InjectMocks private ProfessorTurmaDisciplinaService service;

    private ProfessorModel professor;
    private TurmaModel turma;
    private DisciplinaModel disciplina;
    private ProfessorTurmaDisciplinaModel base;

    @BeforeEach void setUp() {
        professor = new ProfessorModel(1, "Carlos", "52998224725", "carlos@escola.com", "(11) 99999-0001");
        turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        disciplina = new DisciplinaModel(1, "Matemática");
        base = new ProfessorTurmaDisciplinaModel(1, professor, turma, disciplina);
    }

    @Test void listarTodos_retornaLista() {
        when(relacionamentoRepository.findAll()).thenReturn(List.of(base));
        List<ProfessorTurmaDisciplinaModel> resultado = service.listarTodos();
        assertThat(resultado).hasSize(1);
    }

    @Test void buscarPorId_existente_retorna() {
        when(relacionamentoRepository.findById(1)).thenReturn(Optional.of(base));
        ProfessorTurmaDisciplinaModel resultado = service.buscarPorId(1);
        assertThat(resultado.getIdProfessorTurmaDisciplina()).isEqualTo(1);
    }

    @Test void buscarPorId_inexistente_lancaExcecao() {
        when(relacionamentoRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Relação entre professor, turma e disciplina não encontrada");
    }

    @Test void salvar_comDadosValidos_retorna() {
        when(professorRepository.findById(1)).thenReturn(Optional.of(professor));
        when(turmaRepository.findById(1)).thenReturn(Optional.of(turma));
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(disciplina));
        when(relacionamentoRepository.existsByProfessor_IdProfessorAndTurma_IdTurmaAndDisciplina_IdDisciplina(1, 1, 1)).thenReturn(false);
        when(relacionamentoRepository.save(any())).thenReturn(base);
        ProfessorTurmaDisciplinaModel resultado = service.salvar(base);
        assertThat(resultado.getIdProfessorTurmaDisciplina()).isEqualTo(1);
    }

    @Test void salvar_jaExiste_lancaExcecao() {
        when(professorRepository.findById(1)).thenReturn(Optional.of(professor));
        when(turmaRepository.findById(1)).thenReturn(Optional.of(turma));
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(disciplina));
        when(relacionamentoRepository.existsByProfessor_IdProfessorAndTurma_IdTurmaAndDisciplina_IdDisciplina(1, 1, 1)).thenReturn(true);
        assertThatThrownBy(() -> service.salvar(base))
                .isInstanceOf(RuntimeException.class).hasMessage("Este professor já está vinculado a esta disciplina nesta turma");
    }

    @Test void atualizar_existente_retorna() {
        when(relacionamentoRepository.findById(1)).thenReturn(Optional.of(base));
        when(professorRepository.findById(1)).thenReturn(Optional.of(professor));
        when(turmaRepository.findById(1)).thenReturn(Optional.of(turma));
        when(disciplinaRepository.findById(1)).thenReturn(Optional.of(disciplina));
        when(relacionamentoRepository.save(any())).thenReturn(base);
        ProfessorTurmaDisciplinaModel resultado = service.atualizar(1, base);
        assertThat(resultado.getIdProfessorTurmaDisciplina()).isEqualTo(1);
    }

    @Test void deletar_existente_removeSemExcecao() {
        when(relacionamentoRepository.findById(1)).thenReturn(Optional.of(base));
        doNothing().when(relacionamentoRepository).delete(base);
        assertThatCode(() -> service.deletar(1)).doesNotThrowAnyException();
    }

    @Test void buscarPorProfessor_retornaLista() {
        when(relacionamentoRepository.findByProfessor_IdProfessor(1)).thenReturn(List.of(base));
        List<ProfessorTurmaDisciplinaModel> resultado = service.buscarPorProfessor(1);
        assertThat(resultado).hasSize(1);
    }

    @Test void buscarPorTurma_retornaLista() {
        when(relacionamentoRepository.findByTurma_IdTurma(1)).thenReturn(List.of(base));
        List<ProfessorTurmaDisciplinaModel> resultado = service.buscarPorTurma(1);
        assertThat(resultado).hasSize(1);
    }

    @Test void buscarPorDisciplina_retornaLista() {
        when(relacionamentoRepository.findByDisciplina_IdDisciplina(1)).thenReturn(List.of(base));
        List<ProfessorTurmaDisciplinaModel> resultado = service.buscarPorDisciplina(1);
        assertThat(resultado).hasSize(1);
    }
}
