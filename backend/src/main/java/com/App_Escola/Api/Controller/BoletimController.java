package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.BoletimConceitoModel;
import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Model.BoletimNotasModel;
import com.App_Escola.Api.Service.BoletimService;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/aluno/{matricula}/notas")
    public ResponseEntity<BoletimNotasModel> notas(
            @PathVariable Integer matricula
    ) {

        return ResponseEntity.ok(
                boletimService.buscarNotas(matricula)
        );
    }

    @GetMapping("/aluno/{matricula}/conceito")
    public ResponseEntity<BoletimConceitoModel> conceito(
            @PathVariable Integer matricula
    ) {

        return ResponseEntity.ok(
                boletimService.buscarConceito(matricula)
        );
    }
}