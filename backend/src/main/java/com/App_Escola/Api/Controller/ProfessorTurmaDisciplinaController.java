package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.ProfessorTurmaDisciplinaModel;
import com.App_Escola.Api.Service.ProfessorTurmaDisciplinaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/professores-turmas-disciplinas")
@CrossOrigin(origins = "*")
public class ProfessorTurmaDisciplinaController {

    private final ProfessorTurmaDisciplinaService service;

    public ProfessorTurmaDisciplinaController(
            ProfessorTurmaDisciplinaService service
    ) {

        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProfessorTurmaDisciplinaModel>> listar() {

        return ResponseEntity.ok(
                service.listarTodos()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorTurmaDisciplinaModel> buscarPorId(
            @PathVariable Integer id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<ProfessorTurmaDisciplinaModel> cadastrar(
            @RequestBody ProfessorTurmaDisciplinaModel relacionamento
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.salvar(relacionamento)
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorTurmaDisciplinaModel> atualizar(
            @PathVariable Integer id,
            @RequestBody ProfessorTurmaDisciplinaModel relacionamento
    ) {

        return ResponseEntity.ok(
                service.atualizar(
                        id,
                        relacionamento
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer id
    ) {

        service.deletar(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    @GetMapping("/professor/{idProfessor}")
    public ResponseEntity<List<ProfessorTurmaDisciplinaModel>>
    buscarPorProfessor(
            @PathVariable Integer idProfessor
    ) {

        return ResponseEntity.ok(
                service.buscarPorProfessor(idProfessor)
        );
    }

    @GetMapping("/turma/{idTurma}")
    public ResponseEntity<List<ProfessorTurmaDisciplinaModel>>
    buscarPorTurma(
            @PathVariable Integer idTurma
    ) {

        return ResponseEntity.ok(
                service.buscarPorTurma(idTurma)
        );
    }

    @GetMapping("/disciplina/{idDisciplina}")
    public ResponseEntity<List<ProfessorTurmaDisciplinaModel>>
    buscarPorDisciplina(
            @PathVariable Integer idDisciplina
    ) {

        return ResponseEntity.ok(
                service.buscarPorDisciplina(idDisciplina)
        );
    }
}