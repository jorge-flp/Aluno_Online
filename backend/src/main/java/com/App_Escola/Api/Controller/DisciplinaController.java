package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Repository.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/disciplina")
@CrossOrigin(origins = "*")


public class DisciplinaController {
    @Autowired
    private DisciplinaRepository repository;

    @GetMapping("/listar")
    public List<DisciplinaModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaModel> buscarPorId(@PathVariable Integer id) {
        Optional<DisciplinaModel> disciplina = repository.findById(id);
        if (disciplina.isPresent()) {
            return ResponseEntity.ok(disciplina.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public DisciplinaModel cadastrar(@RequestBody DisciplinaModel disciplina) {
        return repository.save(disciplina);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaModel> atualizar(@PathVariable Integer id, @RequestBody DisciplinaModel dados) {
        Optional<DisciplinaModel> disciplina = repository.findById(id);
        if (disciplina.isPresent()) {
            DisciplinaModel existente = disciplina.get();
            // TODO: Atualize os setters de DisciplinaModel
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
