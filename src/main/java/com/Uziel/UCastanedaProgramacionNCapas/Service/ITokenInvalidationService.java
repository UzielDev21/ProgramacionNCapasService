package com.Uziel.UCastanedaProgramacionNCapas.Service;


public interface ITokenInvalidationService {

    void invalidateToken(String jti);
    boolean isTokenInvalid(String jti);
    
}
