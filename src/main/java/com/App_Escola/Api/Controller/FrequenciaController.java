package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.FrequenciaModel;
import com.App_Escola.Api.Repository.FrequenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/frequencia")
@CrossOrigin(origins = "*")
public class FrequenciaController {

    @Autowired
    private FrequenciaRepository repository;

    @GetMapping("/listar")
    public List<FrequenciaModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FrequenciaModel> buscarPorId(@PathVariable Integer id) {
        Optional<FrequenciaModel> frequencia = repository.findById(id);
        if (frequencia.isPresent()) {
            return ResponseEntity.ok(frequencia.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public FrequenciaModel cadastrar(@RequestBody FrequenciaModel frequencia) {
        return repository.save(frequencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FrequenciaModel> atualizar(@PathVariable Integer id, @RequestBody FrequenciaModel dados) {
        Optional<FrequenciaModel> frequencia = repository.findById(id);
        if (frequencia.isPresent()) {
            FrequenciaModel existente = frequencia.get();
            // TODO: Atualize os setters de FrequenciaModel
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