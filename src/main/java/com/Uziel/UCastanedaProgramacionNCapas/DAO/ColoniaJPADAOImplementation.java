package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.ColoniaJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaJPADAOImplementation implements IColoniaJPA{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetByIdMunicipioJPA(int IdMunicipio) {
        Result result = new Result();
        
        try {
            TypedQuery <ColoniaJPA> coloniasJPA = entityManager.createQuery(
                    "FROM ColoniaJPA coloniaJPA WHERE coloniaJPA.MunicipioJPA.IdMunicipio = :IdMunicipio", ColoniaJPA.class);
            coloniasJPA.setParameter("IdMunicipio", IdMunicipio);
            
            List<ColoniaJPA> colonias = coloniasJPA.getResultList();
            
            if (colonias == null || colonias.isEmpty()) {
                result.correct = false;
                result.errorMessage = "No existe el municipio con el Id " + IdMunicipio;
            } else {
                result.correct = true;
                result.object = colonias;
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        
        return result;
    }
    
    
    
}
