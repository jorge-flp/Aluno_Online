package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.ResponsavelModel;
import com.App_Escola.Api.Service.ResponsavelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/responsaveis")
@CrossOrigin(origins = "*")
public class ResponsavelController {

    private final ResponsavelService service;

    public ResponsavelController(ResponsavelService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResponsavelModel>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsavelModel> buscar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResponsavelModel> cadastrar(
            @RequestBody ResponsavelModel responsavel) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(responsavel));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponsavelModel> atualizar(
            @PathVariable Integer id,
            @RequestBody ResponsavelModel responsavel) {

        return ResponseEntity.ok(
                service.atualizar(id, responsavel)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {

        service.deletar(id);

        return ResponseEntity.noContent().build();
    }
}