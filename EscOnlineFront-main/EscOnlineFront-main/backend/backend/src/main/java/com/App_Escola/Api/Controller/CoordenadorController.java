package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.CoordenadorModel;
import com.App_Escola.Api.Service.CoordenadorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordenadores")
@CrossOrigin(origins = "*")
public class CoordenadorController {

    private final CoordenadorService service;

    public CoordenadorController(CoordenadorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<CoordenadorModel>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                service.listarTodos(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoordenadorModel> buscar(@PathVariable Integer id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<CoordenadorModel> cadastrar(
            @RequestBody CoordenadorModel coordenador) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.salvar(coordenador));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoordenadorModel> atualizar(
            @PathVariable Integer id,
            @RequestBody CoordenadorModel coordenador) {

        return ResponseEntity.ok(service.atualizar(id, coordenador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}