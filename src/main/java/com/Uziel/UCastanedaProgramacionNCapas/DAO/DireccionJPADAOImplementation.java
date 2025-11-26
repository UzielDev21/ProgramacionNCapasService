package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.ColoniaJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.DireccionJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public Result DireccionGetByIdJPA(int IdDireccion) {
        Result result = new Result();
        
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, IdDireccion);
            
            result.object = direccionJPA;
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public Result DireccionAddJPA(DireccionJPA direccionJPA, int IdUsuario) {
        Result result = new Result();
        
        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, IdUsuario);
            if (usuarioJPA == null) {
                throw new NoSuchElementException("el usuario con el Id " + IdUsuario + " no existe");
            }
            
            direccionJPA.UsuarioJPA = usuarioJPA;
            entityManager.persist(direccionJPA);
            result.correct = true;
            result.status = 201;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public Result DireccionUpdateJPA(DireccionJPA direccion, int IdDireccion) {
        Result result = new Result();
        
        try {
            
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, IdDireccion);
            
            if (direccionJPA == null) {
                result.correct = false;
                result.errorMessage = "no se encontro la direccion";
                result.status = 400;
            } else {
                
                direccion.setIdDireccion(IdDireccion);
                direccion.UsuarioJPA = direccionJPA.UsuarioJPA;
                
                if (direccion.ColoniaJPA == null || direccion.ColoniaJPA.getIdColonia() == 0) {
                  direccion.ColoniaJPA = direccionJPA.ColoniaJPA;  
                } 
                
                entityManager.merge(direccion);
                result.correct = true;
                result.status = 200;
            }            
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    @Override
    @Transactional
    public Result DireccionDeleteJPA(int IdDireccion) {
        Result result = new Result();
        
        try {
            DireccionJPA direccionJPA = entityManager.find(DireccionJPA.class, IdDireccion);
            
            if (direccionJPA != null) {
                entityManager.remove(direccionJPA);
            } else {
                result.correct = false;
                result.errorMessage = "No existe la Direccion";
            }
            
            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
}
