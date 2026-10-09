package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.Atestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtestadoRepository extends JpaRepository<Atestado, Long> {
	List<Atestado> findByMatriculaOrderByIdDesc(Integer matricula);

	List<Atestado> findByProfessorIdOrderByIdDesc(Long professorId);
}