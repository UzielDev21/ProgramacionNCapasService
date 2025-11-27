package com.Uziel.UCastanedaProgramacionNCapas.Service;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenCacheService {
    private final static Map<String, LocalDateTime> tokenFechaValidacion = new ConcurrentHashMap<>();
    private final static Map<String, String> tokenNombreArchivo = new ConcurrentHashMap<>();
    private final static Map<String, List<UsuarioJPA>> tokenUsuariosArchivo = new ConcurrentHashMap<>();
    
    public void guardarToken(String token, String nombreArchivo, List<UsuarioJPA> usuarios,LocalDateTime fechaValidacion){
        tokenFechaValidacion.put(token, fechaValidacion);
        tokenNombreArchivo.put(token, nombreArchivo);
        tokenUsuariosArchivo.put(token, usuarios);
    }
    
    public LocalDateTime obtenerFechaValidacion(String token){
        return tokenFechaValidacion.get(token);
    } 
    
    public String obtenerNombreArchivo(String token){
        return tokenNombreArchivo.get(token);
    }
    
    public List<UsuarioJPA> obtenerUsuarios(String token){
        return tokenUsuariosArchivo.get(token);
    }
    
    public boolean existeToken(String token){
        return tokenFechaValidacion.containsKey(token);
    }
    
    public void elimianrToken(String token){
        tokenFechaValidacion.remove(token);
        tokenNombreArchivo.remove(token);
        tokenUsuariosArchivo.remove(token);
    }
    
    
}
