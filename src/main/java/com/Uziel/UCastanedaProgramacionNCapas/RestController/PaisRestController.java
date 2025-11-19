package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.PaisJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pais")
public class PaisRestController {
    
    @Autowired
    private PaisJPADAOImplementation paisJPADAOImplementation;
    
    @GetMapping
    public ResponseEntity GetAllPais(){
        Result result = new Result();
        
        try {
            
            result = paisJPADAOImplementation.GetAllJPA();
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
    

}
