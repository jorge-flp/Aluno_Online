package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.NotaEscritaModel;
import com.App_Escola.Api.Service.NotaEscritaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/NotasEscritas")
public class NotaEscritaController {

    @Autowired
    private NotaEscritaService service;

    @PostMapping
    public ResponseEntity<NotaEscritaModel> criar(
            @RequestBody NotaEscritaModel nota) {

        return ResponseEntity.ok(service.criar(nota));
    }

    @GetMapping
    public ResponseEntity<List<NotaEscritaModel>> listarTodas() {

        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaEscritaModel> buscarPorId(
            @PathVariable Long id) {

        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<NotaEscritaModel>> listarPorAluno(
            @PathVariable Long matricula) {

        return ResponseEntity.ok(service.listarPorAluno(matricula));
    }

    @GetMapping("/professor/{id}")
    public ResponseEntity<List<NotaEscritaModel>> listarPorProfessor(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.listarPorProfessor(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaEscritaModel> atualizar(
            @PathVariable Long id,
            @RequestBody NotaEscritaModel dados) {

        return service.atualizar(id, dados)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id) {

        if (!service.excluir(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}