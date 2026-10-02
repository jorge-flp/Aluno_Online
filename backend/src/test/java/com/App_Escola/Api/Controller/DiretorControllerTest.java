package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.DiretorModel;
import com.App_Escola.Api.Service.DiretorService;

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
@DisplayName("DiretorController – testes de camada web")
class DiretorControllerTest {

    @Mock private DiretorService service;
    @InjectMocks private DiretorController controller;

    private MockMvc mockMvc;
    private DiretorModel base;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        base = new DiretorModel(1, "Roberto Alves", "roberto@escola.com", "(21) 98888-1111");
    }

    @Test
    @DisplayName("GET /diretores – 200 com página")
    void listar_retorna200() throws Exception {
        Page<DiretorModel> pagina = new PageImpl<>(List.of(base), PageRequest.of(0, 10), 1);
        when(service.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/diretores").accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Roberto Alves"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("GET /diretores/{id} – 200 quando existe")
    void buscar_existente_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(base);

        mockMvc.perform(get("/diretores/1").accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Roberto Alves"));
    }

    @Test
    @DisplayName("GET /diretores/{id} – 400 quando não existe")
    void buscar_inexistente_retorna400() throws Exception {
        when(service.buscarPorId(99))
                .thenThrow(new RuntimeException("Diretor não encontrado"));

        mockMvc.perform(get("/diretores/99").accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Diretor não encontrado"));
    }

    @Test
    @DisplayName("POST /diretores – 201 com diretor criado")
    void cadastrar_retorna201() throws Exception {
        when(service.salvar(any())).thenReturn(base);

        mockMvc.perform(post("/diretores")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Roberto Alves","email":"roberto@escola.com","telefone":"(21) 98888-1111"}
                                """)
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Roberto Alves"));
    }

    @Test
    @DisplayName("PUT /diretores/{id} – 200 com dados atualizados")
    void atualizar_existente_retorna200() throws Exception {
        DiretorModel atualizado = new DiretorModel(1, "Roberto Atualizado", "roberto.novo@escola.com", "(21) 99999-0000");
        when(service.atualizar(eq(1), any())).thenReturn(atualizado);

        mockMvc.perform(put("/diretores/1")
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Roberto Atualizado","email":"roberto.novo@escola.com","telefone":"(21) 99999-0000"}
                                """)
                        .accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Roberto Atualizado"));
    }

    @Test
    @DisplayName("DELETE /diretores/{id} – 204 quando existe")
    void excluir_existente_retorna204() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/diretores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /diretores/{id} – 400 quando não existe")
    void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Diretor não encontrado")).when(service).deletar(99);

        mockMvc.perform(delete("/diretores/99").accept(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Diretor não encontrado"));
    }
}
