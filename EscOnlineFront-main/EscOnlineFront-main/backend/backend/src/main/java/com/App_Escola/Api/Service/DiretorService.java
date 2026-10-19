package com.App_Escola.Api.Service;

import com.App_Escola.Api.Model.DiretorModel;
import com.App_Escola.Api.Repository.DiretorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DiretorService {

    private final DiretorRepository repository;

    public DiretorService(DiretorRepository repository) {
        this.repository = repository;
    }

    public Page<DiretorModel> listarTodos(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public DiretorModel buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Diretor não encontrado"));
    }

    public DiretorModel salvar(DiretorModel diretor) {
        return repository.save(diretor);
    }

    public DiretorModel atualizar(Integer id, DiretorModel diretorAtualizado) {
        DiretorModel diretorExistente = buscarPorId(id);

        diretorExistente.setNome(diretorAtualizado.getNome());
        diretorExistente.setEmail(diretorAtualizado.getEmail());
        diretorExistente.setTelefone(diretorAtualizado.getTelefone());

        return repository.save(diretorExistente);
    }

    public void deletar(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Diretor não encontrado");
        }

        repository.deleteById(id);
    }
}