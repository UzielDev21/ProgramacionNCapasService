package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.RolJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolJPADAOImplementation implements IRolJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Result GetAllJPA() {
        Result result = new Result();

        try {
            TypedQuery<RolJPA> rolesJPA = entityManager.createQuery(
                    "FROM RolJPA", RolJPA.class);
            List<RolJPA> roles = rolesJPA.getResultList();

//            result.object = roles;
            result.objects = (List<Object>)(List<?>) roles;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
