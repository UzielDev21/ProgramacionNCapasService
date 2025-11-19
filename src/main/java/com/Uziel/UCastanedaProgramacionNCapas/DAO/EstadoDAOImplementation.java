package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Estado;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result EstadosGetByIdPais(int IdPais){
        
        return jdbcTemplate.execute("CALL EstadosGetByIdPais(?, ?)", (CallableStatementCallback<Result>) callableStatement -> {
        
            Result resultSP = new Result();
            
            try {
                
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSetEstados = (ResultSet) callableStatement.getObject(2);
                
                resultSP.objects = new ArrayList<>();
                
                while (resultSetEstados.next()) {
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSetEstados.getInt("IdEstado"));
                    estado.setNombre(resultSetEstados.getString("Nombre"));
                    resultSP.objects.add(estado);
                }
                
               resultSP.correct = true;
               
                
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            
        return resultSP;
        });
    }
    
}
