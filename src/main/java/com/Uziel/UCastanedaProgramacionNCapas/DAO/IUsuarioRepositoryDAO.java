package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsuarioRepositoryDAO extends JpaRepository<UsuarioJPA, Integer> {

    UsuarioJPA findByUserName(String userName);
    
    Optional<UsuarioJPA> findByEmail(String Emai);
    
}
