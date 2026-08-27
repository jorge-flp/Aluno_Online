package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turma")
@CrossOrigin(origins = "*")
public class TurmaController {

    @Autowired
    private TurmaRepository repository;

    @GetMapping("/listar")
    public List<TurmaModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaModel> buscarPorId(@PathVariable Integer id) {
        Optional<TurmaModel> turma = repository.findById(id);
        if (turma.isPresent()) {
            return ResponseEntity.ok(turma.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public TurmaModel cadastrar(@RequestBody TurmaModel turma) {
        return repository.save(turma);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaModel> atualizar(@PathVariable Integer id, @RequestBody TurmaModel dados) {
        Optional<TurmaModel> turma = repository.findById(id);
        if (turma.isPresent()) {
            TurmaModel existente = turma.get();
            // TODO: Atualize os setters de TurmaModel
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