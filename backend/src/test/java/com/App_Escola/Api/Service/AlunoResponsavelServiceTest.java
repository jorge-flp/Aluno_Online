package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.AlunoResponsavelModel;
import com.App_Escola.Api.Model.ResponsavelModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.AlunoResponsavelRepository;
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
@DisplayName("AlunoResponsavelService – testes unitários")
class AlunoResponsavelServiceTest {
    @Mock private AlunoResponsavelRepository repository;
    @InjectMocks private AlunoResponsavelService service;

    private AlunoResponsavelModel base;

    @BeforeEach void setUp() {
        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        AlunoModel aluno = new AlunoModel(100, "Maria", "52998224725", "maria@escola.com", LocalDate.of(2012, 3, 20), turma);
        ResponsavelModel responsavel = new ResponsavelModel(1, "João", "52998224725", "joao@email.com", "(11) 91234-5678");
        base = new AlunoResponsavelModel(1, "Pai", aluno, responsavel);
    }

    @Test void listarTodos_retornaLista() {
        when(repository.findAll()).thenReturn(List.of(base));
        List<AlunoResponsavelModel> resultado = service.listarTodos();
        assertThat(resultado).hasSize(1);
    }

    @Test void buscarPorId_existente_retornaAlunoResponsavel() {
        when(repository.findById(1)).thenReturn(Optional.of(base));
        AlunoResponsavelModel resultado = service.buscarPorId(1);
        assertThat(resultado.getIdAlunoResponsavel()).isEqualTo(1);
    }

    @Test void buscarPorId_inexistente_lancaExcecao() {
        when(repository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.buscarPorId(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Relacionamento não encontrado");
    }

    @Test void salvar_novoRelacionamento_retornaAlunoResponsavel() {
        when(repository.findByAluno_MatriculaAndResponsavel_IdResponsavel(100, 1)).thenReturn(Optional.empty());
        when(repository.save(any())).thenReturn(base);
        AlunoResponsavelModel resultado = service.salvar(base);
        assertThat(resultado.getIdAlunoResponsavel()).isEqualTo(1);
    }

    @Test void salvar_relacionamentoJaExiste_lancaExcecao() {
        when(repository.findByAluno_MatriculaAndResponsavel_IdResponsavel(100, 1)).thenReturn(Optional.of(base));
        assertThatThrownBy(() -> service.salvar(base))
                .isInstanceOf(RuntimeException.class).hasMessage("Aluno já está relacionado a este responsável");
    }

    @Test void atualizar_existente_retornaAtualizado() {
        AlunoResponsavelModel atualizado = new AlunoResponsavelModel(1, "Mãe", base.getAluno(), base.getResponsavel());
        when(repository.findById(1)).thenReturn(Optional.of(base));
        when(repository.save(any())).thenReturn(atualizado);
        AlunoResponsavelModel resultado = service.atualizar(1, atualizado);
        assertThat(resultado.getParentesco()).isEqualTo("Mãe");
    }

    @Test void deletar_existente_removeSemExcecao() {
        when(repository.existsById(1)).thenReturn(true);
        doNothing().when(repository).deleteById(1);
        assertThatCode(() -> service.deletar(1)).doesNotThrowAnyException();
    }

    @Test void deletar_inexistente_lancaExcecao() {
        when(repository.existsById(99)).thenReturn(false);
        assertThatThrownBy(() -> service.deletar(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Relacionamento não encontrado");
    }
}
