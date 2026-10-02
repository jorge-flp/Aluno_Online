package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.SecretarioModel;
import com.App_Escola.Api.Service.SecretarioService;

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
@DisplayName("SecretarioController – testes de camada web")
class SecretarioControllerTest {

    @Mock private SecretarioService service;
    @InjectMocks private SecretarioController controller;

    private MockMvc mockMvc;
    private SecretarioModel base;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        base = new SecretarioModel(1, "Fernanda Lima", "fernanda@escola.com", "(31) 97777-3333");
    }

    @Test
    @DisplayName("GET /secretarios – 200 com página")
    void listar_retorna200() throws Exception {
        Page<SecretarioModel> pagina = new PageImpl<>(List.of(base), PageRequest.of(0, 10), 1);
        when(service.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/secretarios").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Fernanda Lima"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("GET /secretarios/{id} – 200 quando existe")
    void buscar_existente_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(base);

        mockMvc.perform(get("/secretarios/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fernanda Lima"));
    }

    @Test
    @DisplayName("GET /secretarios/{id} – 400 quando não existe")
    void buscar_inexistente_retorna400() throws Exception {
        when(service.buscarPorId(99))
                .thenThrow(new RuntimeException("Secretário não encontrado"));

        mockMvc.perform(get("/secretarios/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Secretário não encontrado"));
    }

    @Test
    @DisplayName("POST /secretarios – 201 com secretário criado")
    void cadastrar_retorna201() throws Exception {
        when(service.salvar(any())).thenReturn(base);

        mockMvc.perform(post("/secretarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Fernanda Lima","email":"fernanda@escola.com","telefone":"(31) 97777-3333"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Fernanda Lima"));
    }

    @Test
    @DisplayName("PUT /secretarios/{id} – 200 com dados atualizados")
    void atualizar_existente_retorna200() throws Exception {
        SecretarioModel atualizado = new SecretarioModel(1, "Fernanda Atualizada", "fernanda.nova@escola.com", "(31) 99999-0000");
        when(service.atualizar(eq(1), any())).thenReturn(atualizado);

        mockMvc.perform(put("/secretarios/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Fernanda Atualizada","email":"fernanda.nova@escola.com","telefone":"(31) 99999-0000"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Fernanda Atualizada"));
    }

    @Test
    @DisplayName("DELETE /secretarios/{id} – 204 quando existe")
    void excluir_existente_retorna204() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/secretarios/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /secretarios/{id} – 400 quando não existe")
    void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Secretário não encontrado")).when(service).deletar(99);

        mockMvc.perform(delete("/secretarios/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Secretário não encontrado"));
    }
}
