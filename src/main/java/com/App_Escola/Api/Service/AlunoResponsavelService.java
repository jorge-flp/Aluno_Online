package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoResponsavelModel;
import com.App_Escola.Api.Repository.AlunoResponsavelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoResponsavelService {

    private final AlunoResponsavelRepository repository;

    public AlunoResponsavelService(AlunoResponsavelRepository repository) {
        this.repository = repository;
    }

    public List<AlunoResponsavelModel> listarTodos() {
        return repository.findAll();
    }

    public AlunoResponsavelModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relacionamento não encontrado"));
    }

    public AlunoResponsavelModel salvar(AlunoResponsavelModel relacionamento) {
        return repository.save(relacionamento);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Relacionamento não encontrado");
        }

        repository.deleteById(id);
    }
}