package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Model.ProfessorTurmaDisciplinaModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Service.ProfessorTurmaDisciplinaService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessorTurmaDisciplinaController – testes")
class ProfessorTurmaDisciplinaControllerTest {
    @Mock private ProfessorTurmaDisciplinaService service;
    @InjectMocks private ProfessorTurmaDisciplinaController controller;
    private MockMvc mockMvc;
    private ProfessorTurmaDisciplinaModel base;

    @BeforeEach void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        ProfessorModel professor = new ProfessorModel(1, "Carlos", "52998224725", "carlos@escola.com", "(11) 99999-0001");
        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        DisciplinaModel disciplina = new DisciplinaModel(1, "Matemática");
        base = new ProfessorTurmaDisciplinaModel(1, professor, turma, disciplina);
    }

    @Test void listar_retorna200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(base));
        mockMvc.perform(get("/professores-turmas-disciplinas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void buscarPorId_existente_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(base);
        mockMvc.perform(get("/professores-turmas-disciplinas/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProfessorTurmaDisciplina").value(1));
    }

    @Test void buscarPorId_inexistente_retorna400() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Relação entre professor, turma e disciplina não encontrada"));
        mockMvc.perform(get("/professores-turmas-disciplinas/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test void cadastrar_retorna201() throws Exception {
        when(service.salvar(any())).thenReturn(base);
        String json = "{\"professor\":{\"idProfessor\":1},\"turma\":{\"idTurma\":1},\"disciplina\":{\"idDisciplina\":1}}";
        mockMvc.perform(post("/professores-turmas-disciplinas")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProfessorTurmaDisciplina").value(1));
    }

    @Test void atualizar_existente_retorna200() throws Exception {
        when(service.atualizar(any(), any())).thenReturn(base);
        String json = "{\"professor\":{\"idProfessor\":1},\"turma\":{\"idTurma\":1},\"disciplina\":{\"idDisciplina\":1}}";
        mockMvc.perform(put("/professores-turmas-disciplinas/1")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProfessorTurmaDisciplina").value(1));
    }

    @Test void deletar_retorna204() throws Exception {
        doNothing().when(service).deletar(1);
        mockMvc.perform(delete("/professores-turmas-disciplinas/1")).andExpect(status().isNoContent());
    }

    @Test void buscarPorProfessor_retorna200() throws Exception {
        when(service.buscarPorProfessor(1)).thenReturn(List.of(base));
        mockMvc.perform(get("/professores-turmas-disciplinas/professor/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void buscarPorTurma_retorna200() throws Exception {
        when(service.buscarPorTurma(1)).thenReturn(List.of(base));
        mockMvc.perform(get("/professores-turmas-disciplinas/turma/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void buscarPorDisciplina_retorna200() throws Exception {
        when(service.buscarPorDisciplina(1)).thenReturn(List.of(base));
        mockMvc.perform(get("/professores-turmas-disciplinas/disciplina/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }
}
