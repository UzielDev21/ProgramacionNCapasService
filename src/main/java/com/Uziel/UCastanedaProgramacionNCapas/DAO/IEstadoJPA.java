package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;

public interface IEstadoJPA {

    Result GetAllJPA();
    
    Result GetByIdPaisJPA(int IdPais);
}
