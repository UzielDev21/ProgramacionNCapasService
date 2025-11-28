package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.IUsuarioRepositoryDAO;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import io.jsonwebtoken.Jwts;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/Login")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUsuarioRepositoryDAO iUsuarioRepositoryDAO;

    public AuthRestController(AuthenticationManager authenticationManager,
            JwtService jwtService,
            IUsuarioRepositoryDAO iUsuarioRepositoryDAO) throws Exception {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
    }

    @PostMapping
    public ResponseEntity login(@RequestBody Map<String, String>json){
        
        String username = json.get("userName");
        String password = json.get("password");
        
        UsernamePasswordAuthenticationToken authInput = new UsernamePasswordAuthenticationToken(
                username,
                password);
        
        try {
            authenticationManager.authenticate(authInput);
        } catch (Exception ex) {
            return ResponseEntity.status(401).body(Map.of("Error","Credenciales invalidas"));
        }
        
        UsuarioJPA usuario = iUsuarioRepositoryDAO.findByUserName(username);
        String rol = usuario.RolJPA.getNombreRol();
        int idUsuario = usuario.getIdUsuario();
        
        String jwt = jwtService.GenerateUserToken(username, idUsuario, rol);
        return ResponseEntity.ok(Map.of(
                "token", jwt,
                "UserName", username,
                "rol", rol,
                "idUsuario", idUsuario
        ));
    }
}
