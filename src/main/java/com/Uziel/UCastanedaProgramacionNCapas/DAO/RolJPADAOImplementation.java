package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.RolJPA;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Rol;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAllJPA() {
        Result result = new Result();

        try {
            TypedQuery<RolJPA> queryRol = entityManager.createQuery("FROM RolJPA", RolJPA.class);
            List<RolJPA> rolesJPA = queryRol.getResultList();

//            List<Rol> roles = rolesJPA.stream().map(rolJPA -> modelMapper.map(rolJPA, Rol.class)).collect(Collectors.toList());
            List<Rol> roles = new ArrayList<>();

            for (RolJPA rolJPA : rolesJPA) {
                Rol rol = modelMapper.map(rolJPA, Rol.class);
                roles.add(rol);
            }

            result.objects = (List<Object>) (List<?>) roles;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
