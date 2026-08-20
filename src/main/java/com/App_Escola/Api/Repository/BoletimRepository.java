package com.App_Escola.Api.Repository;

<<<<<<< HEAD
=======
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
>>>>>>> db7e38465314b1cbda5e8124d6786d722562a7f5
import com.App_Escola.Api.Model.BoletimModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
<<<<<<< HEAD
public interface BoletimRepository extends JpaRepository<BoletimModel, Integer> {
}
=======
public class BoletimRepository {

    public List<BoletimModel> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public Optional<BoletimModel> findById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    public BoletimModel save(BoletimModel boletim) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

}
>>>>>>> db7e38465314b1cbda5e8124d6786d722562a7f5
