package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.EstadoJPA;
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
public class EstadoJPADAOImplementation implements IEstadoJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAllJPA() {
        Result result = new Result();

        try {
//            TypedQuery<EstadoJPA> queryEstado = entityManager.createQuery("FROM EstadoJPA", EstadoJPA.class);
//            List<EstadoJPA> estadosJPA = queryEstado.getResultList();
//
//            List<Estado> estados = new ArrayList<>();
//
//            for (EstadoJPA estadoJPA : estadosJPA) {
//                Estado estado = modelMapper.map(estadosJPA, Estado.class);
//                estados.add(estado);
//            }
//
//            result.objects = (List<Object>) (List<?>) estados;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByIdPaisJPA(int IdPais) {
        Result result = new Result();

        try {

            TypedQuery<EstadoJPA> estadosJPA = entityManager.createQuery(
                    "FROM EstadoJPA estadoJPA WHERE estadoJPA.PaisJPA.IdPais = :IdPais", EstadoJPA.class);
            estadosJPA.setParameter("IdPais", IdPais);

            List<EstadoJPA> estados = estadosJPA.getResultList();

            if (estados == null || estados.isEmpty()) {
                result.correct = false;
                result.errorMessage = "No existe el pais con el Id = " + IdPais;
            } else {
                result.correct = true;
//                result.object = estados;
                result.objects = (List<Object>) (List<?>) estados;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
