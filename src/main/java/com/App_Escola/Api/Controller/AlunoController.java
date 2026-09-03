package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    public ResponseEntity<List<AlunoModel>> listar() {
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AlunoModel> buscarPorMatricula(
            @PathVariable Integer matricula) {

        return ResponseEntity.ok(
                alunoService.buscarPorMatricula(matricula));
    }

    @PostMapping
    public ResponseEntity<AlunoModel> cadastrar(
            @RequestBody AlunoModel aluno) {

        AlunoModel alunoSalvo = alunoService.salvar(aluno);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alunoSalvo);
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer matricula) {

        alunoService.deletar(matricula);

        return ResponseEntity.noContent().build();
    }
}