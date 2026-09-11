package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.TurmaModel;
import com.App_Escola.Api.Repository.TurmaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TurmaService {

    private final TurmaRepository turmaRepository;

    public TurmaService(TurmaRepository turmaRepository) {
        this.turmaRepository = turmaRepository;
    }

    public Page<TurmaModel> listarTodos(Pageable pageable) {
        return turmaRepository.findAll(pageable);
    }

    public TurmaModel buscarPorId(Integer id) {
        return turmaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Turma não encontrada"));
    }

    public TurmaModel salvar(TurmaModel turma) {
        return turmaRepository.save(turma);
    }

    public TurmaModel atualizar(Integer id, TurmaModel turma) {

        TurmaModel turmaExistente = turmaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Turma não encontrada"));

        turmaExistente.setAnoSerie(turma.getAnoSerie());
        turmaExistente.setNome(turma.getNome());

        return turmaRepository.save(turmaExistente);
    }

    public void deletar(Integer id) {

        if (!turmaRepository.existsById(id)) {
            throw new RuntimeException("Turma não encontrada");
        }

        turmaRepository.deleteById(id);
    }
}