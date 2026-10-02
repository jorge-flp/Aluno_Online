package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Exception.GlobalExceptionHandler;
import com.App_Escola.Api.Model.BoletimConceitoModel;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Model.BoletimNotasModel;
import com.App_Escola.Api.Service.BoletimService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BoletimController – testes")
class BoletimControllerTest {
    @Mock private BoletimService boletimService;
    @InjectMocks private BoletimController boletimController;
    private MockMvc mockMvc;

    @BeforeEach void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(boletimController)
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test void buscarNotas_matriculaExistente_retorna200() throws Exception {
        BoletimNotasModel boletim = new BoletimNotasModel();
        when(boletimService.buscarNotas(100)).thenReturn(boletim);
        mockMvc.perform(get("/boletins/aluno/100/notas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test void buscarNotas_matriculaInexistente_retorna400() throws Exception {
        when(boletimService.buscarNotas(99)).thenThrow(new RuntimeException("Aluno não encontrado"));
        mockMvc.perform(get("/boletins/aluno/99/notas").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test void buscarConceito_matriculaExistente_retorna200() throws Exception {
        BoletimConceitoModel conceito = new BoletimConceitoModel(100, "Maria", 8.5, "A", "Excelente desempenho acadêmico.");
        when(boletimService.buscarConceito(100)).thenReturn(conceito);
        mockMvc.perform(get("/boletins/aluno/100/conceito").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test void gerarBoletim_retorna200() throws Exception {
        BoletimModel boletim = new BoletimModel();
        when(boletimService.gerarBoletim(any(), any())).thenReturn(boletim);
        mockMvc.perform(post("/boletins/aluno/100/gerar?anoLetivo=2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test void listarHistorico_retorna200() throws Exception {
        when(boletimService.listarBoletinsDoAluno(100)).thenReturn(List.of());
        mockMvc.perform(get("/boletins/aluno/100/historico").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test void buscarPorAno_retorna200() throws Exception {
        BoletimModel boletim = new BoletimModel();
        when(boletimService.buscarBoletim(100, 2024)).thenReturn(boletim);
        mockMvc.perform(get("/boletins/aluno/100/ano/2024").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test void deletar_retorna204() throws Exception {
        doNothing().when(boletimService).deletarBoletim(1);
        mockMvc.perform(delete("/boletins/1")).andExpect(status().isNoContent());
    }
}
