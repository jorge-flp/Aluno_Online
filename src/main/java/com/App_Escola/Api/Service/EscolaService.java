package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.EscolaModel;
import com.App_Escola.Api.Repository.EscolaRepository;

@Service
public class EscolaService {

    @Autowired
    private EscolaRepository escolaRepository;

    public List<EscolaModel> ListarEscolas() {
        return escolaRepository.findAll();
    }

    public Optional<EscolaModel> BuscarEscolaPorInep(String inep) {
        return escolaRepository.findById(inep);
    }

    public EscolaModel SalvarEscola(EscolaModel escola) {
        return escolaRepository.save(escola);
    }

    public void DeletarEscola(String inep) {
        escolaRepository.deleteById(inep);
    }
}
