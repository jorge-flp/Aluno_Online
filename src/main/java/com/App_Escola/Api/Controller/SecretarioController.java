package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.SecretarioModel;
import com.App_Escola.Api.Service.SecretarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/secretarios")
@CrossOrigin(origins = "*")
public class SecretarioController {

    private final SecretarioService service;

    public SecretarioController(SecretarioService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SecretarioModel>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SecretarioModel> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SecretarioModel> cadastrar(
            @RequestBody SecretarioModel secretario) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(secretario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecretarioModel> atualizar(
            @PathVariable Integer id,
            @RequestBody SecretarioModel secretario) {

        return ResponseEntity.ok(service.atualizar(id, secretario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}