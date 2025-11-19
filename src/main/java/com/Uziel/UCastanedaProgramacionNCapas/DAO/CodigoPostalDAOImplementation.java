package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Colonia;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Estado;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Municipio;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Pais;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CodigoPostalDAOImplementation implements ICodigoPostalDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result CodigoPostalGetDatos(String CodigoPostal) {

        return jdbcTemplate.execute("CALL CodigoPostalGetDatos(?,?)", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setString(1, CodigoPostal);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    colonia.setNombreColonia(resultSet.getString("NombreColonia"));
                    colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    municipio.setNombre(resultSet.getString("NombreMunicipio"));
                    
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("IdEstado"));
                    estado.setNombre(resultSet.getString("NombreEstado"));
                    
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("NombrePais"));
                    
                    estado.Pais = pais;
                    municipio.Estado = estado;
                    colonia.Municipio = municipio;
                    
                    result.objects.add(colonia);
                }
                result.correct = true;

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });

    }

}
