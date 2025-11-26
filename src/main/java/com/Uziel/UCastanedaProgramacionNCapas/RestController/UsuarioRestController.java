package com.Uziel.UCastanedaProgramacionNCapas.RestController;

import com.Uziel.UCastanedaProgramacionNCapas.DAO.UsuarioJPADAOImplementation;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.RolJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.Service.CargaMasivaLogger;
import com.Uziel.UCastanedaProgramacionNCapas.Service.JwtService;
import com.Uziel.UCastanedaProgramacionNCapas.Service.TokenService;
import jakarta.persistence.EntityExistsException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("usuario")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation usuarioJPADAOImplementation;
    
    @Autowired
    private CargaMasivaLogger cargaMasivaLogger;
    
    @Autowired
    private TokenService tokenService;
     
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

    @PostMapping("/cargaMasiva/validar")
    public ResponseEntity ValidarCarga(@RequestParam("file") MultipartFile file){
        Result result = new Result();
        String nombreArchivo = null;
        
        try {
            nombreArchivo = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + "CargaMasiva";
            List<UsuarioJPA> usuariosArchivo = new ArrayList<>();
            List<String> erroresLocal = new ArrayList<>();
            
            if (file.getOriginalFilename().endsWith(".txt")) {
                String contenido = new String(file.getBytes(), StandardCharsets.UTF_8);
                String[] lineas = contenido.split("\n");
                
                int indice = 1;
                
                for (String linea : lineas) {
                    String [] campos = linea.split("\\|");
                    
                    if (campos.length < 13) {
                        erroresLocal.add("Linea " + indice + ": Formato invalido");
                    } else {
                        UsuarioJPA usuario = new UsuarioJPA();
                        usuario.setUserName(campos[0].trim());
                        usuario.setNombre(campos[1].trim());
                        usuario.setApellidoPaterno(campos[2].trim());
                        usuario.setApellidoMaterno(campos[3].trim());
                        usuario.setEmail(campos[4].trim());
                        usuario.setPassword(campos[5].trim());
                       
                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String fechaIngresada = campos[6];
                        Date fechaFormateda = formatter.parse(fechaIngresada);
                        usuario.setFechaNacimiento(fechaFormateda);
                        
                        usuario.setSexo(campos[7].trim());;
                        usuario.setTelefono(campos[8].trim());;
                        usuario.setCelular(campos[9].trim());;
                        usuario.setCurp(campos[10].trim());;
                        usuario.setStatus(Integer.parseInt(campos[11].trim()));;
                        
                        usuario.RolJPA = new RolJPA();
                        usuario.RolJPA.setIdRol(Integer.parseInt(campos[12].trim()));
                        
                        usuariosArchivo.add(usuario);
                    }
                    indice++;
                }
                
                
            } else {
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
