package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Service.AlunoService;

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
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AlunoController – testes de camada web")
class AlunoControllerTest {

    @Mock private AlunoService alunoService;
    @InjectMocks private AlunoController alunoController;

    private MockMvc mockMvc;
    private AlunoModel alunoBase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(alunoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();

        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        alunoBase = new AlunoModel(100, "Maria Souza", "52998224725",
                "maria@escola.com", LocalDate.of(2012, 3, 20), turma);
    }

    @Test
    @DisplayName("GET /alunos – 200 com página")
    void listar_retorna200() throws Exception {
        Page<AlunoModel> pagina = new PageImpl<>(List.of(alunoBase), PageRequest.of(0, 10), 1);
        when(alunoService.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/alunos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].matricula").value(100))
                .andExpect(jsonPath("$.content[0].nome").value("Maria Souza"))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(alunoService).listarTodos(any());
    }

    @Test
    @DisplayName("GET /alunos/{matricula} – 200 quando existe")
    void buscarPorMatricula_existente_retorna200() throws Exception {
        when(alunoService.buscarPorMatricula(100)).thenReturn(alunoBase);

        mockMvc.perform(get("/alunos/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matricula").value(100))
                .andExpect(jsonPath("$.nome").value("Maria Souza"));

        verify(alunoService).buscarPorMatricula(100);
    }

    @Test
    @DisplayName("GET /alunos/{matricula} – 400 quando não existe")
    void buscarPorMatricula_inexistente_retorna400() throws Exception {
        when(alunoService.buscarPorMatricula(99))
                .thenThrow(new RuntimeException("Aluno não encontrado"));

        mockMvc.perform(get("/alunos/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Aluno não encontrado"));

        verify(alunoService).buscarPorMatricula(99);
    }

    @Test
    @DisplayName("POST /alunos – 201 com aluno criado")
    void cadastrar_retorna201() throws Exception {
        when(alunoService.salvar(any())).thenReturn(alunoBase);

        String json = """
                {"nome":"Maria Souza","cpf":"52998224725","email":"maria@escola.com",
                 "dataNascimento":"2012-03-20","turma":{"idTurma":1}}""";

        mockMvc.perform(post("/alunos")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.matricula").value(100))
                .andExpect(jsonPath("$.nome").value("Maria Souza"));

        verify(alunoService).salvar(any());
    }

    @Test
    @DisplayName("DELETE /alunos/{matricula} – 204 quando existe")
    void excluir_existente_retorna204() throws Exception {
        doNothing().when(alunoService).deletar(100);

        mockMvc.perform(delete("/alunos/100"))
                .andExpect(status().isNoContent());

        verify(alunoService).deletar(100);
    }

    @Test
    @DisplayName("DELETE /alunos/{matricula} – 400 quando não existe")
    void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Aluno não encontrado")).when(alunoService).deletar(99);

        mockMvc.perform(delete("/alunos/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Aluno não encontrado"));

        verify(alunoService).deletar(99);
    }
}
