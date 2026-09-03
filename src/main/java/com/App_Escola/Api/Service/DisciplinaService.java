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

    private DisciplinaRepository disciplinaRepository;

    public List<DisciplinaModel> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public Optional<DisciplinaModel> buscarPorId(Integer id) {
        return disciplinaRepository.findById(id);
    }

    public DisciplinaModel salvar(DisciplinaModel disciplina) {
        return disciplinaRepository.save(disciplina);
    }

    public DisciplinaModel atualizar(Integer id, DisciplinaModel dados) {
        Optional<DisciplinaModel> disciplinaOpt = disciplinaRepository.findById(id);

        if (disciplinaOpt.isPresent()) {
            DisciplinaModel disciplinaExistente = disciplinaOpt.get();
            disciplinaExistente.setNome(dados.getNome());
            disciplinaExistente.setDescricao(dados.getDescricao());
            disciplinaExistente.setCargaHoraria(dados.getCargaHoraria());

            return disciplinaRepository.save(disciplinaExistente);
        }

        return null;
    }

    public void deletar(Integer id) {
        disciplinaRepository.deleteById(id);
    }
}