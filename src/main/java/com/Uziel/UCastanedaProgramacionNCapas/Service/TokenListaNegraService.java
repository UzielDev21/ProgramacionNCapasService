package com.Uziel.UCastanedaProgramacionNCapas.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenListaNegraService {
    
    private final Set<String> listaNegra = ConcurrentHashMap.newKeySet();
    
    public void agregarListaNegra(String token){
        listaNegra.add(token);
        System.out.println("Token agregado correctamente " + token);
    }
    
    public boolean isBlackListed(String token){
        return listaNegra.contains(token);
    }

}
