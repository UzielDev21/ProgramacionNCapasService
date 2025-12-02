package com.Uziel.UCastanedaProgramacionNCapas.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenUsoService {

    private final Map<String, Integer> tokenUsoMap = new ConcurrentHashMap<>();
    private static final int LIMITE_USO = 5;

    public void registrarUso(String jti) {
        Integer ususActuales = tokenUsoMap.get(jti);

        if (ususActuales == null) {
            tokenUsoMap.put(jti, 1);
        } else {
            tokenUsoMap.put(jti, ususActuales + 1);
        }
        
        System.out.println("uso registrado: " + jti + " total usos: " + tokenUsoMap.get(jti));

    }
    
    public boolean excedioLimite(String jti){
        int usos = tokenUsoMap.getOrDefault(jti, 0);
        return usos >= LIMITE_USO;
    }
    
    public void eliminarToken(String jti){
        tokenUsoMap.remove(jti);
    }

}
