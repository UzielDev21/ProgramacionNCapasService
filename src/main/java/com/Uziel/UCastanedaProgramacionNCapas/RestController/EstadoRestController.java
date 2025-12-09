package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.EstadoJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("estados")
public class EstadoRestController {
    
    @Autowired
    private EstadoJPADAOImplementation estadoJPADAOImplementation;
    
    @GetMapping("/{IdPais}")
    public ResponseEntity GetByIdPais(@PathVariable("IdPais") int IdPais){
        Result result = new Result();
        
        try {
            
            if (IdPais != 0) {
                
                result = estadoJPADAOImplementation.GetByIdPaisJPA(IdPais);
                
                if (result.correct) {
                    result.status = 200;
                } else {
                    result.status = 400;
                }
            } else {
                result.correct = false;
                result.errorMessage = "IdPais no Valido";
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
