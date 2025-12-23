package com.Uziel.UCastanedaProgramacionNCapas.DAO;

//import com.Uziel.UCastanedaProgramacionNCapas.JPA.PasswordTokenJPA;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//public interface IPasswordTokenRepositoryDAO extends JpaRepository<PasswordTokenJPA, Integer> {
//
//    Optional<PasswordTokenJPA> findByToken(String token);
//
//    List<PasswordTokenJPA> findByUsuarioJPA_IdUsuarioAndIsUsed(int idUsuario, int isUsed);
//
//    @Modifying
//    @Transactional
//    @Query("""
//           UPDATE PasswordTokenJPA token
//           SET token.isUsed = 1
//           WHERE token.usuarioJPA.idUsuario = :idUsuario AND token.isUsed = 0
//           """)
//    int intinvalidateAtiveTokenByUser(@Param("idUsuario") int idUsuario);
//
//    @Modifying
//    @Transactional
//    @Query("""
//           DELETE FROM PasswordTokenJPA token
//           WHERE token.expiration < :now OR token.isUsed = 1
//           """)
//    int deleteExpiredOrUsed(@Param("now") Date now);
//
//}
