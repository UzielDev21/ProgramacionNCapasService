package com.Uziel.UCastanedaProgramacionNCapas.Service;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.IUsuarioRepositoryDAO;
import com.Uziel.UCastanedaProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.PasswordTokenJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    private final VerificationTokenService verificationTokenService;
    private final VerificationEmailProducer verificationEmailProducer;
    private final IUsuarioRepositoryDAO iUsuarioRepositoryDAO; 

    public UsuarioService(UsuarioJPADAOImplementation usuarioJPADAOImplementation,
            VerificationTokenService verificationTokenService,
            VerificationEmailProducer verificationEmailProducer,
            IUsuarioRepositoryDAO iUsuarioRepositoryDAO) {

        this.usuarioJPADAOImplementation = usuarioJPADAOImplementation;
        this.verificationTokenService = verificationTokenService;
        this.verificationEmailProducer = verificationEmailProducer;
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
    }

    @Transactional
    public Result registrarUsuario(UsuarioJPA usuarioJPA) {

        Result result = new Result();

        try {
            
            result = usuarioJPADAOImplementation.AddJPA(usuarioJPA);

            if (!result.correct) {
                return result;
            }

            int idUsuario = usuarioJPA.getIdUsuario();
            String token = verificationTokenService.generateToken(idUsuario);

            verificationEmailProducer.senderVerificationEmail(
                    usuarioJPA.getEmail(),
                    usuarioJPA.getNombre(),
                    token);

            result.correct = true;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
}
