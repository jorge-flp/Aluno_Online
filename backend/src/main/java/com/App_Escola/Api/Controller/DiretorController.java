package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.DiretorModel;
import com.App_Escola.Api.Service.DiretorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/diretores")
@CrossOrigin(origins = "*")
public class DiretorController {

    private final DiretorService service;

    public DiretorController(DiretorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<DiretorModel>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                service.listarTodos(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiretorModel> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DiretorModel> cadastrar(
            @Valid @RequestBody DiretorModel diretor) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(diretor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiretorModel> atualizar(
            @PathVariable Integer id,
            @Valid @RequestBody DiretorModel diretor) {

        return ResponseEntity.ok(service.atualizar(id, diretor));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}