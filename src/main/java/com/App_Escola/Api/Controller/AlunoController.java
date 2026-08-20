package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")
@CrossOrigin(origins = "*")

public class AlunoController {

    @Autowired
    private AlunoRepository repository;

    @GetMapping("listar")
    public List<AlunoModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoModel> buscarPorID(@PathVariable Integer id) {
        Optional<AlunoModel> aluno = repository.findById(id);
        if (aluno.isPresent()) {
            return ResponseEntity.ok(aluno.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public AlunoModel cadastrar(@RequestBody AlunoModel aluno) {
        return repository.save(aluno);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoModel> atualizar(@PathVariable Integer id, @RequestBody AlunoModel dados) {
        Optional<AlunoModel> aluno = repository.findById(id);
        if (aluno.isPresent()) {
            AlunoModel existente = aluno.get();
            
            return ResponseEntity.ok(repository.save(existente));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
