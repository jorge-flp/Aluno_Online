package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoResponsavelModel;
import com.App_Escola.Api.Repository.AlunoResponsavelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlunoResponsavelService {

    @Autowired
    private AlunoResponsavelRepository repository;

    public List<AlunoResponsavelModel> listar() {
        return repository.findAll();
    }

    public Optional<AlunoResponsavelModel> buscarPorId(Integer id) {
        return repository.findById(id);
    }

    public AlunoResponsavelModel salvar(AlunoResponsavelModel alunoResponsavel) {
        return repository.save(alunoResponsavel);
    }

    public AlunoResponsavelModel atualizar(Integer id, AlunoResponsavelModel dados) {
    Optional<AlunoResponsavelModel> alunoResponsavelOpt = repository.findById(id);

    if (alunoResponsavelOpt.isPresent()) {
        AlunoResponsavelModel arExistente = alunoResponsavelOpt.get();
        arExistente.setParentesco(dados.getParentesco());
        arExistente.setResponsavel_principal(dados.getResponsavel_principal());
        arExistente.setAluno(dados.getAluno());
        arExistente.setResponsavel(dados.getResponsavel());

        return repository.save(arExistente);
    }

    return null;
}
    public boolean deletar(Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}