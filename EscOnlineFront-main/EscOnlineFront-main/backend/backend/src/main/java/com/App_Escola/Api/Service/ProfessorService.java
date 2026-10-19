package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.ProfessorModel;
import com.App_Escola.Api.Repository.ProfessorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    public ProfessorService(
            ProfessorRepository professorRepository
    ) {
        this.professorRepository = professorRepository;
    }

    public Page<ProfessorModel> listarTodos(
            Pageable pageable
    ) {
        return professorRepository.findAll(pageable);
    }

    public ProfessorModel buscarPorId(
            Integer id
    ) {
        return professorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Professor não encontrado"
                        )
                );
    }

    public ProfessorModel salvar(
            ProfessorModel professor
    ) {
        return professorRepository.save(professor);
    }

    public ProfessorModel atualizar(
            Integer id,
            ProfessorModel professorAtualizado
    ) {

        ProfessorModel professor =
                buscarPorId(id);

        professor.setNome(
                professorAtualizado.getNome()
        );

        professor.setCpf(
                professorAtualizado.getCpf()
        );

        professor.setEmail(
                professorAtualizado.getEmail()
        );

        professor.setTelefone(
                professorAtualizado.getTelefone()
        );

        return professorRepository.save(professor);
    }

    public void deletar(Integer id) {

        ProfessorModel professor =
                buscarPorId(id);

        professorRepository.delete(professor);
    }
}