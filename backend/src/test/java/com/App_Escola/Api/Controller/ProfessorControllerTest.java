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

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Testes de camada web para ProfessorController.
 *
 * Usa MockMvcBuilders.standaloneSetup() — zero contexto Spring, zero conexão
 * com banco de dados. Compatível com Spring Boot 4.x (que removeu @WebMvcTest).
 *
 * O GlobalExceptionHandler é registrado via setControllerAdvice() para que os
 * cenários de erro (400) sejam testados com o handler de produção real.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("ProfessorController – testes de camada web (MockMvc standalone)")
class ProfessorControllerTest {

    @Mock
    private ProfessorService professorService;

    @InjectMocks
    private ProfessorController professorController;

    private MockMvc mockMvc;
    private ProfessorModel professorBase;

    // JSON manual — evita dependência do ObjectMapper de produção nos testes
    private static final String PROFESSOR_JSON =
            """
            {
              "nome": "Carlos Lima",
              "cpf": "52998224725",
              "email": "carlos@escola.com",
              "telefone": "(11) 99999-0001"
            }
            """;

    @BeforeEach
    void setUp() {
        professorBase = new ProfessorModel(
                1,
                "Carlos Lima",
                "52998224725",
                "carlos@escola.com",
                "(11) 99999-0001"
        );

        // Sobe MockMvc sem contexto Spring: registra o controller + advice + paginação
        mockMvc = MockMvcBuilders
                .standaloneSetup(professorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    // -------------------------------------------------------
    // GET /professores
    // -------------------------------------------------------

    @Test
    @DisplayName("GET /professores – retorna 200 e lista paginada")
    void listar_comProfessores_retorna200EConteudoPaginado() throws Exception {
        Page<ProfessorModel> pagina = new PageImpl<>(
                List.of(professorBase),
                PageRequest.of(0, 10),
                1
        );

        when(professorService.listarTodos(any())).thenReturn(pagina);

        mockMvc.perform(get("/professores")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].idProfessor").value(1))
                .andExpect(jsonPath("$.content[0].nome").value("Carlos Lima"))
                .andExpect(jsonPath("$.content[0].cpf").value("52998224725"))
                .andExpect(jsonPath("$.content[0].email").value("carlos@escola.com"))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1));

        verify(professorService, times(1)).listarTodos(any());
    }

    // -------------------------------------------------------
    // GET /professores/{id}
    // -------------------------------------------------------

    @Test
    @DisplayName("GET /professores/{id} – retorna 200 e JSON do professor quando ID existe")
    void buscar_idExistente_retorna200EProfessor() throws Exception {
        when(professorService.buscarPorId(1)).thenReturn(professorBase);

        mockMvc.perform(get("/professores/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idProfessor").value(1))
                .andExpect(jsonPath("$.nome").value("Carlos Lima"))
                .andExpect(jsonPath("$.cpf").value("52998224725"))
                .andExpect(jsonPath("$.email").value("carlos@escola.com"))
                .andExpect(jsonPath("$.telefone").value("(11) 99999-0001"));

        verify(professorService, times(1)).buscarPorId(1);
    }

    @Test
    @DisplayName("GET /professores/{id} – retorna 400 com mensagem quando ID não existe")
    void buscar_idInexistente_retorna400ComMensagem() throws Exception {
        when(professorService.buscarPorId(99))
                .thenThrow(new RuntimeException("Professor não encontrado"));

        mockMvc.perform(get("/professores/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Professor não encontrado"))
                .andExpect(jsonPath("$.status").value(400));

        verify(professorService, times(1)).buscarPorId(99);
    }

    // -------------------------------------------------------
    // POST /professores
    // -------------------------------------------------------

    @Test
    @DisplayName("POST /professores – retorna 201 e JSON do professor criado")
    void cadastrar_dadosValidos_retorna201EProfessorCriado() throws Exception {
        when(professorService.salvar(any(ProfessorModel.class))).thenReturn(professorBase);

        mockMvc.perform(post("/professores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROFESSOR_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idProfessor").value(1))
                .andExpect(jsonPath("$.nome").value("Carlos Lima"))
                .andExpect(jsonPath("$.cpf").value("52998224725"));

        verify(professorService, times(1)).salvar(any(ProfessorModel.class));
    }

    // -------------------------------------------------------
    // PUT /professores/{id}
    // -------------------------------------------------------

    @Test
    @DisplayName("PUT /professores/{id} – retorna 200 e JSON atualizado")
    void atualizar_idExistente_retorna200EProfessorAtualizado() throws Exception {
        ProfessorModel aposAtualizar = new ProfessorModel(
                1,
                "Carlos Lima Atualizado",
                "52998224725",
                "carlos.novo@escola.com",
                "(11) 88888-7777"
        );
        String jsonNovos =
                """
                {
                  "nome": "Carlos Lima Atualizado",
                  "cpf": "52998224725",
                  "email": "carlos.novo@escola.com",
                  "telefone": "(11) 88888-7777"
                }
                """;

        when(professorService.atualizar(eq(1), any(ProfessorModel.class)))
                .thenReturn(aposAtualizar);

        mockMvc.perform(put("/professores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonNovos)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProfessor").value(1))
                .andExpect(jsonPath("$.nome").value("Carlos Lima Atualizado"))
                .andExpect(jsonPath("$.email").value("carlos.novo@escola.com"))
                .andExpect(jsonPath("$.telefone").value("(11) 88888-7777"));

        verify(professorService, times(1)).atualizar(eq(1), any(ProfessorModel.class));
    }

    // -------------------------------------------------------
    // DELETE /professores/{id}
    // -------------------------------------------------------

    @Test
    @DisplayName("DELETE /professores/{id} – retorna 204 No Content quando professor existe")
    void deletar_idExistente_retorna204() throws Exception {
        doNothing().when(professorService).deletar(1);

        mockMvc.perform(delete("/professores/1"))
                .andExpect(status().isNoContent());

        verify(professorService, times(1)).deletar(1);
    }

    @Test
    @DisplayName("DELETE /professores/{id} – retorna 400 quando professor não existe")
    void deletar_idInexistente_retorna400ComMensagem() throws Exception {
        doThrow(new RuntimeException("Professor não encontrado"))
                .when(professorService).deletar(99);

        mockMvc.perform(delete("/professores/99")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Professor não encontrado"))
                .andExpect(jsonPath("$.status").value(400));

        verify(professorService, times(1)).deletar(99);
    }
}
