package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioRestController {
    
    
    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAll(){
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
    public ResponseEntity GetById(@PathVariable("IdUsuario") int IdUsuario){
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
    

}
