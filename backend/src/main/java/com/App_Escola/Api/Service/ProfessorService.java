package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Repository.ProfessorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public List<ProfessorModel> listarTodos() {
        return professorRepository.findAll();
    }

    public Optional<ProfessorModel> buscarPorMatricula(Integer matricula) {
        return professorRepository.findById(matricula);
    }

    public ProfessorModel salvar(ProfessorModel professor) {
        // Validar cpf ou regras do professor no futuro
        return professorRepository.save(professor);
    }

    public void deletar(Integer matricula) {
        professorRepository.deleteById(matricula);
    }

}
