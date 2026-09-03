package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.AlunoResponsavelModel;
import com.App_Escola.Api.Service.AlunoResponsavelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos-responsaveis")
@CrossOrigin(origins = "*")
public class AlunoResponsavelController {

    private final AlunoResponsavelService service;

    public AlunoResponsavelController(AlunoResponsavelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AlunoResponsavelModel>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponsavelModel> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlunoResponsavelModel> cadastrar(
            @RequestBody AlunoResponsavelModel relacionamento) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(relacionamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}