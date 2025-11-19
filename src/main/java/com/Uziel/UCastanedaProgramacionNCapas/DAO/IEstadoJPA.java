package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;

public interface IEstadoJPA {

    Result GetAllJPA();
    
    Result GetByIdPaisJPA(int IdPais);
}
