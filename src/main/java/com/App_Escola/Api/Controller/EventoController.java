package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.EventoModel;
import com.App_Escola.Api.Repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/evento")
@CrossOrigin(origins = "*")
public class EventoController {

    @Autowired
    private EventoRepository repository;

    @GetMapping("/listar")
    public List<EventoModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoModel> buscarPorId(@PathVariable Integer id) {
        Optional<EventoModel> evento = repository.findById(id);
        if (evento.isPresent()) {
            return ResponseEntity.ok(evento.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public EventoModel cadastrar(@RequestBody EventoModel evento) {
        return repository.save(evento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventoModel> atualizar(@PathVariable Integer id, @RequestBody EventoModel dados) {
        Optional<EventoModel> evento = repository.findById(id);
        if (evento.isPresent()) {
            EventoModel existente = evento.get();
            
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