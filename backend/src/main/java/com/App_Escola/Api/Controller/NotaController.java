package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.NotaModel;
import com.App_Escola.Api.Repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nota")
@CrossOrigin(origins = "*")
public class NotaController {

    @Autowired
    private NotaRepository repository;

    @GetMapping("/listar")
    public List<NotaModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotaModel> buscarPorId(@PathVariable Integer id) {
        Optional<NotaModel> nota = repository.findById(id);
        if (nota.isPresent()) {
            return ResponseEntity.ok(nota.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public NotaModel cadastrar(@RequestBody NotaModel nota) {
        return repository.save(nota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaModel> atualizar(@PathVariable Integer id, @RequestBody NotaModel dados) {
        Optional<NotaModel> nota = repository.findById(id);
        if (nota.isPresent()) {
            NotaModel existente = nota.get();
            // TODO: Atualize os setters de NotaModel
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