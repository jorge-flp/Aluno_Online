package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.NotaModel;
import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Service.NotaService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("NotaController – testes")
class NotaControllerTest {
    @Mock private NotaService notaService;
    @InjectMocks private NotaController notaController;
    private MockMvc mockMvc;
    private NotaModel notaBase;

    @BeforeEach void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notaController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
        TurmaModel turma = new TurmaModel(1, "5°", "5° Ano A", List.of());
        AlunoModel aluno = new AlunoModel(100, "Maria", "52998224725", "maria@escola.com", LocalDate.of(2012, 3, 20), turma);
        DisciplinaModel disciplina = new DisciplinaModel(1, "Matemática");
        notaBase = new NotaModel(1, aluno, disciplina, 1, 8.0);
    }

    @Test void listar_retorna200() throws Exception {
        when(notaService.listarTodos()).thenReturn(List.of(notaBase));
        mockMvc.perform(get("/notas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void buscarPorAluno_retorna200() throws Exception {
        when(notaService.buscarPorAluno(100)).thenReturn(List.of(notaBase));
        mockMvc.perform(get("/notas/aluno/100").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test void cadastrar_retorna201() throws Exception {
        when(notaService.salvar(any())).thenReturn(notaBase);
        String json = "{\"aluno\":{\"matricula\":100},\"disciplina\":{\"idDisciplina\":1},\"bimestre\":1,\"valor\":8.0}";
        mockMvc.perform(post("/notas")
                        .contentType(MediaType.APPLICATION_JSON).content(json)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idNota").value(1));
    }

    @Test void deletar_retorna204() throws Exception {
        doNothing().when(notaService).deletar(1);
        mockMvc.perform(delete("/notas/1")).andExpect(status().isNoContent());
    }
}
