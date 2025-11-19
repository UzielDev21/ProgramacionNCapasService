package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.ML.Colonia;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Direccion;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Estado;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Municipio;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Pais;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Result;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Rol;
import com.Uziel.UCastanedaProgramacionNCapas.ML.Usuario;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository //Clase que maneja la base de datos
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {

        Result result = jdbcTemplate.execute("{CALL UsuariosDireccionGetAll(?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();
            try {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

                resultSP.objects = new ArrayList<>();

                int IdUsuario = 0;

                while (resultSet.next()) {

                    IdUsuario = resultSet.getInt("IdUsuario");

                    if (!resultSP.objects.isEmpty() && IdUsuario == ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombreColonia(resultSet.getString("Nombrecolonia"));
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

                        ((Usuario) (resultSP.objects.get(resultSP.objects.size() - 1))).Direcciones.add(direccion);

                    } else {

                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario.setUserName(resultSet.getString("UserName"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario.setEmail(resultSet.getString("Email"));
                        usuario.setPassword(resultSet.getString("password"));
                        usuario.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario.setSexo(resultSet.getString("Sexo"));
                        usuario.setTelefono(resultSet.getString("Telefono"));
                        usuario.setCelular(resultSet.getString("Celular"));
                        usuario.setCurp(resultSet.getString("Curp"));
                        usuario.setImagen(resultSet.getString("ImagenFile"));

                        usuario.Rol = new Rol();
                        usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario.Rol.setNombreRol(resultSet.getString("NombreRol"));

                        usuario.Direcciones = new ArrayList<>();

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombreColonia(resultSet.getString("Nombrecolonia"));
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

                        usuario.Direcciones.add(direccion);
                        resultSP.objects.add(usuario);
                    }
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

    @Override
    public Result GetById(int IdUsuario) {
        Result result = jdbcTemplate.execute("{CALL UsuariosDireccionGetById(?,?,?)}", (CallableStatementCallback<Result>) callableStatement -> {

            Result resultSP = new Result();

            try {

                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.registerOutParameter(3, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(2);
                if (resultSetUsuario.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setUserName(resultSetUsuario.getString("UserName"));
                    usuario.setNombre(resultSetUsuario.getString("Nombre"));
                    usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuario.setPassword(resultSetUsuario.getString("Password"));
                    usuario.setFechaNacimiento(resultSetUsuario.getDate("FechaNacimiento"));
                    usuario.setSexo(resultSetUsuario.getString("Sexo"));
                    usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuario.setCelular(resultSetUsuario.getString("Celular"));
                    usuario.setCurp(resultSetUsuario.getString("Curp"));
                    usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuario.Rol = new Rol();
                    usuario.Rol.setIdRol(resultSetUsuario.getInt("IdRol"));
                    usuario.Rol.setNombreRol(resultSetUsuario.getString("NombreRol"));

                    ResultSet resultSetDirecciones = (ResultSet) callableStatement.getObject(3);
                    usuario.Direcciones = new ArrayList<>();

                    while (resultSetDirecciones.next()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSetDirecciones.getInt("IdDireccion"));
                        direccion.setCalle(resultSetDirecciones.getString("Calle"));
                        direccion.setNumeroInterior(resultSetDirecciones.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSetDirecciones.getString("NumeroExterior"));

                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSetDirecciones.getInt("IdColonia"));
                        direccion.Colonia.setNombreColonia(resultSetDirecciones.getString("NombreColonia"));
                        usuario.Direcciones.add(direccion);
                    }
                    resultSP.object = usuario;
                    resultSP.correct = true;
                }

            } catch (Exception ex) {
                resultSP.correct = false;
                resultSP.errorMessage = ex.getLocalizedMessage();
                resultSP.ex = ex;
            }

            return resultSP;

        });

        return result;
    }

    @Override
    public Result Add(Usuario usuario) {

        Result result = new Result();

        result.correct = jdbcTemplate.execute("CALL UsuariosDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Boolean>) callableStatement -> {

            callableStatement.setString(1, usuario.getUserName());
            callableStatement.setString(2, usuario.getNombre());
            callableStatement.setString(3, usuario.getApellidoPaterno());
            callableStatement.setString(4, usuario.getApellidoMaterno());
            callableStatement.setString(5, usuario.getEmail());
            callableStatement.setString(6, usuario.getPassword());
            callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
            callableStatement.setString(8, usuario.getSexo());
            callableStatement.setString(9, usuario.getTelefono());
            callableStatement.setString(10, usuario.getCelular());
            callableStatement.setString(11, usuario.getCurp());
            callableStatement.setString(12, usuario.getImagen());
            callableStatement.setInt(13, usuario.Rol.getIdRol());
            callableStatement.setString(14, usuario.Direcciones.get(0).getCalle());
            callableStatement.setString(15, usuario.Direcciones.get(0).getNumeroInterior());
            callableStatement.setString(16, usuario.Direcciones.get(0).getNumeroExterior());
            callableStatement.setInt(17, usuario.Direcciones.get(0).Colonia.getIdColonia());

            callableStatement.execute();

            return true;

        });

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Usuario> usuarios) {
        Result result = new Result();
        try {
            jdbcTemplate.batchUpdate("{CALL UsuarioAdd(?,?,?,?,?,?,?,?,?,?,?,?)}",
                    usuarios,
                    usuarios.size(),
                    (callableStatement, usuario) -> {
                        callableStatement.setString(1, usuario.getUserName());
                        callableStatement.setString(2, usuario.getNombre());
                        callableStatement.setString(3, usuario.getApellidoPaterno());
                        callableStatement.setString(4, usuario.getApellidoMaterno());
                        callableStatement.setString(5, usuario.getEmail());
                        callableStatement.setString(6, usuario.getPassword());
                        callableStatement.setDate(7, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                        callableStatement.setString(8, usuario.getSexo());
                        callableStatement.setString(9, usuario.getTelefono());
                        callableStatement.setString(10, usuario.getCelular());
                        callableStatement.setString(11, usuario.getCurp());
                        callableStatement.setInt(12, usuario.Rol.getIdRol());
                    });
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;            
        }
        return result;
    }

    @Override
    public Result Update(Usuario usuario) {
        return jdbcTemplate.execute("CALL UsuarioUpdate(?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {

                callableStatement.setString(1, usuario.getUserName());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getEmail());
                callableStatement.setDate(6, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setString(7, usuario.getSexo());
                callableStatement.setString(8, usuario.getTelefono());
                callableStatement.setString(9, usuario.getCelular());
                callableStatement.setString(10, usuario.getCurp());
                callableStatement.setInt(11, usuario.Rol.getIdRol());
                callableStatement.setInt(12, usuario.getIdUsuario());

                int RowAffected = callableStatement.executeUpdate();

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
    public Result UsuariosBuscar(Usuario usuario) {
        return jdbcTemplate.execute("CALL UsuarioGetAllDinamico (?,?,?,?,?)", (CallableStatementCallback<Result>) callableStatement -> {
            Result result = new Result();

            try {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                result.objects = new ArrayList<>();
                int IdUsuario = 0;

                while (resultSet.next()) {
                    IdUsuario = resultSet.getInt("IdUsuario");

                    if (!result.objects.isEmpty() && IdUsuario == ((Usuario) (result.objects.get(result.objects.size() - 1))).getIdUsuario()) {

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombreColonia(resultSet.getString("Nombrecolonia"));
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

                        ((Usuario) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        Usuario usuario2 = new Usuario();
                        usuario2.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuario2.setUserName(resultSet.getString("UserName"));
                        usuario2.setNombre(resultSet.getString("NombreUsuario"));
                        usuario2.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuario2.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuario2.setEmail(resultSet.getString("Email"));
                        usuario2.setPassword(resultSet.getString("password"));
                        usuario2.setFechaNacimiento(resultSet.getDate("FechaNacimiento"));
                        usuario2.setSexo(resultSet.getString("Sexo"));
                        usuario2.setTelefono(resultSet.getString("Telefono"));
                        usuario2.setCelular(resultSet.getString("Celular"));
                        usuario2.setCurp(resultSet.getString("Curp"));
                        usuario2.setImagen(resultSet.getString("ImagenFile"));

                        usuario2.Rol = new Rol();
                        usuario2.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuario2.Rol.setNombreRol(resultSet.getString("NombreRol"));

                        usuario2.Direcciones = new ArrayList<>();

                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));

                        Colonia colonia = new Colonia();
                        colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        colonia.setNombreColonia(resultSet.getString("Nombrecolonia"));
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

                        usuario2.Direcciones.add(direccion);
                        result.objects.add(usuario2);
                    }
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
