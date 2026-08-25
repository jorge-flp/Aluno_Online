package com.App_Escola.Api.Repository;

import com.App_Escola.Api.Model.CursoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<CursoRepository, Integer> {

    CursoModel save(CursoModel curso);

    void setDisciplina(Object disciplina);

    void setTipo(Object tipo);

    void setCargaHor(Object cargaHor);

    void setIdCur(Integer id_curso);

    void setDescri(String descricao);
}