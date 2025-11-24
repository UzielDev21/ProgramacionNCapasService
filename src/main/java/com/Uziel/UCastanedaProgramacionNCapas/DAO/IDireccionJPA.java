package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.DireccionJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;

public interface IDireccionJPA {

    Result DireccionGetByIdJPA(int IdDireccion);
    
    Result DireccionAddJPA(DireccionJPA direccionJPA, int IdUsuario);
    
    Result DireccionUpdateJPA(DireccionJPA direccionJPA, int IdDireccion);
    
    Result DireccionDeleteJPA(int IdDireccion);
    
}
