package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.PasswordTokenJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface IPasswordTokenRepositoryDAO extends JpaRepository<PasswordTokenJPA, Integer> {
    
    Optional<PasswordTokenJPA> findByToken(String token);

    List<PasswordTokenJPA> findAllByUsuarioJPAOrderByExpiryDateDesc(UsuarioJPA usuarioJPA);
    
    List<PasswordTokenJPA> findTopByUsuarioJPAOrderByExpiryDateDesc(UsuarioJPA usuarioJPA);
    
    Optional<PasswordTokenJPA> findByTokenAndExpiryDateAfter(String token, LocalDateTime now);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM PasswordTokenJPA token WHERE token.usuarioJPA = :usuarioJPA")
    int DeleteAllByUsuario(@Param("usuarioJPA") UsuarioJPA usuarioJPA);
    
    @Transactional
    @Modifying
    @Query("DELETE FROM PasswordTokenJPA token WHERE token.expiryDate < :now")
    int deleteAllExpiredSince(@Param("now") LocalDateTime now);
    
}
