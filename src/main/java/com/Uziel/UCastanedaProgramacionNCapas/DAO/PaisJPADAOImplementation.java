package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.PaisJPA;
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
public class PaisJPADAOImplementation implements IPaisJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAllJPA() {

        Result result = new Result();

        try {
            TypedQuery<PaisJPA> queryPais = entityManager.createQuery("FROM PaisJPA", PaisJPA.class);
            List<PaisJPA> paisesJPA = queryPais.getResultList();

            List<Pais> paises = new ArrayList<>();

            for (PaisJPA paisJPA : paisesJPA) {

                Pais pais = modelMapper.map(paisJPA, Pais.class);
                paises.add(pais);
            }

            result.objects = (List<Object>) (List<?>) paises;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
