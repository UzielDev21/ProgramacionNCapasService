package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Direccion;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;

public interface IDireccionDAO {
    
    Result DireccionGetbyId(int IdDireccion);
    
    Result DireccionAdd(Direccion direccion, int IdUsuario);
    
    Result DireccionDelete(int IdDireccion);
}
