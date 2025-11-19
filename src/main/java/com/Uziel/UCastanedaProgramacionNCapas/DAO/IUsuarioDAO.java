package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;
import java.util.List;

interface IUsuarioDAO {

        Result GetAll();
        
        Result GetById(int IdUsuario);
        
        Result Add(Usuario usuario);
        
        Result AddAll(List<Usuario> usuarios);
        
        Result Update(Usuario usuario);
        
        Result UsuariosBuscar(Usuario usuario);
}
