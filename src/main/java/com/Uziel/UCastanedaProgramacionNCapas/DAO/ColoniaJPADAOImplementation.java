package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.ColoniaJPA;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Colonia;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
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
            TypedQuery <ColoniaJPA> queryColonia = entityManager.createQuery("FROM ColoniaJPA coloniaJPA WHERE coloniaJPA.MunicipioJPA.IdMunicipio = :IdMunicipio", ColoniaJPA.class);
            queryColonia.setParameter("IdMunicipio", IdMunicipio);
            
            List<ColoniaJPA> coloniasJPA = queryColonia.getResultList();
            List<Colonia> colonias = new ArrayList<>();
            
            for (ColoniaJPA coloniaJPA : coloniasJPA) {
                Colonia colonia = modelMapper.map(coloniaJPA, Colonia.class);
                colonias.add(colonia);
            }
            
            result.objects = (List<Object>)(List<?>)colonias;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        
        return result;
    }
    
    
    
}
