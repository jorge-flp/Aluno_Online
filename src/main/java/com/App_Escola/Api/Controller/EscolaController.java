package com.App_Escola.Api.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.App_Escola.Api.Model.EscolaModel;
import com.App_Escola.Api.Repository.EscolaRepository;

@RestController
@RequestMapping("/escola")
public class EscolaController {

    private final EscolaRepository escolaRepository;

    EscolaController(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }

    @GetMapping
    public List<EscolaModel> getEscolas() {
        return escolaRepository.findAll();
    }

    @PostMapping
    public EscolaModel createEscola(@RequestBody EscolaModel escola) {
        return escolaRepository.save(escola);
    }
}