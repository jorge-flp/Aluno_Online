package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.ProfessorDisciplinaModel;
import com.App_Escola.Api.Repository.ProfessorDisciplinaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class ProfessorDisciplinaService {

    private ProfessorDisciplinaRepository professorDisciplinaRepository;

    public List<ProfessorDisciplinaRepository> listarTodas() {
        return professorDisciplinaRepository.findAll();
    }

    public Optional<ProfessorDisciplinaRepository> buscarPorId(Integer id) {
        return professorDisciplinaRepository.findById(id);
    }

    public ProfessorDisciplinaModel salvar(ProfessorDisciplinaModel professorDisciplina) {
        return professorDisciplinaRepository.save(professorDisciplina);
    }

    public ProfessorDisciplinaModel atualizar(Integer id, ProfessorDisciplinaModel dados) {
        Optional<ProfessorDisciplinaRepository> professorDisciplinaOpt = professorDisciplinaRepository.findById(id);

        if (professorDisciplinaOpt.isPresent()) {
            ProfessorDisciplinaModel pdExistente = (ProfessorDisciplinaModel) professorDisciplinaOpt.get();
            pdExistente.setData_inicio(dados.getData_inicio());
            pdExistente.setData_fim(dados.getData_fim());
            pdExistente.setProfessor(dados.getProfessor());
            pdExistente.setDisciplina(dados.getDisciplina());

            return professorDisciplinaRepository.save(pdExistente);
        }

        return null;
    }

    public void deletar(Integer id) {
        professorDisciplinaRepository.deleteById(id);
    }
}