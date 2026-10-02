package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.AlunoResponsavelModel;
import com.App_Escola.Api.Model.ResponsavelModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Service.AlunoResponsavelService;
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
import java.time.LocalDate;
import java.util.List;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AlunoResponsavelController – testes")
class AlunoResponsavelControllerTest {
    @Mock private AlunoResponsavelService service;
    @InjectMocks private AlunoResponsavelController controller;
    private MockMvc mockMvc;
    private AlunoResponsavelModel base;

    @BeforeEach void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        AlunoModel aluno = new AlunoModel(100, "Maria", "52998224725", "maria@escola.com", LocalDate.of(2012, 3, 20), turma);
        ResponsavelModel responsavel = new ResponsavelModel(1, "João", "52998224725", "joao@email.com", "(11) 91234-5678");
        base = new AlunoResponsavelModel(1, "Pai", aluno, responsavel);
    }

    @Test void listar_retorna200() throws Exception {
        when(service.listarTodos()).thenReturn(List.of(base));
        mockMvc.perform(get("/alunos-responsaveis").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void buscar_existente_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(base);
        mockMvc.perform(get("/alunos-responsaveis/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idAlunoResponsavel").value(1));
    }

    @Test void buscar_inexistente_retorna400() throws Exception {
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Relacionamento não encontrado"));
        mockMvc.perform(get("/alunos-responsaveis/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Relacionamento não encontrado"));
    }

    @Test void cadastrar_retorna201() throws Exception {
        when(service.salvar(any())).thenReturn(base);
        String json = "{\"parentesco\":\"Pai\",\"aluno\":{\"matricula\":100},\"responsavel\":{\"idResponsavel\":1}}";
        mockMvc.perform(post("/alunos-responsaveis")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idAlunoResponsavel").value(1));
    }

    @Test void atualizar_existente_retorna200() throws Exception {
        AlunoResponsavelModel atualizado = new AlunoResponsavelModel(1, "Mãe", base.getAluno(), base.getResponsavel());
        when(service.atualizar(eq(1), any())).thenReturn(atualizado);
        String json = "{\"parentesco\":\"Mãe\",\"aluno\":{\"matricula\":100},\"responsavel\":{\"idResponsavel\":1}}";
        mockMvc.perform(put("/alunos-responsaveis/1")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.parentesco").value("Mãe"));
    }

    @Test void excluir_existente_retorna204() throws Exception {
        doNothing().when(service).deletar(1);
        mockMvc.perform(delete("/alunos-responsaveis/1")).andExpect(status().isNoContent());
    }

    @Test void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Relacionamento não encontrado")).when(service).deletar(99);
        mockMvc.perform(delete("/alunos-responsaveis/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Relacionamento não encontrado"));
    }
}
