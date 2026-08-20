package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/professor")
@CrossOrigin(origins = "*")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;

    @GetMapping("/listar")
    public List<ProfessorModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfessorModel> buscarPorId(@PathVariable Integer id) {
        Optional<ProfessorModel> professor = repository.findById(id);
        if (professor.isPresent()) {
            return ResponseEntity.ok(professor.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public ProfessorModel cadastrar(@RequestBody ProfessorModel professor) {
        return repository.save(professor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfessorModel> atualizar(@PathVariable Integer id, @RequestBody ProfessorModel dados) {
        Optional<ProfessorModel> professor = repository.findById(id);
        if (professor.isPresent()) {
            ProfessorModel existente = professor.get();
            // TODO: Atualize os setters de ProfessorModel
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