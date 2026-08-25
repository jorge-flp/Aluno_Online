package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.DisciplinaModel;
import com.App_Escola.Api.Repository.DisciplinaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class DisciplinaService {

    private final DisciplinaRepository disciplinaRepository;

    public List<DisciplinaModel> listarTodos() {
        return disciplinaRepository.findAll();
    }

    public DisciplinaModel salvar(DisciplinaModel disciplina) {
        
        return disciplinaRepository.save(disciplina);
    }

    public DisciplinaModel atualizar(DisciplinaModel disciplina) {
        
        return disciplinaRepository.save(disciplina);
    }

    public void deletar(Integer id) {
        disciplinaRepository.deleteById(id);
    }

    public Optional<DisciplinaModel> buscarPorId(Integer id) {
        
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }

}
