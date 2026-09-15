package com.App_Escola.Api.Controller;

import com.App_Escola.Api.Model.BoletimModel;
import com.App_Escola.Api.Service.BoletimService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boletins")
@CrossOrigin(origins = "*")
public class BoletimController {

    private final BoletimService boletimService;

    public BoletimController(BoletimService boletimService) {
        this.boletimService = boletimService;
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<BoletimModel> buscarBoletim(
            @PathVariable Integer matricula) {

        return ResponseEntity.ok(
                boletimService.buscarBoletimDoAluno(matricula)
        );
    }
}