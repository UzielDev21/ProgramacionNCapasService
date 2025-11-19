package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.MunicipioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Municipio;
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
public class MunicipioJPADAOImplementation implements IMunicipio{

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetByIdEstadoJPA(int IdEstado){
        Result result = new Result();
        
        try {
            TypedQuery <MunicipioJPA> queryMunicipio = entityManager.createQuery("FROM MunicipioJPA municipioJPA WHERE municipioJPA.EstadoJPA.IdEstado = :IdEstado", MunicipioJPA.class);
            queryMunicipio.setParameter("IdEstado", IdEstado);
            
            List<MunicipioJPA> municipiosJPA = queryMunicipio.getResultList();
            List<Municipio> municipios = new ArrayList<>();
            
            for (MunicipioJPA municipioJPA : municipiosJPA) {
             Municipio municipio = modelMapper.map(municipioJPA, Municipio.class);
             municipios.add(municipio);
            }
            
            result.objects = (List<Object>)(List<?>) municipios;
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
