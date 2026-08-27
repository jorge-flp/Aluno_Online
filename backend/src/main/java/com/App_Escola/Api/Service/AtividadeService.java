package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.App_Escola.Api.Model.AtividadeModel;
import com.App_Escola.Api.Repository.AtividadeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class AtividadeService {

    private AtividadeRepository atividadeRepository;

    public List<AtividadeModel> listarTodas() {
        return atividadeRepository.findAll();
    }

    public Optional<AtividadeModel> buscarPorId(Integer id) {
        return atividadeRepository.findById(id);
    }

    public AtividadeModel salvar(AtividadeModel atividade) {
        return atividadeRepository.save(atividade);
    }

    public void deletar(Integer id) {
        atividadeRepository.deleteById(id);
    }
}