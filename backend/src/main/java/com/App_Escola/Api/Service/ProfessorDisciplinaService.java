package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.ProfessorDisciplinaModel;
import com.App_Escola.Api.Repository.ProfessorDisciplinaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfessorDisciplinaService {

    private final ProfessorDisciplinaRepository professorDisciplinaRepository;

    public List<ProfessorDisciplinaModel> listarTodos() {
        return professorDisciplinaRepository.findAll();
    }

    public Optional<ProfessorDisciplinaModel> buscarPorId(Integer id) {
        return professorDisciplinaRepository.findById(id);
    }

    public ProfessorDisciplinaModel salvar(ProfessorDisciplinaModel professorDisciplina) {
        return professorDisciplinaRepository.save(professorDisciplina);
    }

    public void deletar(Integer id) {
        professorDisciplinaRepository.deleteById(id);
    }
}