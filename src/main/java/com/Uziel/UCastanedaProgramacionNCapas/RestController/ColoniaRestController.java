package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.ColoniaJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("colonias")
public class ColoniaRestController {

    
    @Autowired
    private ColoniaJPADAOImplementation coloniaJPADAOImplementation;
    
    @GetMapping("/{IdMunicipio}")
    public ResponseEntity GetByIdEstado(@PathVariable("IdMunicipio") int IdMunicipio){
        Result result = new Result();
        
        try {
            if (IdMunicipio != 0) {
               
                result = coloniaJPADAOImplementation.GetByIdMunicipioJPA(IdMunicipio);
                
                if (result.correct) {
                    result.status = 200;
                } else {
                    result.status = 500;
                }
                
            } else {
                result.correct = false;
                result.errorMessage = "Municipio no Valido";
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
