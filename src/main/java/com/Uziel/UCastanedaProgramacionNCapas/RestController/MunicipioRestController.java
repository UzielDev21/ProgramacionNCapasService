package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.MunicipioJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("municipios")
public class MunicipioRestController {
    
    @Autowired
    private MunicipioJPADAOImplementation municipioJPADAOImplementation; 
    
    @GetMapping("/{IdEstado}")
    public ResponseEntity GetByIdEstado(@PathVariable("IdEstado") int IdEstado){
        Result result = new Result();
        
        try {
            
            if (IdEstado != 0) {
                
                result = municipioJPADAOImplementation.GetByIdEstadoJPA(IdEstado);
                
                if (result.correct) {
                    result.status = 200;
                } else {
                    result.status = 400;
                }
                
            } else {
                result.correct = false;
                result.errorMessage = "Estado no valido" + IdEstado;
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
