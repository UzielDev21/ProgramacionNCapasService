package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.ColoniaJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.DireccionJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionJPADAOImplementation implements IDireccionJPA {

    @PersistenceContext
    private EntityManager entityManager;
            
    @Override
    @Transactional
    public Result DireccionGetByIdJPA(int IdDireccion){
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
//            DireccionJPA direccionJPA = modelMapper.map(direccion, DireccionJPA.class);
//            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, IdUsuario);
//            ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccion.Colonia.getIdColonia());
//            direccionJPA.setUsuarioJPA(usuarioJPA);
//            direccionJPA.setColoniaJPA(coloniaJPA);
//
//            entityManager.persist(direccionJPA);
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
    public Result DireccionUpdateJPA(DireccionJPA direccionJPA) {
        Result result = new Result();

        try {
//            DireccionJPA direccionBase = entityManager.find(DireccionJPA.class, direccion.getIdDireccion());
//            DireccionJPA direccionJPA = modelMapper.map(direccion, DireccionJPA.class);
//
//            ColoniaJPA coloniaJPA = entityManager.find(ColoniaJPA.class, direccion.Colonia.getIdColonia());
//            direccionJPA.setColoniaJPA(coloniaJPA);
//
//            direccionJPA.setUsuarioJPA(direccionBase.getUsuarioJPA());
//            entityManager.merge(direccionJPA);
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
