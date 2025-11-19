package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;
import java.util.List;

public interface IUsuarioJPA {
    
    Result GetAllJPA();
    
    Result AddJPA(Usuario usuario);
    
    Result UpdateJPA(Usuario usuario);
    
    Result UpdateImagenJPA(int IdUsuario, String imagenBase64);
    
    Result DeleteJPA(int IdUsuario);
    
    Result GetByIdJPA(int IdUsuario);
    
    Result BuscarUsuarioJPA(Usuario usuario);
    
    Result AddAllJPA(List<Usuario> usuarios);
}
