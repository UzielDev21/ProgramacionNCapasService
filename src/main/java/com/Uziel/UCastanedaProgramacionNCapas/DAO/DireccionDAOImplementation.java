package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Colonia;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Direccion;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Estado;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Municipio;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Pais;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;
import java.sql.ResultSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result DireccionGetbyId(int IdDireccion) {
        return jdbcTemplate.execute("CALL DireccionGetById(?,?)", (CallableStatementCallback<Result>) callableStatement -> {

            Result result = new Result();

            try {
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

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

                    estado.setPais(pais);
                    municipio.setEstado(estado);
                    colonia.setMunicipio(municipio);
                    direccion.setColonia(colonia);
                    
                    result.object = direccion;
                    result.correct = true;
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;

        });
    }

    @Override
    public Result DireccionAdd(Direccion direccion, int IdUsuario) {
        return jdbcTemplate.execute("Call direccionAdd(?,?,?,?,?)", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();
            try {

                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, IdUsuario);
                callableStatement.execute();

                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMessage = ex.getLocalizedMessage();
                result.ex = ex;
            }

            return result;
        });
    }

    @Override
    public Result DireccionDelete(int IdDireccion) {
        return jdbcTemplate.execute("CALL DireccionDelete(?)", (CallableStatementCallback<Result>) callableStatement -> {

            Result result = new Result();

            try {
                callableStatement.setInt(1, IdDireccion);
                callableStatement.execute();

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
