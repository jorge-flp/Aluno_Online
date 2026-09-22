package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.NotaEscritaModel;
import com.App_Escola.Api.Repository.NotaEscritaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotaEscritaService {

    @Autowired
    private NotaEscritaRepository repository;

    public NotaEscritaModel criar(NotaEscritaModel nota) {
        return repository.save(nota);
    }

    public List<NotaEscritaModel> listarTodas() {
        return repository.findAll();
    }

    public Optional<NotaEscritaModel> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<NotaEscritaModel> listarPorAluno(Long matricula) {
        return repository.findByAlunoMatricula(matricula);
    }

    public List<NotaEscritaModel> listarPorProfessor(Long professorId) {
        return repository.findByProfessorId(professorId);
    }

    public Optional<NotaEscritaModel> atualizar(
            Long id,
            NotaEscritaModel dados) {

        return repository.findById(id)
                .map(nota -> {
                    nota.setFeedback(dados.getFeedback());
                    nota.setAluno(dados.getAluno());
                    nota.setProfessorId(dados.getProfessorId());

                    return repository.save(nota);
                });
    }

    public boolean excluir(Long id) {

        if (!repository.existsById(id)) {
            return false;
        }

        repository.deleteById(id);
        return true;
    }
}