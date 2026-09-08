package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Repository.AlunoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Page<AlunoModel> listarTodos(Pageable pageable) {
        return alunoRepository.findAll(pageable);
    }

    public AlunoModel buscarPorMatricula(Integer matricula) {
        return alunoRepository.findById(matricula)
                .orElseThrow(() ->
                        new RuntimeException("Aluno não encontrado"));
    }

    public AlunoModel salvar(AlunoModel aluno) {
        return alunoRepository.save(aluno);
    }

    public void deletar(Integer matricula) {
        if (!alunoRepository.existsById(matricula)) {
            throw new RuntimeException("Aluno não encontrado");
        }

        alunoRepository.deleteById(matricula);
    }
}