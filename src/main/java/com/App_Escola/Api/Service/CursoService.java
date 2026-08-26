package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.CursoModel;
import com.App_Escola.Api.Repository.CursoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    public List<CursoModel> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<CursoModel> buscarPorId(Integer id) {
        return cursoRepository.findById(id);
    }

    public CursoModel salvar(CursoModel curso) {
        return cursoRepository.save(curso);
    }

    public void deletar(Integer id) {
        cursoRepository.deleteById(id);
    }
}