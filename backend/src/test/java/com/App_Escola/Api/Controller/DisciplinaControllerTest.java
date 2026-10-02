package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Service.DisciplinaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DisciplinaController – testes de camada web")
class DisciplinaControllerTest {

    @Mock private DisciplinaService disciplinaService;
    @InjectMocks private DisciplinaController disciplinaController;

    private MockMvc mockMvc;
    private DisciplinaModel disciplinaBase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(disciplinaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        disciplinaBase = new DisciplinaModel(1, "Matemática");
    }

    @Test
    @DisplayName("GET /disciplinas – 200 com lista")
    void listar_retorna200() throws Exception {
        when(disciplinaService.listarTodos()).thenReturn(List.of(disciplinaBase));

        mockMvc.perform(get("/disciplinas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].idDisciplina").value(1))
                .andExpect(jsonPath("$[0].nome").value("Matemática"));
    }

    @Test
    @DisplayName("GET /disciplinas/{id} – 200 quando existe")
    void buscar_existente_retorna200() throws Exception {
        when(disciplinaService.buscarPorId(1)).thenReturn(disciplinaBase);

        mockMvc.perform(get("/disciplinas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idDisciplina").value(1))
                .andExpect(jsonPath("$.nome").value("Matemática"));
    }

    @Test
    @DisplayName("GET /disciplinas/{id} – 400 quando não existe")
    void buscar_inexistente_retorna400() throws Exception {
        when(disciplinaService.buscarPorId(99))
                .thenThrow(new RuntimeException("Disciplina não encontrada"));

        mockMvc.perform(get("/disciplinas/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Disciplina não encontrada"));
    }

    @Test
    @DisplayName("POST /disciplinas – 201 com disciplina criada")
    void cadastrar_retorna201() throws Exception {
        when(disciplinaService.salvar(any())).thenReturn(disciplinaBase);

        mockMvc.perform(post("/disciplinas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Matemática"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idDisciplina").value(1))
                .andExpect(jsonPath("$.nome").value("Matemática"));
    }

    @Test
    @DisplayName("PUT /disciplinas/{id} – 200 com dados atualizados")
    void atualizar_existente_retorna200() throws Exception {
        DisciplinaModel atualizada = new DisciplinaModel(1, "Matemática Avançada");
        when(disciplinaService.atualizar(eq(1), any())).thenReturn(atualizada);

        mockMvc.perform(put("/disciplinas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Matemática Avançada"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Matemática Avançada"));
    }

    @Test
    @DisplayName("DELETE /disciplinas/{id} – 204 quando existe")
    void deletar_existente_retorna204() throws Exception {
        doNothing().when(disciplinaService).deletar(1);

        mockMvc.perform(delete("/disciplinas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /disciplinas/{id} – 400 quando não existe")
    void deletar_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Disciplina não encontrada"))
                .when(disciplinaService).deletar(99);

        mockMvc.perform(delete("/disciplinas/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Disciplina não encontrada"));
    }
}
