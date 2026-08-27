package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AtividadeModel;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Repository.AtividadeRepository;
import com.App_Escola.Api.Repository.BoletimRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/boletim")
@CrossOrigin(origins = "*")

public class BoletimController {
    @Autowired
    private BoletimRepository repository;

    @GetMapping("/listar")
    public List<BoletimModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoletimModel> buscarPorId(@PathVariable Integer id) {
        Optional<BoletimModel> boletim = repository.findById(id);
        if (boletim.isPresent()) {
            return ResponseEntity.ok(boletim.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public BoletimModel cadastrar(@RequestBody BoletimModel boletim) {
        return repository.save(boletim);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoletimModel> atualizar(@PathVariable Integer id, @RequestBody BoletimModel dados) {
        Optional<BoletimModel> boletim = repository.findById(id);
        if (boletim.isPresent()) {
            BoletimModel existente = boletim.get();
            
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
