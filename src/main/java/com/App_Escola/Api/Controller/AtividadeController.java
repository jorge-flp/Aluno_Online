package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AtividadeModel;
import com.App_Escola.Api.Repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/atividade")
@CrossOrigin(origins = "*")
public class AtividadeController {

    @Autowired
    private AtividadeRepository repository;

    @GetMapping("/listar")
    public List<AtividadeModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeModel> buscarPorId(@PathVariable Integer id) {
        Optional<AtividadeModel> atividade = repository.findById(id);
        if (atividade.isPresent()) {
            return ResponseEntity.ok(atividade.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public AtividadeModel cadastrar(@RequestBody AtividadeModel atividade) {
        return repository.save(atividade);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AtividadeModel> atualizar(@PathVariable Integer id, @RequestBody AtividadeModel dados) {
        Optional<AtividadeModel> atividade = repository.findById(id);
        if (atividade.isPresent()) {
            AtividadeModel existente = atividade.get();
            
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