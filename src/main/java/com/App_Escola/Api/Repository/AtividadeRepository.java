package com.App_Escola.Api.Repository;

<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> db7e38465314b1cbda5e8124d6786d722562a7f5
import com.App_Escola.Api.Model.AtividadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface AtividadeRepository extends JpaRepository<AtividadeModel, Integer> {
}
=======
public class AtividadeRepository {

    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    public AtividadeModel save(AtividadeModel atividade) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public Optional<AtividadeModel> findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public List<AtividadeModel> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
>>>>>>> db7e38465314b1cbda5e8124d6786d722562a7f5
