package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Model.ProfessorTurmaDisciplinaModel;
import com.App_Escola.Api.Model.TurmaModel;

import com.App_Escola.Api.Repository.DisciplinaRepository;
import com.App_Escola.Api.Repository.ProfessorRepository;
import com.App_Escola.Api.Repository.ProfessorTurmaDisciplinaRepository;
import com.App_Escola.Api.Repository.TurmaRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessorTurmaDisciplinaService {

    private final ProfessorTurmaDisciplinaRepository relacionamentoRepository;
    private final ProfessorRepository professorRepository;
    private final TurmaRepository turmaRepository;
    private final DisciplinaRepository disciplinaRepository;

    public ProfessorTurmaDisciplinaService(
            ProfessorTurmaDisciplinaRepository relacionamentoRepository,
            ProfessorRepository professorRepository,
            TurmaRepository turmaRepository,
            DisciplinaRepository disciplinaRepository
    ) {

        this.relacionamentoRepository = relacionamentoRepository;
        this.professorRepository = professorRepository;
        this.turmaRepository = turmaRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public List<ProfessorTurmaDisciplinaModel> listarTodos() {

        return relacionamentoRepository.findAll();
    }

    public ProfessorTurmaDisciplinaModel buscarPorId(
            Integer id
    ) {

        return relacionamentoRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Relação entre professor, turma e disciplina não encontrada"
                        )
                );
    }

    public ProfessorTurmaDisciplinaModel salvar(
            ProfessorTurmaDisciplinaModel relacionamento
    ) {

        if (relacionamento.getProfessor() == null ||
                relacionamento.getProfessor().getIdProfessor() == null) {

            throw new RuntimeException(
                    "O professor é obrigatório"
            );
        }

        if (relacionamento.getTurma() == null ||
                relacionamento.getTurma().getIdTurma() == null) {

            throw new RuntimeException(
                    "A turma é obrigatória"
            );
        }

        if (relacionamento.getDisciplina() == null ||
                relacionamento.getDisciplina().getIdDisciplina() == null) {

            throw new RuntimeException(
                    "A disciplina é obrigatória"
            );
        }

        Integer idProfessor =
                relacionamento.getProfessor().getIdProfessor();

        Integer idTurma =
                relacionamento.getTurma().getIdTurma();

        Integer idDisciplina =
                relacionamento.getDisciplina().getIdDisciplina();

        ProfessorModel professor =
                professorRepository.findById(idProfessor)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Professor não encontrado"
                                )
                        );

        TurmaModel turma =
                turmaRepository.findById(idTurma)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Turma não encontrada"
                                )
                        );

        DisciplinaModel disciplina =
                disciplinaRepository.findById(idDisciplina)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Disciplina não encontrada"
                                )
                        );

        boolean jaExiste =
                relacionamentoRepository
                        .existsByProfessor_IdProfessorAndTurma_IdTurmaAndDisciplina_IdDisciplina(
                                idProfessor,
                                idTurma,
                                idDisciplina
                        );

        if (jaExiste) {

            throw new RuntimeException(
                    "Este professor já está vinculado a esta disciplina nesta turma"
            );
        }

        relacionamento.setProfessor(professor);
        relacionamento.setTurma(turma);
        relacionamento.setDisciplina(disciplina);

        return relacionamentoRepository.save(relacionamento);
    }

    public ProfessorTurmaDisciplinaModel atualizar(
            Integer id,
            ProfessorTurmaDisciplinaModel relacionamentoAtualizado
    ) {

        ProfessorTurmaDisciplinaModel existente =
                buscarPorId(id);

        if (relacionamentoAtualizado.getProfessor() == null ||
                relacionamentoAtualizado.getProfessor().getIdProfessor() == null) {

            throw new RuntimeException(
                    "O professor é obrigatório"
            );
        }

        if (relacionamentoAtualizado.getTurma() == null ||
                relacionamentoAtualizado.getTurma().getIdTurma() == null) {

            throw new RuntimeException(
                    "A turma é obrigatória"
            );
        }

        if (relacionamentoAtualizado.getDisciplina() == null ||
                relacionamentoAtualizado.getDisciplina().getIdDisciplina() == null) {

            throw new RuntimeException(
                    "A disciplina é obrigatória"
            );
        }

        ProfessorModel professor =
                professorRepository.findById(
                                relacionamentoAtualizado
                                        .getProfessor()
                                        .getIdProfessor()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Professor não encontrado"
                                )
                        );

        TurmaModel turma =
                turmaRepository.findById(
                                relacionamentoAtualizado
                                        .getTurma()
                                        .getIdTurma()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Turma não encontrada"
                                )
                        );

        DisciplinaModel disciplina =
                disciplinaRepository.findById(
                                relacionamentoAtualizado
                                        .getDisciplina()
                                        .getIdDisciplina()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Disciplina não encontrada"
                                )
                        );

        existente.setProfessor(professor);
        existente.setTurma(turma);
        existente.setDisciplina(disciplina);

        return relacionamentoRepository.save(existente);
    }

    public void deletar(Integer id) {

        ProfessorTurmaDisciplinaModel relacionamento =
                buscarPorId(id);

        relacionamentoRepository.delete(relacionamento);
    }

    public List<ProfessorTurmaDisciplinaModel> buscarPorProfessor(
            Integer idProfessor
    ) {

        return relacionamentoRepository
                .findByProfessor_IdProfessor(idProfessor);
    }

    public List<ProfessorTurmaDisciplinaModel> buscarPorTurma(
            Integer idTurma
    ) {

        return relacionamentoRepository
                .findByTurma_IdTurma(idTurma);
    }

    public List<ProfessorTurmaDisciplinaModel> buscarPorDisciplina(
            Integer idDisciplina
    ) {

        return relacionamentoRepository
                .findByDisciplina_IdDisciplina(idDisciplina);
    }
}