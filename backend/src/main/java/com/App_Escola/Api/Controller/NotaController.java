package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.NotaModel;
import com.App_Escola.Api.Service.NotaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotaController {

    private final NotaService notaService;

    public NotaController(
            NotaService notaService
    ) {
        this.notaService = notaService;
    }

    @GetMapping
    public ResponseEntity<List<NotaModel>> listar() {

        return ResponseEntity.ok(
                notaService.listarTodos()
        );
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<NotaModel>> buscarPorAluno(
            @PathVariable Integer matricula
    ) {

        return ResponseEntity.ok(
                notaService.buscarPorAluno(matricula)
        );
    }

    @PostMapping
    public ResponseEntity<NotaModel> cadastrar(
            @RequestBody NotaModel nota
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        notaService.salvar(nota)
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id
    ) {

        notaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}