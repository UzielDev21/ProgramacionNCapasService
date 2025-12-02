package com.Uziel.UCastanedaProgramacionNCapas.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenListaNegraService {
    
    private final Set<String> listaNegra = ConcurrentHashMap.newKeySet();
    
    public void agregarListaNegra(String jti){
        listaNegra.add(jti);
        System.out.println("Token agregado correctamente " + jti);
    }
    
    public boolean isBlackListed(String jti){
        return listaNegra.contains(jti);
    }

}
