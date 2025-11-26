package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import java.util.List;

public interface IUsuarioJPA {
    
    Result GetAllJPA();
    
    Result AddJPA(UsuarioJPA usuarioJPA);
    
    Result UpdateJPA(UsuarioJPA usuario);
    
    Result UpdateImagenJPA(int IdUsuario, String imagenBase64);
    
    Result DeleteJPA(int IdUsuario);
    
    Result GetByIdJPA(int IdUsuario);
    
    Result BuscarUsuarioJPA(UsuarioJPA usuario);
    
    Result AddAllJPA(List<UsuarioJPA> usuariosJPA);
    
    Result ValidarCarga(String nombreArchivo, List<UsuarioJPA> usuariosArchivo);
}
