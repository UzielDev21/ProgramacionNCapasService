package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.IUsuarioRepositoryDAO;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.TokenListaNegraService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class AuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final IUsuarioRepositoryDAO iUsuarioRepositoryDAO;
    private final TokenListaNegraService tokenListaNegraService;

    public AuthRestController(AuthenticationManager authenticationManager,
            JwtService jwtService,
            IUsuarioRepositoryDAO iUsuarioRepositoryDAO,
            TokenListaNegraService tokenListaNegraService) throws Exception {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.iUsuarioRepositoryDAO = iUsuarioRepositoryDAO;
        this.tokenListaNegraService = tokenListaNegraService;
    }

    @PostMapping("/Login")
    public ResponseEntity login(@RequestBody Map<String, String> json) {

        Result result = new Result();

        try {

            String username = json.get("userName");
            String password = json.get("password");

            UsernamePasswordAuthenticationToken authInput = new UsernamePasswordAuthenticationToken(
                    username,
                    password);

            try {
                authenticationManager.authenticate(authInput);
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = "Credenciales invalidas";
                result.status = 401;
                return ResponseEntity.status(result.status).body(result);
            }

            UsuarioJPA usuario = iUsuarioRepositoryDAO.findByUserName(username);
            String rol = usuario.RolJPA.getNombreRol();
            int idUsuario = usuario.getIdUsuario();

            if (usuario == null) {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado";
                result.status = 401;
                return ResponseEntity.status(result.status).body(result);
            }

            String jwt = jwtService.GenerateUserToken(username, idUsuario, rol);
            result.correct = true;
            result.status = 200;
            result.object = jwt;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/Logout")
    public ResponseEntity Logout(HttpServletRequest request) {

        Result result = new Result();

        try {

            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                result.correct = false;
                result.errorMessage = "No se encontro el token";
                result.status = 400;
                return ResponseEntity.status(result.status).body(result);
            }

            String token = authHeader.substring(7);
            tokenListaNegraService.agregarListaNegra(token);
            SecurityContextHolder.clearContext();

            result.correct = true;
            result.status = 200;
            result.object = "Logout Exitoso";

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

}
