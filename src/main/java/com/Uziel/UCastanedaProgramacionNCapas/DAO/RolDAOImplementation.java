package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOImplementation implements IRolDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = jdbcTemplate.execute("{CALL RolGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();

            try {
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                resultSP.objects = new ArrayList<>();

                while (resultSet.next()) {
                    Rol rol = new Rol();

                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setNombreRol(resultSet.getString("Rol"));
                    rol.setDescripcion(resultSet.getString("Descripcion"));
                    resultSP.objects.add(rol);
                }
                resultSP.correct = true;
            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }
            return resultSP;
        });
        return result;
    }

}
