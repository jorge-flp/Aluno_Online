package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.ResponsavelModel;
import com.App_Escola.Api.Service.ResponsavelService;

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
@DisplayName("ResponsavelController – testes de camada web")
class ResponsavelControllerTest {

    @Mock private ResponsavelService service;
    @InjectMocks private ResponsavelController controller;

    private MockMvc mockMvc;
    private ResponsavelModel responsavelBase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        responsavelBase = new ResponsavelModel(1, "João Silva",
                "52998224725", "joao@email.com", "(11) 91234-5678");
    }

    @Test
    @DisplayName("GET /responsaveis – 200 com página")
    void listar_retorna200() throws Exception {
        Page<ResponsavelModel> pagina =
                new PageImpl<>(List.of(responsavelBase), PageRequest.of(0, 10), 1);
        when(service.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/responsaveis").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].idResponsavel").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("João Silva"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("GET /responsaveis/{id} – 200 quando existe")
    void buscar_existente_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(responsavelBase);

        mockMvc.perform(get("/responsaveis/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idResponsavel").value(1))
                .andExpect(jsonPath("$.nome").value("João Silva"));
    }

    @Test
    @DisplayName("GET /responsaveis/{id} – 400 quando não existe")
    void buscar_inexistente_retorna400() throws Exception {
        when(service.buscarPorId(99))
                .thenThrow(new RuntimeException("Responsável não encontrado"));

        mockMvc.perform(get("/responsaveis/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Responsável não encontrado"));
    }

    @Test
    @DisplayName("POST /responsaveis – 201 com responsável criado")
    void cadastrar_retorna201() throws Exception {
        when(service.salvar(any())).thenReturn(responsavelBase);

        mockMvc.perform(post("/responsaveis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"João Silva","cpf":"52998224725",
                                 "email":"joao@email.com","telefone":"(11) 91234-5678"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idResponsavel").value(1));
    }

    @Test
    @DisplayName("PUT /responsaveis/{id} – 200 com dados atualizados")
    void atualizar_existente_retorna200() throws Exception {
        ResponsavelModel atualizado = new ResponsavelModel(1, "João Atualizado",
                "52998224725", "joao.novo@email.com", "(11) 99999-0000");
        when(service.atualizar(eq(1), any())).thenReturn(atualizado);

        mockMvc.perform(put("/responsaveis/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"João Atualizado","cpf":"52998224725",
                                 "email":"joao.novo@email.com","telefone":"(11) 99999-0000"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João Atualizado"));
    }

    @Test
    @DisplayName("DELETE /responsaveis/{id} – 204 quando existe")
    void excluir_existente_retorna204() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/responsaveis/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /responsaveis/{id} – 400 quando não existe")
    void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Responsável não encontrado")).when(service).deletar(99);

        mockMvc.perform(delete("/responsaveis/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Responsável não encontrado"));
    }
}
