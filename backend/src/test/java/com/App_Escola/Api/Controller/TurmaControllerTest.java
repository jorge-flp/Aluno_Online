package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Service.TurmaService;

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

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TurmaController – testes de camada web")
class TurmaControllerTest {

    @Mock private TurmaService turmaService;
    @InjectMocks private TurmaController turmaController;

    private MockMvc mockMvc;
    private TurmaModel turmaBase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(turmaController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        turmaBase = new TurmaModel(1, "5°", "5° Ano A", List.of());
    }

    @Test
    @DisplayName("GET /turmas – 200 com página")
    void listar_retorna200() throws Exception {
        Page<TurmaModel> pagina = new PageImpl<>(List.of(turmaBase), PageRequest.of(0, 10), 1);
        when(turmaService.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/turmas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idTurma").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("5° Ano A"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("GET /turmas/{id} – 200 quando existe")
    void buscar_existente_retorna200() throws Exception {
        when(turmaService.buscarPorId(1)).thenReturn(turmaBase);

        mockMvc.perform(get("/turmas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idTurma").value(1))
                .andExpect(jsonPath("$.nome").value("5° Ano A"));
    }

    @Test
    @DisplayName("GET /turmas/{id} – 400 quando não existe")
    void buscar_inexistente_retorna400() throws Exception {
        when(turmaService.buscarPorId(99)).thenThrow(new RuntimeException("Turma não encontrada"));

        mockMvc.perform(get("/turmas/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Turma não encontrada"));
    }

    @Test
    @DisplayName("POST /turmas – 201 com turma criada")
    void cadastrar_retorna201() throws Exception {
        when(turmaService.salvar(any())).thenReturn(turmaBase);

        mockMvc.perform(post("/turmas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"anoSerie":"5°","nome":"5° Ano A"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idTurma").value(1));
    }

    @Test
    @DisplayName("PUT /turmas/{id} – 200 com dados atualizados")
    void atualizar_existente_retorna200() throws Exception {
        TurmaModel atualizada = new TurmaModel(1, "5° Novo", "5° Ano B", List.of());
        when(turmaService.atualizar(eq(1), any())).thenReturn(atualizada);

        mockMvc.perform(put("/turmas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"anoSerie":"5° Novo","nome":"5° Ano B"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("5° Ano B"));
    }

    @Test
    @DisplayName("DELETE /turmas/{id} – 204 quando existe")
    void excluir_existente_retorna204() throws Exception {
        doNothing().when(turmaService).deletar(1);

        mockMvc.perform(delete("/turmas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /turmas/{id} – 400 quando não existe")
    void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Turma não encontrada")).when(turmaService).deletar(99);

        mockMvc.perform(delete("/turmas/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Turma não encontrada"));
    }
}
