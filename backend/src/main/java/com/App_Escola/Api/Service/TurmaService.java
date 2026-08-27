package com.App_Escola.Api.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.hibernate.internal.build.AllowNonPortable;
import org.springframework.beans.factory.annotation.Autowired;

import com.App_Escola.Api.Repository.TurmaRepository;

import lombok.AllArgsConstructor;

import com.App_Escola.Api.Model.TurmaModel;

@Service
@AllArgsConstructor
@AllowNonPortable
public class TurmaService {

    private TurmaRepository turmaRepository;

    public List<TurmaModel> ListarTurmas() {
        return turmaRepository.findAll();
    }

    public Optional<TurmaModel> BuscarTurmaPorId(Integer id) {
        return turmaRepository.findById(id);
    }

    public TurmaModel SalvarTurma(TurmaModel turma) {
        return turmaRepository.save(turma);
    }

    public void DeletarTurma(Integer id) {
        turmaRepository.deleteById(id);
    }

}
