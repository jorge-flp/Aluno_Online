package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.BoletimConceitoModel;
import com.App_Escola.Api.Model.BoletimNotasModel;
import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.NotaModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.AlunoRepository;
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
@DisplayName("BoletimService – testes unitários")
class BoletimServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @Mock
    private NotaRepository notaRepository;

    @InjectMocks
    private BoletimService boletimService;

    private AlunoModel aluno;
    private DisciplinaModel matematica;
    private DisciplinaModel portugues;

    @BeforeEach
    void setUp() {
        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());

        aluno = new AlunoModel(
                100,
                "Maria Souza",
                "52998224725",
                "maria@escola.com",
                LocalDate.of(2012, 3, 20),
                turma
        );

        matematica = new DisciplinaModel(1, "Matemática");
        portugues  = new DisciplinaModel(2, "Português");
    }

    // --------------------------------------------------------
    // buscarNotas – cenários principais
    // --------------------------------------------------------

    @Test
    @DisplayName("buscarNotas – lança RuntimeException quando aluno não existe")
    void buscarNotas_alunoNaoEncontrado_lancaExcecao() {
        when(alunoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletimService.buscarNotas(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aluno não encontrado");
    }

    @Test
    @DisplayName("buscarNotas – retorna boletim com média correta para uma disciplina")
    void buscarNotas_umaDisciplina_mediaCorreta() {
        // Duas notas de Matemática: 8.0 e 6.0 → média 7.0
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 8.0),
                new NotaModel(2, aluno, matematica, 2, 6.0)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimNotasModel boletim = boletimService.buscarNotas(100);

        assertThat(boletim.getMatricula()).isEqualTo(100);
        assertThat(boletim.getNome()).isEqualTo("Maria Souza");
        assertThat(boletim.getTurma()).isEqualTo("5° Ano A");
        assertThat(boletim.getDisciplinas()).hasSize(1);
        assertThat(boletim.getDisciplinas().get(0).getMedia()).isEqualTo(7.0);
        assertThat(boletim.getMediaGeral()).isEqualTo(7.0);
    }

    @Test
    @DisplayName("buscarNotas – agrupa notas por disciplina corretamente")
    void buscarNotas_duasDisciplinas_agrupadas() {
        // Matemática: 10.0 → média 10.0
        // Português:  5.0 + 7.0 → média 6.0
        // Média geral: (10.0 + 6.0) / 2 = 8.0
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 10.0),
                new NotaModel(2, aluno, portugues,  1, 5.0),
                new NotaModel(3, aluno, portugues,  2, 7.0)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimNotasModel boletim = boletimService.buscarNotas(100);

        assertThat(boletim.getDisciplinas()).hasSize(2);

        var matDisciplina = boletim.getDisciplinas().stream()
                .filter(d -> d.getDisciplina().equals("Matemática"))
                .findFirst().orElseThrow();

        var porDisciplina = boletim.getDisciplinas().stream()
                .filter(d -> d.getDisciplina().equals("Português"))
                .findFirst().orElseThrow();

        assertThat(matDisciplina.getMedia()).isEqualTo(10.0);
        assertThat(porDisciplina.getMedia()).isEqualTo(6.0);
        assertThat(boletim.getMediaGeral()).isEqualTo(8.0);
    }

    @Test
    @DisplayName("buscarNotas – retorna boletim com lista vazia quando aluno não tem notas")
    void buscarNotas_semNotas_disciplinasVazias() {
        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(List.of());

        BoletimNotasModel boletim = boletimService.buscarNotas(100);

        assertThat(boletim.getDisciplinas()).isEmpty();
        assertThat(boletim.getMediaGeral()).isEqualTo(0.0);
    }

    @Test
    @DisplayName("buscarNotas – turma fica nula quando aluno não tem turma")
    void buscarNotas_semTurma_turmaNull() {
        AlunoModel alunoSemTurma = new AlunoModel(
                200, "Pedro Lima", "11144477735",
                "pedro@escola.com", LocalDate.of(2011, 1, 1), null
        );

        when(alunoRepository.findById(200)).thenReturn(Optional.of(alunoSemTurma));
        when(notaRepository.findByAluno_Matricula(200)).thenReturn(List.of());

        BoletimNotasModel boletim = boletimService.buscarNotas(200);

        assertThat(boletim.getTurma()).isNull();
    }

    // --------------------------------------------------------
    // buscarConceito
    // --------------------------------------------------------

    @Test
    @DisplayName("buscarConceito – retorna conceito A para média >= 9.0")
    void buscarConceito_mediaAlta_conceitoA() {
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 9.5)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimConceitoModel conceito = boletimService.buscarConceito(100);

        assertThat(conceito.getConceito()).isEqualTo("A");
        assertThat(conceito.getFeedback()).isEqualTo("Excelente desempenho acadêmico.");
    }

    @Test
    @DisplayName("buscarConceito – retorna conceito B para média entre 7.0 e 8.9")
    void buscarConceito_mediaBoa_conceitoB() {
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 8.0)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimConceitoModel conceito = boletimService.buscarConceito(100);

        assertThat(conceito.getConceito()).isEqualTo("B");
        assertThat(conceito.getFeedback()).isEqualTo("Bom desempenho acadêmico.");
    }

    @Test
    @DisplayName("buscarConceito – retorna conceito C para média entre 5.0 e 6.9")
    void buscarConceito_mediaRegular_conceitoC() {
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 6.0)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimConceitoModel conceito = boletimService.buscarConceito(100);

        assertThat(conceito.getConceito()).isEqualTo("C");
        assertThat(conceito.getFeedback()).contains("reforçar os estudos");
    }

    @Test
    @DisplayName("buscarConceito – retorna conceito D para média abaixo de 5.0")
    void buscarConceito_mediaBaixa_conceitoD() {
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 3.0)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimConceitoModel conceito = boletimService.buscarConceito(100);

        assertThat(conceito.getConceito()).isEqualTo("D");
        assertThat(conceito.getFeedback()).contains("acompanhamento acadêmico");
    }

    @Test
    @DisplayName("buscarConceito – retorna SEM AVALIAÇÃO quando aluno não tem notas")
    void buscarConceito_semNotas_semAvaliacao() {
        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(List.of());

        BoletimConceitoModel conceito = boletimService.buscarConceito(100);

        assertThat(conceito.getConceito()).isEqualTo("SEM AVALIAÇÃO");
        assertThat(conceito.getFeedback()).contains("não possui notas");
    }

    @Test
    @DisplayName("buscarConceito – lança RuntimeException quando aluno não existe")
    void buscarConceito_alunoNaoEncontrado_lancaExcecao() {
        when(alunoRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> boletimService.buscarConceito(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Aluno não encontrado");
    }

    // --------------------------------------------------------
    // arredondamento
    // --------------------------------------------------------

    @Test
    @DisplayName("buscarNotas – média é arredondada para 2 casas decimais")
    void buscarNotas_mediaArredondada() {
        // 10 + 7 + 8 = 25 / 3 = 8.333...  →  arredonda para 8.33
        List<NotaModel> notas = List.of(
                new NotaModel(1, aluno, matematica, 1, 10.0),
                new NotaModel(2, aluno, matematica, 2,  7.0),
                new NotaModel(3, aluno, matematica, 3,  8.0)
        );

        when(alunoRepository.findById(100)).thenReturn(Optional.of(aluno));
        when(notaRepository.findByAluno_Matricula(100)).thenReturn(notas);

        BoletimNotasModel boletim = boletimService.buscarNotas(100);

        assertThat(boletim.getDisciplinas().get(0).getMedia()).isEqualTo(8.33);
        assertThat(boletim.getMediaGeral()).isEqualTo(8.33);
    }
}
