package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.JustificativaFaltaModel;
import com.App_Escola.Api.Service.JustificativaService;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/justificativas")
public class JustificativaController {

    private final JustificativaFaltaModel justificativaFaltaService;

    public JustificativaController(JustificativaFaltaModel justificativaFaltaService) {
        this.justificativaFaltaService = justificativaFaltaService;
    }

    @GetMapping
    public ResponseEntity<@Nullable Object> listarTodas() {
        return ResponseEntity.ok(JustificativaFaltaModel.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@Nullable Object> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(JustificativaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<JustificativaFaltaModel> criar(
            @RequestBody JustificativaFaltaModel justificativa) {

        JustificativaFaltaModel novaJustificativa = JustificativaFaltaModel.salvar(justificativa);

        return ResponseEntity.status(HttpStatus.CREATED).body(novaJustificativa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JustificativaFaltaModel> atualizar(
            @PathVariable Integer id,
            @RequestBody JustificativaFaltaModel justificativa) {

        JustificativaFaltaModel atualizada = JustificativaFaltaModel.atualizar(id, justificativa);

        return ResponseEntity.ok(atualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        JustificativaFaltaModel.deletar(id);

        return ResponseEntity.noContent().build();
    }
}