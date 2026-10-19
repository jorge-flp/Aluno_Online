package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Service.TurmaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/turmas")
@CrossOrigin(origins = "*")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping
    public ResponseEntity<Page<TurmaModel>> listar(
            @PageableDefault(size = 10) Pageable pageable) {

        return ResponseEntity.ok(
                turmaService.listarTodos(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TurmaModel> buscar(
            @PathVariable Integer id) {

        return ResponseEntity.ok(
                turmaService.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<TurmaModel> cadastrar(
            @RequestBody TurmaModel turma) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(turmaService.salvar(turma));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurmaModel> atualizar(
            @PathVariable Integer id,
            @RequestBody TurmaModel turma) {

        return ResponseEntity.ok(
                turmaService.atualizar(id, turma)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Integer id) {

        turmaService.deletar(id);

        return ResponseEntity.noContent().build();
    }
}