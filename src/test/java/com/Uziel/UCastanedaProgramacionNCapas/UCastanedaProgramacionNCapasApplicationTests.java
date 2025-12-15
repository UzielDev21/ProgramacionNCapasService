package com.Uziel.UCastanedaProgramacionNCapas;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.RolJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UCastanedaProgramacionNCapasApplicationTests {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void add() {
//        Result result = new Result();
//
//        try {
//            UsuarioJPA usuario = new UsuarioJPA();
//
//            usuario.setUserName("PruebaTest_01");
//            usuario.setNombre("UniPru");
//            usuario.setApellidoPaterno("Romerjo");
//            usuario.setApellidoMaterno("rojas");
//            usuario.setFechaNacimiento(java.sql.Date.valueOf("2000-02-21"));
//            usuario.setEmail("UniTest@gmail.com");
//            usuario.setPassword("258PruebaM0");
//            usuario.setSexo("M ");
//            usuario.setTelefono("2536910346");
//            usuario.setCelular("2536910363");
//            usuario.setCurp("CAJU020521HMCSSZA4");
//            RolJPA rol = entityManager.find(RolJPA.class, 5);
//            usuario.RolJPA = rol;
//            result = usuarioJPADAOImplementation.AddJPA(usuario);
//
//            Assertions.assertTrue(result.correct);
//            Assertions.assertEquals(usuario.getStatus(), 1);
//            Assertions.assertEquals(usuario.getIsVerified(), 0);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            result.correct = false;
//            result.errorMessage = ex.getLocalizedMessage();
//            result.ex = ex;
//        }

    }

}
