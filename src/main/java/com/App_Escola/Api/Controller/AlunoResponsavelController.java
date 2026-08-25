package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AlunoResponsavelModel;
import com.App_Escola.Api.Service.AlunoResponsavelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno-responsavel")
@CrossOrigin(origins = "*")
public class AlunoResponsavelController {

    @Autowired
    private AlunoResponsavelService service;

    @GetMapping("/listar")
    public List<AlunoResponsavelModel> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponsavelModel> buscarPorId(@PathVariable Integer id) {
        Optional<AlunoResponsavelModel> alunoResponsavel = service.buscarPorId(id);
        
        if (alunoResponsavel.isPresent()) {
            return ResponseEntity.ok(alunoResponsavel.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public AlunoResponsavelModel cadastrar(@RequestBody AlunoResponsavelModel alunoResponsavel) {
        return service.salvar(alunoResponsavel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoResponsavelModel> updateAlunoResponsavel(@PathVariable Integer id, @RequestBody AlunoResponsavelModel dados) {
        AlunoResponsavelModel alunoResponsavelAtualizado = service.atualizar(id, dados);

        if (alunoResponsavelAtualizado != null) {
            return ResponseEntity.ok(alunoResponsavelAtualizado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlunoResponsavel(@PathVariable Integer id) {
        boolean deletado = service.deletar(id);
        if (!deletado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}