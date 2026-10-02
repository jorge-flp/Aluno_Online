package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Repository.ProfessorRepository;
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
@DisplayName("ProfessorService – testes unitários")
class ProfessorServiceTest {
    @Mock private ProfessorRepository professorRepository;
    @InjectMocks private ProfessorService professorService;
    private ProfessorModel professorBase;

    @BeforeEach void setUp() {
        professorBase = new ProfessorModel(1, "Carlos Lima", "52998224725", "carlos@escola.com", "(11) 99999-0001");
    }

    @Test void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        when(professorRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(professorBase), pageable, 1));
        Page<ProfessorModel> resultado = professorService.listarTodos(pageable);
        assertThat(resultado.getTotalElements()).isEqualTo(1);
    }

    @Test void listarTodos_semRegistros_retornaPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        when(professorRepository.findAll(pageable)).thenReturn(new PageImpl<>(List.of(), pageable, 0));
        assertThat(professorService.listarTodos(pageable).getTotalElements()).isZero();
    }

    @Test void buscarPorId_existente_retornaProfessor() {
        when(professorRepository.findById(1)).thenReturn(Optional.of(professorBase));
        ProfessorModel resultado = professorService.buscarPorId(1);
        assertThat(resultado.getIdProfessor()).isEqualTo(1);
    }

    @Test void buscarPorId_inexistente_lancaExcecao() {
        when(professorRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> professorService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Professor não encontrado");
    }

    @Test void salvar_dadosValidos_retornaProfessorSalvo() {
        ProfessorModel novo = new ProfessorModel(null, "Ana", "11144477735", "ana@escola.com", "(21) 91111-2222");
        when(professorRepository.save(novo)).thenReturn(new ProfessorModel(2, "Ana", "11144477735", "ana@escola.com", "(21) 91111-2222"));
        ProfessorModel resultado = professorService.salvar(novo);
        assertThat(resultado.getIdProfessor()).isEqualTo(2);
    }

    @Test void atualizar_existente_retornaAtualizado() {
        ProfessorModel novos = new ProfessorModel(null, "Carlos Atualizado", "52998224725", "carlos.novo@escola.com", "(11) 88888-7777");
        when(professorRepository.findById(1)).thenReturn(Optional.of(professorBase));
        when(professorRepository.save(any())).thenReturn(new ProfessorModel(1, "Carlos Atualizado", "52998224725", "carlos.novo@escola.com", "(11) 88888-7777"));
        ProfessorModel resultado = professorService.atualizar(1, novos);
        assertThat(resultado.getNome()).isEqualTo("Carlos Atualizado");
    }

    @Test void atualizar_inexistente_lancaExcecao() {
        when(professorRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> professorService.atualizar(99, professorBase))
                .isInstanceOf(RuntimeException.class).hasMessage("Professor não encontrado");
    }

    @Test void deletar_existente_removeSemExcecao() {
        when(professorRepository.findById(1)).thenReturn(Optional.of(professorBase));
        doNothing().when(professorRepository).delete(professorBase);
        assertThatCode(() -> professorService.deletar(1)).doesNotThrowAnyException();
    }

    @Test void deletar_inexistente_lancaExcecao() {
        when(professorRepository.findById(99)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> professorService.deletar(99))
                .isInstanceOf(RuntimeException.class).hasMessage("Professor não encontrado");
    }
}
