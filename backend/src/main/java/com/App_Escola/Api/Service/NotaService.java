package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.AlunoModel;
import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.NotaModel;

import com.App_Escola.Api.Repository.AlunoRepository;
import com.App_Escola.Api.Repository.DisciplinaRepository;
import com.App_Escola.Api.Repository.NotaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotaService {

    private final NotaRepository notaRepository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public NotaService(
            NotaRepository notaRepository,
            AlunoRepository alunoRepository,
            DisciplinaRepository disciplinaRepository
    ) {
        this.notaRepository = notaRepository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<NotaModel> listarTodos() {
        return notaRepository.findAll();
    }

    public List<NotaModel> buscarPorAluno(
            Integer matricula
    ) {
        return notaRepository
                .findByAluno_Matricula(matricula);
    }

    public NotaModel buscarPorId(Integer id) {

        return notaRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Nota não encontrada")
                );
    }

    public NotaModel salvar(NotaModel nota) {

        Integer matricula =
                nota.getAluno().getMatricula();

        Integer idDisciplina =
                nota.getDisciplina().getIdDisciplina();

        AlunoModel aluno =
                alunoRepository.findById(matricula)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Aluno não encontrado"
                                )
                        );

        DisciplinaModel disciplina =
                disciplinaRepository
                        .findById(idDisciplina)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Disciplina não encontrada"
                                )
                        );

        nota.setAluno(aluno);
        nota.setDisciplina(disciplina);

        return notaRepository.save(nota);
    }

    public void deletar(Integer id) {

        NotaModel nota =
                buscarPorId(id);

        notaRepository.delete(nota);
    }
}