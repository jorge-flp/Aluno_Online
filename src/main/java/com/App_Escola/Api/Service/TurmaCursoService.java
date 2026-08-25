package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.TurmaCursoModel;
import com.App_Escola.Api.Repository.TurmaCursoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class TurmaCursoService {

    private TurmaCursoRepository turmaCursoRepository;

    public List<TurmaCursoRepository> listarTodas() {
        return turmaCursoRepository.findAll();
    }

    public Optional<TurmaCursoRepository> buscarPorId(Integer id) {
        return turmaCursoRepository.findById(id);
    }

    public TurmaCursoModel salvar(TurmaCursoModel turmaCurso) {
        return turmaCursoRepository.save(turmaCurso);
    }

    public TurmaCursoModel atualizar(Integer id, TurmaCursoModel dados) {
        Optional<TurmaCursoRepository> turmaCursoOpt = turmaCursoRepository.findById(id);

        if (turmaCursoOpt.isPresent()) {
            TurmaCursoModel tcExistente = (TurmaCursoModel) turmaCursoOpt.get();
            tcExistente.setData_inicio(dados.getData_inicio());
            tcExistente.setData_fim(dados.getData_fim());
            tcExistente.setTurma(dados.getTurma());
            tcExistente.setCurso(dados.getCurso());

            return turmaCursoRepository.save(tcExistente);
        }

        return null;
    }

    public void deletar(Integer id) {
        turmaCursoRepository.deleteById(id);
    }
}