package com.App_Escola.Api.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Service.DisciplinaService;

@RestController
@RequestMapping("/disciplina")
@CrossOrigin(origins = "*")
public class DisciplinaController {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @GetMapping("/listar")
    public List<DisciplinaModel> listar() {
        return disciplinaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaModel> buscarPorID(@PathVariable Integer id) {
        Optional<DisciplinaModel> disciplina = disciplinaService.buscarPorId(id);

        if (disciplina.isPresent()) {
            return ResponseEntity.ok(disciplina.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<DisciplinaModel> cadastrar(@RequestBody DisciplinaModel disciplina) {
        DisciplinaModel disciplinaSalva = disciplinaService.salvar(disciplina);
        return ResponseEntity.ok(disciplinaSalva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisciplinaModel> atualizar(@PathVariable Integer id, @RequestBody DisciplinaModel dados) {
        DisciplinaModel disciplinaAtualizada = disciplinaService.atualizar(id, dados);

        if (disciplinaAtualizada != null) {
            return ResponseEntity.ok(disciplinaAtualizada);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        disciplinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}