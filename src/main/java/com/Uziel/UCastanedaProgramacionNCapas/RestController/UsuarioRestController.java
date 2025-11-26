package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.Service.CargaMasivaLogger;
import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import jakarta.persistence.EntityExistsException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @Autowired
    private CargaMasivaLogger cargaMasivaLogger;
    
//    @PostMapping("/CargaMasivaProcesar")
//    public ResponseEntity AddAll(@RequestHeader("Authorization") String authotizarionHeader, 
//            @RequestBody List<UsuarioJPA> usuarios){
//        Result result = new Result();
//        
//        try {
//            String token = authotizarionHeader.replace("Bearer ", "").trim();
//            
//            if (!jwtService.isTokenValid(token)) {
//                result.correct = false;
//                result.errorMessage = "Token Invalido";
//                result.status = 401;
//                cargaMasivaLogger.writeLog("cargaMasiva", token, "Error", "");
//                
//                return ResponseEntity.status(result.status).body(result);
//            }
//            
//            if (jwtService.isExpired(token)) {
//                result.correct = false;
//                result.errorMessage = "Token Expirado";
//                result.status = 401;
//                cargaMasivaLogger.writeLog("CargaMasiva", token, "Error", "Token Expirado");
//
//                return ResponseEntity.status(result.status).body(result);
//            }
//            
//            Result resultCarga = usuarioJPADAOImplementation.AddAllJPA(usuarios);
//            
//            if (!resultCarga.correct) {
//                result.correct = false;
//                result.errorMessage = resultCarga.errorMessage;
//                result.ex = resultCarga.ex;
//                result.status = 500;
//                
//                cargaMasivaLogger.writeLog("cargaMasiva", token, "Error", "Error al insertar usuarios" + resultCarga.errorMessage);
//                return ResponseEntity.status(result.status).body(result);
//            }
//            
//            result.correct = true;
//            result.status = 200;
//            result.objects = (List<Object>)(Object) usuarios;
//            
//            cargaMasivaLogger.writeLog("CargaMasiva", token, "Procesado", "");
//            return ResponseEntity.status(result.status).body(result);
//        } catch (Exception ex) {
//            result.correct = false;
//            result.errorMessage = ex.getLocalizedMessage();
//            result.ex = ex;
//            result.status = 500;
//        }
//        
//        return ResponseEntity.status(result.status).body(result);
//    }
    
    
        
    @GetMapping
    public ResponseEntity GetAll() {
        Result result = new Result();

        try {
            result = usuarioJPADAOImplementation.GetAllJPA();
            result.correct = true;
            result.status = 200;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @GetMapping("/{IdUsuario}")
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario) {
        Result result = new Result();

        try {
            if (IdUsuario != 0) {
                result = usuarioJPADAOImplementation.GetByIdJPA(IdUsuario);
                result.correct = true;
                result.status = 200;
            } else {
                result.correct = false;
                result.errorMessage = "no se encuentra el dato requerido :C";
                result.status = 400;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PostMapping("/buscar")
    public ResponseEntity GetAllDinamico(@RequestBody UsuarioJPA usuario){
        Result result = new Result();
        
        try {
            result = usuarioJPADAOImplementation.BuscarUsuarioJPA(usuario);
            result.correct = true;
            result.status = 200;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @PostMapping
    public ResponseEntity AddUsuario(@RequestBody UsuarioJPA usuarioJPA) {
        Result result = new Result();

        try {
            result = usuarioJPADAOImplementation.AddJPA(usuarioJPA);

            if (result.status == 0) {
                if (result.correct) {
                    result.status = 201;
                } else {
                    if (result.ex instanceof EntityExistsException) {
                        result.status = 409;
                    } else {
                        result.status = 500;
                    }
                }
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @DeleteMapping("/{IdUsuario}")
    public ResponseEntity DeleteUsuario(@PathVariable("IdUsuario") int IdUSuario) {
        Result result = new Result();

        try {
            if (IdUSuario != 0) {
                result = usuarioJPADAOImplementation.DeleteJPA(IdUSuario);
                result.correct = true;
                result.status = 200;
            } else {
                result.correct = false;
                result.errorMessage = "No existe el usuario";
                result.status = 500;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }
   
    @PatchMapping("/imagen/{IdUsuario}")
    @CrossOrigin(origins = "http://localhost:8081")
    public ResponseEntity UpdateImagen(@PathVariable("IdUsuario") int IdUsuario, @RequestBody UsuarioJPA usuario){
        Result result = new Result();
        
        try {
            result = usuarioJPADAOImplementation.UpdateImagenJPA(IdUsuario, usuario.getImagen());
            
            if (result.correct) {
                result.status = 200;
                return ResponseEntity.status(result.status).body(result);
            } else {
                result.status = 400;
                return ResponseEntity.status(result.status).body(result);
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
    
    @PutMapping
    public ResponseEntity Update(@RequestBody UsuarioJPA usuarioJPA){
        Result result = new Result();
        
        try {
            if (usuarioJPA.getIdUsuario() > 0) {
                result = usuarioJPADAOImplementation.UpdateJPA(usuarioJPA);
                
                if (result.correct) {
                    result.status = 200;
                } else {
                    result.status = 400;
                }
                
            } else {
                result.correct = false;
                result.errorMessage = "IdUsuario no proporcionado";
                result.status = 400;
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }
        
        return ResponseEntity.status(result.status).body(result);
    }
}
