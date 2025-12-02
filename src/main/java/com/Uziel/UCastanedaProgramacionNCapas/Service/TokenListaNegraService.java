package com.Uziel.UCastanedaProgramacionNCapas.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class TokenListaNegraService implements ITokenInvalidationService{
    
    private final Set<String> listaNegra = ConcurrentHashMap.newKeySet();
    
    @Override
    public void invalidateToken(String jti){
        listaNegra.add(jti);
        System.out.println("Token Invalidado Correctamente " + jti);
    }
    
    @Override
    public boolean isTokenInvalid(String jti){
        return listaNegra.contains(jti);
    }

}
