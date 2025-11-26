package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.MunicipioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioJPADAOImplementation implements IMunicipioJPA{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public Result GetByIdEstadoJPA(int IdEstado){
        Result result = new Result();
        
        try {
            TypedQuery <MunicipioJPA> municipiosJPA = entityManager.createQuery(
                    "FROM MunicipioJPA municipioJPA WHERE municipioJPA.EstadoJPA.IdEstado = :IdEstado", MunicipioJPA.class);
            municipiosJPA.setParameter("IdEstado", IdEstado);
            
            List<MunicipioJPA> municipios = municipiosJPA.getResultList();
            
            if (municipios == null || municipios.isEmpty()) {
                result.correct = false;
                result.errorMessage = "No existe el estado con el Id = " + IdEstado;
            } else {
                result.correct = true;
                result.objects = (List<Object>)(List<?>) municipios;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
