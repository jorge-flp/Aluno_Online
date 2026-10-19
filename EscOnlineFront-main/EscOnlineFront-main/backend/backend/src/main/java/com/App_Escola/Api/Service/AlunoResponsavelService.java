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
        Integer matricula = relacionamento.getAluno().getMatricula();
        Integer idResponsavel = relacionamento.getResponsavel().getIdResponsavel();

        if (repository.findByAluno_MatriculaAndResponsavel_IdResponsavel(matricula, idResponsavel).isPresent()) {
            throw new RuntimeException("Aluno já está relacionado a este responsável");
        }

        return repository.save(relacionamento);
    }

    public AlunoResponsavelModel atualizar(Integer id, AlunoResponsavelModel relacionamento) {
        AlunoResponsavelModel existente = buscarPorId(id);
        existente.setParentesco(relacionamento.getParentesco());
        return repository.save(existente);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Relacionamento não encontrado");
        }
        repository.deleteById(id);
    }
}