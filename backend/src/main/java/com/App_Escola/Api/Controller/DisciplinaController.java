package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Service.DisciplinaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(
            DisciplinaService disciplinaService
    ) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaModel>> listar() {

        return ResponseEntity.ok(
                disciplinaService.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaModel> buscar(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                disciplinaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<DisciplinaModel> cadastrar(
            @RequestBody DisciplinaModel disciplina
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        disciplinaService.salvar(disciplina)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaModel> atualizar(
            @PathVariable Integer id,
            @RequestBody DisciplinaModel disciplina
    ) {

        return ResponseEntity.ok(
                disciplinaService.atualizar(
                        id,
                        disciplina
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id
    ) {

        disciplinaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}