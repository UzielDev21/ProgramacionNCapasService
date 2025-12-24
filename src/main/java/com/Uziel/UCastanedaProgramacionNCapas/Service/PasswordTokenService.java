package com.Uziel.UCastanedaProgramacionNCapas.Service;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.IPasswordTokenRepositoryDAO;
import com.Uziel.UCastanedaProgramacionNCapas.DAO.IUsuarioRepositoryDAO;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.PasswordTokenJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordTokenService {

    private final IPasswordTokenRepositoryDAO passwordTokenRepository;
    private final IUsuarioRepositoryDAO usuarioRepository;

    public PasswordTokenService(IPasswordTokenRepositoryDAO passwordTokenRepository,
            IUsuarioRepositoryDAO usuarioRepository) {

        this.passwordTokenRepository = passwordTokenRepository;
        this.usuarioRepository = usuarioRepository;
    }
    
    @Transactional
    public PasswordTokenJPA createTokenForUser(UsuarioJPA usuarioJPA){
        
        String token = UUID.randomUUID().toString();
        
        PasswordTokenJPA passwordTokenjpa = new PasswordTokenJPA(token, usuarioJPA);
        
        return passwordTokenRepository.save(passwordTokenjpa);
    } 
    
    public Optional<PasswordTokenJPA> validateToken(String token){
        return passwordTokenRepository.findByTokenAndExpiryDateAfter(token, LocalDateTime.now());
    }
    
    public Optional<UsuarioJPA> getUserByValidToken(String token){
        return validateToken(token).map(PasswordTokenJPA::getUsuarioJPA);
    }
    
    @Transactional
    public int invalidateAllTokenForUser(UsuarioJPA usuarioJPA){
        return passwordTokenRepository.DeleteAllByUsuario(usuarioJPA);
    }

    @Transactional
    public int cleanupExpiredTokens(){
        return passwordTokenRepository.deleteAllExpiredSince(LocalDateTime.now());
    }
    
}
