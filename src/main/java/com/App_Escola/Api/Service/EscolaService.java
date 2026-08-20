package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.EscolaModel;
import com.App_Escola.Api.Repository.EscolaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EscolaService {

    private final EscolaRepository escolaRepository;

    public List<EscolaModel> listarTodas() {
        return escolaRepository.findAll();
    }

    public Optional<EscolaModel> buscarPorInep(Integer inep) {
        return escolaRepository.findById(inep);
    }

    public EscolaModel salvar(EscolaModel escola) {
        return escolaRepository.save(escola);
    }
}