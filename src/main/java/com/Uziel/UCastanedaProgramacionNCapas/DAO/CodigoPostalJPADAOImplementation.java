package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.ColoniaJPA;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Colonia;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Estado;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Municipio;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Pais;
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
public class CodigoPostalJPADAOImplementation implements ICodigoPostalJPA{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result CodigoPostalGetDatosJPA(String CodigoPostal) {
        Result result = new Result();
        
        try {
            TypedQuery<ColoniaJPA> queryCodigoPostal = entityManager.createQuery("FROM ColoniaJPA coloniaJPA WHERE coloniaJPA.CodigoPostal = :CodigoPostal", ColoniaJPA.class);
            queryCodigoPostal.setParameter("CodigoPostal", CodigoPostal);
            
            List<ColoniaJPA> coloniasJPA = queryCodigoPostal.getResultList();
            List<Colonia> colonias = new ArrayList<>();
            
            for (ColoniaJPA coloniaJPA : coloniasJPA) {
                
                Colonia colonia = modelMapper.map(coloniaJPA, Colonia.class);
                Municipio municipio = modelMapper.map(coloniaJPA.MunicipioJPA, Municipio.class);
                Estado estado = modelMapper.map(coloniaJPA.MunicipioJPA.EstadoJPA, Estado.class);
                Pais pais = modelMapper.map(coloniaJPA.MunicipioJPA.EstadoJPA.PaisJPA, Pais.class);
                estado.Pais = pais;
                municipio.Estado = estado;
                colonia.Municipio = municipio;
                colonias.add(colonia);
            }
            
            result.objects = (List<Object>)(List<?>) colonias;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    
    
}
