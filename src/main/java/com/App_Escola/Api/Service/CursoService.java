package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.stereotype.Service;

import com.App_Escola.Api.Model.CursoModel;
import com.App_Escola.Api.Repository.CursoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@AllowNonPortable
public class CursoService {

    private CursoRepository cursoRepository;

    public List<CursoRepository> listarTodas() {
        return cursoRepository.findAll();
    }

    public Optional<CursoRepository> buscarPorId(Integer id) {
        return cursoRepository.findById(id);
    }

    public CursoModel salvar(CursoModel curso) {
        return cursoRepository.save(curso);
    }

    public CursoRepository atualizar(Integer id, CursoModel dados) {
        Optional<CursoRepository> cursoOpt = cursoRepository.findById(id);

        if (cursoOpt.isPresent()) {
            CursoRepository cursoExistente = cursoOpt.get();
            cursoExistente.setIdCur(dados.getId_curso());
            cursoExistente.setDescri(dados.getDescricao());
            cursoExistente.setCargaHor(dados.getCarga_horaria());

            return cursoRepository.save(cursoExistente);
        }

        return null;
    }

    public void deletar(Integer id) {
        cursoRepository.deleteById(id);
    }
}