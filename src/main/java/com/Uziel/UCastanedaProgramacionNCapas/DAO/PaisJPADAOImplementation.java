package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.PaisJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PaisJPADAOImplementation implements IPaisJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Result GetAllJPA() {

        Result result = new Result();

        try {
            TypedQuery<PaisJPA> queryPais = entityManager.createQuery("FROM PaisJPA", PaisJPA.class);
            List<PaisJPA> paisesJPA = queryPais.getResultList();

            
//            result.object = paisesJPA;
            result.objects = (List<Object>)(List<?>)paisesJPA;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
