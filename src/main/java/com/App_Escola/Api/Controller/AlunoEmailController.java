package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AlunoEmailModel;
import com.App_Escola.Api.Repository.AlunoEmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno-email")
@CrossOrigin(origins = "*")
public class AlunoEmailController {

    @Autowired
    private AlunoEmailRepository repository;

    @GetMapping("/listar")
    public List<AlunoEmailModel> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoEmailModel> buscarPorId(@PathVariable Integer id) {
        Optional<AlunoEmailModel> email = repository.findById(id);
        
        if (email.isPresent()) {
            return ResponseEntity.ok(email.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public AlunoEmailModel cadastrar(@RequestBody AlunoEmailModel email) {
        return repository.save(email);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoEmailModel> updateEmail(@PathVariable Integer id, @RequestBody AlunoEmailModel dados) {
        Optional<AlunoEmailModel> alunoEmail = repository.findById(id);

        if (alunoEmail.isPresent()) {
            AlunoEmailModel emailExistente = alunoEmail.get();
            emailExistente.setEmail(dados.getEmail());
            emailExistente.setAluno(dados.getAluno());
            
            return ResponseEntity.ok(repository.save(emailExistente));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmail(@PathVariable Integer id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}