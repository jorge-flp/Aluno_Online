package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.CoordenadorModel;
import com.App_Escola.Api.Repository.CoordenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoordenadorService {

    private final CoordenadorRepository repository;

    public CoordenadorService(CoordenadorRepository repository) {
        this.repository = repository;
    }

    public List<CoordenadorModel> listarTodos() {
        return repository.findAll();
    }

    public CoordenadorModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coordenador não encontrado"));
    }

    public CoordenadorModel salvar(CoordenadorModel coordenador) {
        return repository.save(coordenador);
    }

    public CoordenadorModel atualizar(Integer id, CoordenadorModel coordenador) {
        CoordenadorModel existente = buscarPorId(id);

        existente.setNome(coordenador.getNome());
        existente.setEmail(coordenador.getEmail());
        existente.setTelefone(coordenador.getTelefone());

        return repository.save(existente);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Coordenador não encontrado");
        }

        repository.deleteById(id);
    }
}