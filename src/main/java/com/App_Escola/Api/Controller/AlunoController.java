package com.App_Escola.Api.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Service.AlunoService;

@RestController
@RequestMapping("/aluno")
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping("/listar")
    public List<AlunoModel> listar() {
        return alunoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoModel> buscarPorID(@PathVariable Integer id) {

        Optional<AlunoModel> aluno = alunoService.buscarPorMatricula(id);

        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<AlunoModel> cadastrar(@RequestBody AlunoModel aluno) {

        AlunoModel alunoSalvo = alunoService.salvar(aluno);

        return ResponseEntity.ok(alunoSalvo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {

        alunoService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}