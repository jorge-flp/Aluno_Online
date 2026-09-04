package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.ResponsavelModel;
import com.App_Escola.Api.Repository.ResponsavelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsavelService {

    private final ResponsavelRepository repository;

    public ResponsavelService(ResponsavelRepository repository) {
        this.repository = repository;
    }

    public List<ResponsavelModel> listarTodos() {
        return repository.findAll();
    }

    public ResponsavelModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Responsável não encontrado"));
    }

    public ResponsavelModel salvar(ResponsavelModel responsavel) {
        return repository.save(responsavel);
    }

    public ResponsavelModel atualizar(
            Integer id,
            ResponsavelModel responsavel) {

        ResponsavelModel existente = buscarPorId(id);

        existente.setNome(responsavel.getNome());
        existente.setCpf(responsavel.getCpf());
        existente.setEmail(responsavel.getEmail());
        existente.setTelefone(responsavel.getTelefone());

        return repository.save(existente);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Responsável não encontrado");
        }

        repository.deleteById(id);
    }
}