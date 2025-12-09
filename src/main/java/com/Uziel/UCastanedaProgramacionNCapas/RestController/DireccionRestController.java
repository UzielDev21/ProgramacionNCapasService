package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.DireccionJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.DireccionJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("direccion")
public class DireccionRestController {

    @Autowired
    private DireccionJPADAOImplementation direccionJPADAOImplementation;

    @GetMapping("/{IdDireccion}")
    public ResponseEntity GetByIdDireccion(@PathVariable("IdDireccion") int IdDireccion) {
        Result result = new Result();

        try {
            result = direccionJPADAOImplementation.DireccionGetByIdJPA(IdDireccion);
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

    @PostMapping("/{IdUsuario}")
    public ResponseEntity AddDireccion(@RequestBody DireccionJPA direccionJPA, @PathVariable("IdUsuario") int IdUsuario) {
        Result result = new Result();

        try {
            result = direccionJPADAOImplementation.DireccionAddJPA(direccionJPA, IdUsuario);

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.status = 500;
        }

        return ResponseEntity.status(result.status).body(result);
    }

    @PutMapping("/{IdDireccion}")
    public ResponseEntity UpdateDireccion(@RequestBody DireccionJPA direccionJPA, @PathVariable("IdDireccion") int IdDireccion) {
        Result result = new Result();

        try {
            if (IdDireccion > 0) {
                result = direccionJPADAOImplementation.DireccionUpdateJPA(direccionJPA, IdDireccion);

                if (result.correct) {
                    result.status = 200;
                } else {
                    result.status = 400;
                }
            } else {
                result.correct = false;
                result.errorMessage = "IdDireccion no proporcionada";
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

    @DeleteMapping("/{IdDireccion}")
    public ResponseEntity DeleteDirecicon(@PathVariable("IdDireccion") int IdDireccion) {
        Result result = new Result();

        try {
            if (IdDireccion != 0) {
                result = direccionJPADAOImplementation.DireccionDeleteJPA(IdDireccion);
                result.correct = true;
                result.status = 200;
            } else {
                result.correct = false;
                result.errorMessage = "No se pudo eliminar correctamente la direccion";
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
