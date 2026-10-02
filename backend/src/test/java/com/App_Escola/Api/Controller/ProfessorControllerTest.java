package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Service.ProfessorService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessorController – testes")
class ProfessorControllerTest {
    @Mock private ProfessorService professorService;
    @InjectMocks private ProfessorController professorController;
    private MockMvc mockMvc;
    private ProfessorModel professorBase;

    @BeforeEach void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(professorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        professorBase = new ProfessorModel(1, "Carlos Lima", "52998224725", "carlos@escola.com", "(11) 99999-0001");
    }

    @Test void listar_retorna200() throws Exception {
        when(professorService.listarTodos(any())).thenReturn(new PageImpl<>(List.of(professorBase), PageRequest.of(0, 10), 1));
        mockMvc.perform(get("/professores").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idProfessor").value(1));
    }

    @Test void buscarExistente_retorna200() throws Exception {
        when(professorService.buscarPorId(1)).thenReturn(professorBase);
        mockMvc.perform(get("/professores/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProfessor").value(1));
    }

    @Test void buscarInexistente_retorna400() throws Exception {
        when(professorService.buscarPorId(99)).thenThrow(new RuntimeException("Professor não encontrado"));
        mockMvc.perform(get("/professores/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Professor não encontrado"));
    }

    @Test void cadastrar_retorna201() throws Exception {
        when(professorService.salvar(any())).thenReturn(professorBase);
        mockMvc.perform(post("/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Carlos Lima\",\"cpf\":\"52998224725\",\"email\":\"carlos@escola.com\",\"telefone\":\"(11) 99999-0001\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProfessor").value(1));
    }

    @Test void atualizar_retorna200() throws Exception {
        ProfessorModel atualizado = new ProfessorModel(1, "Carlos Atualizado", "52998224725", "carlos.novo@escola.com", "(11) 88888-7777");
        when(professorService.atualizar(eq(1), any())).thenReturn(atualizado);
        mockMvc.perform(put("/professores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Carlos Atualizado\",\"cpf\":\"52998224725\",\"email\":\"carlos.novo@escola.com\",\"telefone\":\"(11) 88888-7777\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos Atualizado"));
    }

    @Test void deletarExistente_retorna204() throws Exception {
        doNothing().when(professorService).deletar(1);
        mockMvc.perform(delete("/professores/1")).andExpect(status().isNoContent());
    }

    @Test void deletarInexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Professor não encontrado")).when(professorService).deletar(99);
        mockMvc.perform(delete("/professores/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Professor não encontrado"));
    }
}
