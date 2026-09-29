package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.BoletimConceitoModel;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Model.BoletimNotasModel;

import com.App_Escola.Api.Service.BoletimService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boletins")
@CrossOrigin(origins = "*")
public class BoletimController {

    private final BoletimService boletimService;

    public BoletimController(
            BoletimService boletimService
    ) {
        this.boletimService = boletimService;
    }

    // =========================================================
    // BOLETIM DETALHADO
    // =========================================================

    @GetMapping("/aluno/{matricula}/notas")
    public ResponseEntity<BoletimNotasModel> buscarNotas(
            @PathVariable Integer matricula
    ) {

        return ResponseEntity.ok(
                boletimService.buscarNotas(
                        matricula
                )
        );
    }

    // =========================================================
    // BOLETIM POR CONCEITO
    // =========================================================

    @GetMapping("/aluno/{matricula}/conceito")
    public ResponseEntity<BoletimConceitoModel> buscarConceito(
            @PathVariable Integer matricula
    ) {

        return ResponseEntity.ok(
                boletimService.buscarConceito(
                        matricula
                )
        );
    }

    // =========================================================
    // GERA E SALVA BOLETIM
    // =========================================================

    @PostMapping("/aluno/{matricula}/gerar")
    public ResponseEntity<BoletimModel> gerarBoletim(
            @PathVariable Integer matricula,
            @RequestParam Integer anoLetivo
    ) {

        return ResponseEntity.ok(
                boletimService.gerarBoletim(
                        matricula,
                        anoLetivo
                )
        );
    }

    // =========================================================
    // HISTÓRICO DO ALUNO
    // =========================================================

    @GetMapping("/aluno/{matricula}/historico")
    public ResponseEntity<List<BoletimModel>>
    listarHistorico(
            @PathVariable Integer matricula
    ) {

        return ResponseEntity.ok(
                boletimService
                        .listarBoletinsDoAluno(
                                matricula
                        )
        );
    }

    // =========================================================
    // BUSCA POR ANO
    // =========================================================

    @GetMapping(
            "/aluno/{matricula}/ano/{anoLetivo}"
    )
    public ResponseEntity<BoletimModel> buscarPorAno(
            @PathVariable Integer matricula,
            @PathVariable Integer anoLetivo
    ) {

        return ResponseEntity.ok(
                boletimService.buscarBoletim(
                        matricula,
                        anoLetivo
                )
        );
    }

    // =========================================================
    // DELETE
    // =========================================================

    @DeleteMapping("/{idBoletim}")
    public ResponseEntity<Void> deletar(
            @PathVariable Integer idBoletim
    ) {

        boletimService.deletarBoletim(
                idBoletim
        );

        return ResponseEntity
                .noContent()
                .build();
    }
}