package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.TurmaCursoModel;
import com.App_Escola.Api.Repository.TurmaCursoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TurmaCursoService {

    private final TurmaCursoRepository turmaCursoRepository;

    public List<TurmaCursoModel> listarTodos() {
        return turmaCursoRepository.findAll();
    }

    public Optional<TurmaCursoModel> buscarPorId(Integer id) {
        return turmaCursoRepository.findById(id);
    }

    public TurmaCursoModel salvar(TurmaCursoModel turmaCurso) {
        return turmaCursoRepository.save(turmaCurso);
    }

    public void deletar(Integer id) {
        turmaCursoRepository.deleteById(id);
    }
}