package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.IUsuarioRepositoryDAO;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.PasswordTokenJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.Service.PasswordEmailProducer;
import com.Uziel.UCastanedaProgramacionNCapas.Service.PasswordTokenService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class PasswordRestController {

    private final IUsuarioRepositoryDAO usuarioRepository;
    private final PasswordTokenService passwordTokenService;
    private final PasswordEmailProducer passwordEmailProducer;

    public PasswordRestController(IUsuarioRepositoryDAO usuarioRepository,
            PasswordTokenService passwordTokenService,
            PasswordEmailProducer passwordEmailProducer) {

        this.usuarioRepository = usuarioRepository;
        this.passwordTokenService = passwordTokenService;
        this.passwordEmailProducer = passwordEmailProducer;
    }

    @PostMapping("/reset-password")
    public ResponseEntity requestPasswordEmail(@RequestParam("email") String email) {
        Result result = new Result();

        try {

            UsuarioJPA usuarioJPA = usuarioRepository.findByEmail(email);

            if (usuarioJPA == null) {
                result.correct = true;
                result.status = 200;
                result.object = "Enlace de recuperación enviado correctamente.";
                return ResponseEntity.status(result.status).body(result);
            }

            PasswordTokenJPA tokenEntity = passwordTokenService.createTokenForUser(usuarioJPA);
            passwordEmailProducer.senderPasswordEmail(
                    usuarioJPA.getEmail(),
                    usuarioJPA.getNombre(),
                    tokenEntity.getToken());

            result.correct = true;
            result.status = 200;
            result.object = "Enlace de recuperación enviado correctamente.";

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    
    @GetMapping("/reset-password")
    public ResponseEntity validarPasswordToken(@RequestParam("token") String token){
        Result result = new Result();
        
        try {
            Optional<UsuarioJPA> usuarioJPAOptional = passwordTokenService.getUserByValidToken(token);
            
            if (usuarioJPAOptional.isEmpty()) {
                result.correct = false;
                result.status = 400;
                result.errorMessage = "Token invalido o expirado.";
            } else {
                result.correct = true;
                result.status = 200;
                result.object = "Token valido. puedes continuar con el cambio de contraseña.";
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    } 
}
