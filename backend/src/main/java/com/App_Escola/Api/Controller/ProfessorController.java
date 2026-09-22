package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Service.ProfessorService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professores")
@CrossOrigin(origins = "*")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(
            ProfessorService professorService
    ) {
        this.professorService = professorService;
    }

    @GetMapping
    public ResponseEntity<Page<ProfessorModel>> listar(
            @PageableDefault(size = 10)
            Pageable pageable
    ) {

        return ResponseEntity.ok(
                professorService.listarTodos(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorModel> buscar(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                professorService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProfessorModel> cadastrar(
            @Valid
            @RequestBody ProfessorModel professor
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        professorService.salvar(professor)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorModel> atualizar(
            @PathVariable Integer id,
            @Valid
            @RequestBody ProfessorModel professor
    ) {

        return ResponseEntity.ok(
                professorService.atualizar(
                        id,
                        professor
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id
    ) {

        professorService.deletar(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}