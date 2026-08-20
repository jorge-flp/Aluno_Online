package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.CalendarioModel;
import com.App_Escola.Api.Repository.CalendarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calendario")
@CrossOrigin(origins = "*")

public class CalendarioController {
    @Autowired
    private CalendarioRepository repository;

    @GetMapping("/listar")
    public List<CalendarioModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioModel> buscarPorId(@PathVariable Integer id) {
        Optional<CalendarioModel> calendario = repository.findById(id);
        if (calendario.isPresent()) {
            return ResponseEntity.ok(calendario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public CalendarioModel cadastrar(@RequestBody CalendarioModel calendario) {
        return repository.save(calendario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarioModel> atualizar(@PathVariable Integer id, @RequestBody CalendarioModel dados) {
        Optional<CalendarioModel> calendario = repository.findById(id);
        if (calendario.isPresent()) {
            CalendarioModel existente = calendario.get();
            // TODO: Atualize os setters de CalendarioModel
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
