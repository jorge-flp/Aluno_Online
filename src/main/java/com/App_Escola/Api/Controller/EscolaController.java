package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.EscolaModel;
import com.App_Escola.Api.Repository.EscolaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/escola")
@CrossOrigin(origins = "*")
public class EscolaController {

    @Autowired
    private EscolaRepository repository;

    @GetMapping("/listar")
    public List<EscolaModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EscolaModel> buscarPorId(@PathVariable Integer id) {
        Optional<EscolaModel> escola = repository.findById(id);
        if (escola.isPresent()) {
            return ResponseEntity.ok(escola.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public EscolaModel cadastrar(@RequestBody EscolaModel escola) {
        return repository.save(escola);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EscolaModel> atualizar(@PathVariable Integer id, @RequestBody EscolaModel dados) {
        Optional<EscolaModel> escola = repository.findById(id);
        if (escola.isPresent()) {
            EscolaModel existente = escola.get();
            // TODO: Atualize os setters de EscolaModel
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