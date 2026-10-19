package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Repository.DisciplinaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaService(
            DisciplinaRepository disciplinaRepository
    ) {
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<DisciplinaModel> listarTodos() {
        return disciplinaRepository.findAll();
    }

    public DisciplinaModel buscarPorId(Integer id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Disciplina não encontrada")
                );
    }

    public DisciplinaModel salvar(
            DisciplinaModel disciplina
    ) {
        return disciplinaRepository.save(disciplina);
    }

    public DisciplinaModel atualizar(
            Integer id,
            DisciplinaModel disciplina
    ) {

        DisciplinaModel existente =
                buscarPorId(id);

        existente.setNome(
                disciplina.getNome()
        );

        return disciplinaRepository.save(existente);
    }

    public void deletar(Integer id) {

        DisciplinaModel disciplina =
                buscarPorId(id);

        disciplinaRepository.delete(disciplina);
    }
}