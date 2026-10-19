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

    @Mock
    private ProfessorRepository professorRepository;

    @InjectMocks
    private ProfessorService professorService;

    private ProfessorModel professorBase;

    @BeforeEach
    void setUp() {
        professorBase = new ProfessorModel(
                1,
                "Carlos Lima",
                "52998224725",   // CPF válido de teste
                "carlos@escola.com",
                "(11) 99999-0001"
        );
    }

    // -------------------------------------------------------
    // listarTodos
    // -------------------------------------------------------

    @Test
    @DisplayName("listarTodos – retorna page com professores existentes")
    void listarTodos_comRegistros_retornaPaginada() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProfessorModel> paginaEsperada = new PageImpl<>(
                List.of(professorBase),
                pageable,
                1
        );

        when(professorRepository.findAll(pageable)).thenReturn(paginaEsperada);

        Page<ProfessorModel> resultado = professorService.listarTodos(pageable);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotalElements()).isEqualTo(1);
        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).getNome()).isEqualTo("Carlos Lima");

        verify(professorRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("listarTodos – retorna page vazia quando não há professores")
    void listarTodos_semRegistros_retornaPaginaVazia() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProfessorModel> paginaVazia = new PageImpl<>(List.of(), pageable, 0);

        when(professorRepository.findAll(pageable)).thenReturn(paginaVazia);

        Page<ProfessorModel> resultado = professorService.listarTodos(pageable);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getTotalElements()).isZero();
        assertThat(resultado.getContent()).isEmpty();

        verify(professorRepository, times(1)).findAll(pageable);
    }

    // -------------------------------------------------------
    // buscarPorId
    // -------------------------------------------------------

    @Test
    @DisplayName("buscarPorId – retorna professor quando ID existe")
    void buscarPorId_idExistente_retornaProfessor() {
        when(professorRepository.findById(1)).thenReturn(Optional.of(professorBase));

        ProfessorModel resultado = professorService.buscarPorId(1);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdProfessor()).isEqualTo(1);
        assertThat(resultado.getNome()).isEqualTo("Carlos Lima");
        assertThat(resultado.getCpf()).isEqualTo("52998224725");
        assertThat(resultado.getEmail()).isEqualTo("carlos@escola.com");

        verify(professorRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("buscarPorId – lança RuntimeException quando ID não existe")
    void buscarPorId_idInexistente_lancaExcecao() {
        when(professorRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.buscarPorId(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Professor não encontrado");

        verify(professorRepository, times(1)).findById(99);
    }

    // -------------------------------------------------------
    // salvar
    // -------------------------------------------------------

    @Test
    @DisplayName("salvar – persiste e retorna professor com ID gerado")
    void salvar_dadosValidos_retornaProfessorSalvo() {
        ProfessorModel semId = new ProfessorModel(
                null,
                "Ana Costa",
                "11144477735",
                "ana@escola.com",
                "(21) 91111-2222"
        );
        ProfessorModel comId = new ProfessorModel(
                2,
                "Ana Costa",
                "11144477735",
                "ana@escola.com",
                "(21) 91111-2222"
        );

        when(professorRepository.save(semId)).thenReturn(comId);

        ProfessorModel resultado = professorService.salvar(semId);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getIdProfessor()).isEqualTo(2);
        assertThat(resultado.getNome()).isEqualTo("Ana Costa");

        verify(professorRepository, times(1)).save(semId);
    }

    // -------------------------------------------------------
    // atualizar
    // -------------------------------------------------------

    @Test
    @DisplayName("atualizar – modifica todos os campos e retorna professor atualizado")
    void atualizar_idExistente_retornaProfessorAtualizado() {
        ProfessorModel dadosNovos = new ProfessorModel(
                null,
                "Carlos Lima Atualizado",
                "52998224725",
                "carlos.novo@escola.com",
                "(11) 88888-7777"
        );
        ProfessorModel aposAtualizar = new ProfessorModel(
                1,
                "Carlos Lima Atualizado",
                "52998224725",
                "carlos.novo@escola.com",
                "(11) 88888-7777"
        );

        when(professorRepository.findById(1)).thenReturn(Optional.of(professorBase));
        when(professorRepository.save(any(ProfessorModel.class))).thenReturn(aposAtualizar);

        ProfessorModel resultado = professorService.atualizar(1, dadosNovos);

        assertThat(resultado.getNome()).isEqualTo("Carlos Lima Atualizado");
        assertThat(resultado.getEmail()).isEqualTo("carlos.novo@escola.com");
        assertThat(resultado.getTelefone()).isEqualTo("(11) 88888-7777");

        // confirma que findById e save foram chamados exatamente uma vez cada
        verify(professorRepository, times(1)).findById(1);
        verify(professorRepository, times(1)).save(any(ProfessorModel.class));
    }

    @Test
    @DisplayName("atualizar – lança RuntimeException quando ID não existe")
    void atualizar_idInexistente_lancaExcecao() {
        ProfessorModel qualquer = new ProfessorModel(
                null, "X", "52998224725", null, null
        );

        when(professorRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.atualizar(99, qualquer))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Professor não encontrado");

        verify(professorRepository, never()).save(any());
    }

    // -------------------------------------------------------
    // deletar
    // -------------------------------------------------------

    @Test
    @DisplayName("deletar – remove professor existente sem lançar exceção")
    void deletar_idExistente_deletaSemExcecao() {
        when(professorRepository.findById(1)).thenReturn(Optional.of(professorBase));
        doNothing().when(professorRepository).delete(professorBase);

        assertThatCode(() -> professorService.deletar(1))
                .doesNotThrowAnyException();

        verify(professorRepository, times(1)).findById(1);
        verify(professorRepository, times(1)).delete(professorBase);
    }

    @Test
    @DisplayName("deletar – lança RuntimeException quando ID não existe")
    void deletar_idInexistente_lancaExcecao() {
        when(professorRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> professorService.deletar(99))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Professor não encontrado");

        verify(professorRepository, never()).delete(any());
    }
}
