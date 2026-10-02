package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.CoordenadorModel;
import com.App_Escola.Api.Service.CoordenadorService;

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
@DisplayName("CoordenadorController – testes de camada web")
class CoordenadorControllerTest {

    @Mock private CoordenadorService service;
    @InjectMocks private CoordenadorController controller;

    private MockMvc mockMvc;
    private CoordenadorModel base;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        base = new CoordenadorModel(1L, "Carla Mendes", "carla@escola.com", "(11) 91111-2222");
    }

    @Test
    @DisplayName("GET /coordenadores – 200 com página")
    void listar_retorna200() throws Exception {
        Page<CoordenadorModel> pagina = new PageImpl<>(List.of(base), PageRequest.of(0, 10), 1);
        when(service.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/coordenadores").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("Carla Mendes"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @DisplayName("GET /coordenadores/{id} – 200 quando existe")
    void buscar_existente_retorna200() throws Exception {
        when(service.buscarPorId(1)).thenReturn(base);

        mockMvc.perform(get("/coordenadores/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carla Mendes"));
    }

    @Test
    @DisplayName("GET /coordenadores/{id} – 400 quando não existe")
    void buscar_inexistente_retorna400() throws Exception {
        when(service.buscarPorId(99))
                .thenThrow(new RuntimeException("Coordenador não encontrado"));

        mockMvc.perform(get("/coordenadores/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenador não encontrado"));
    }

    @Test
    @DisplayName("POST /coordenadores – 201 com coordenador criado")
    void cadastrar_retorna201() throws Exception {
        when(service.salvar(any())).thenReturn(base);

        mockMvc.perform(post("/coordenadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Carla Mendes","email":"carla@escola.com","telefone":"(11) 91111-2222"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Carla Mendes"));
    }

    @Test
    @DisplayName("PUT /coordenadores/{id} – 200 com dados atualizados")
    void atualizar_existente_retorna200() throws Exception {
        CoordenadorModel atualizado = new CoordenadorModel(1L, "Carla Atualizada", "carla.nova@escola.com", "(11) 99999-0000");
        when(service.atualizar(eq(1), any())).thenReturn(atualizado);

        mockMvc.perform(put("/coordenadores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"nome":"Carla Atualizada","email":"carla.nova@escola.com","telefone":"(11) 99999-0000"}
                                """)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carla Atualizada"));
    }

    @Test
    @DisplayName("DELETE /coordenadores/{id} – 204 quando existe")
    void excluir_existente_retorna204() throws Exception {
        doNothing().when(service).deletar(1);

        mockMvc.perform(delete("/coordenadores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /coordenadores/{id} – 400 quando não existe")
    void excluir_inexistente_retorna400() throws Exception {
        doThrow(new RuntimeException("Coordenador não encontrado")).when(service).deletar(99);

        mockMvc.perform(delete("/coordenadores/99").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Coordenador não encontrado"));
    }
}
