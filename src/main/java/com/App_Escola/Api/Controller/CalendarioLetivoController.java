package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.CalendarioLetivoModel;
import com.App_Escola.Api.Repository.CalendarioLetivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/calendario")
@CrossOrigin(origins = "*")
public class CalendarioLetivoController {

    @Autowired
    private CalendarioLetivoRepository repository;

    @GetMapping("/listar")
    public List<CalendarioLetivoModel> listar() {
        return repository.findAll(); // Corrigido para usar a instância 'repository'
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarioLetivoModel> buscarPorId(@PathVariable Integer id) {
        Optional<CalendarioLetivoModel> calendario = repository.findById(id);
        if (calendario.isPresent()) {
            return ResponseEntity.ok(calendario.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public CalendarioLetivoModel cadastrar(@RequestBody CalendarioLetivoModel calendario) {
        return repository.save(calendario); // Corrigido para usar a instância 'repository'
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarioLetivoModel> atualizar(@PathVariable Integer id,
            @RequestBody CalendarioLetivoModel dados) {
        Optional<CalendarioLetivoModel> calendario = repository.findById(id);
        if (calendario.isPresent()) {
            CalendarioLetivoModel existente = calendario.get();
            // TODO: Atualize os setters de CalendarioLetivoModel conforme os campos que
            // você tem
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