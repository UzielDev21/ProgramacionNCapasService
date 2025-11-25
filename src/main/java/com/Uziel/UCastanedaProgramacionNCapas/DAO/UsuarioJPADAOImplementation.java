package com.Uziel.UCastanedaProgramacionNCapas.DAO;

import com.Uziel.UCastanedaProgramacionNCapas.JPA.DireccionJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.ColoniaJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.RolJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.UsuarioJPA;
import com.Uziel.UCastanedaProgramacionNCapas.JPA.Result;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioJPADAOImplementation implements IUsuarioJPA {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Result GetAllJPA() {
        Result result = new Result();

        try {
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery("FROM UsuarioJPA", UsuarioJPA.class);
            List<UsuarioJPA> usuariosJPA = queryUsuario.getResultList();

//            result.object = usuariosJPA;
            result.objects = (List<Object>) (List<?>) usuariosJPA;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetByIdJPA(int IdUsuario) {
        Result result = new Result();

        try {

            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, IdUsuario);

            result.object = usuarioJPA;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateJPA(UsuarioJPA usuario) {
        Result result = new Result();

        try {

            UsuarioJPA usuarioPutJPA = entityManager.find(UsuarioJPA.class, usuario.getIdUsuario());
//            
            if (usuarioPutJPA == null) {
                if (usuario.DireccionesJPA != null || usuario.DireccionesJPA.isEmpty()) {
                    entityManager.persist(usuario);
                    result.correct = true;
                    result.status = 200;
                } else {
                    usuario.DireccionesJPA.get(0).UsuarioJPA = usuario;
                    entityManager.persist(usuario);
                    result.correct = true;
                    result.status = 200;
                }
            } else {
                usuario.setPassword(usuarioPutJPA.getPassword());
                usuario.setImagen(usuarioPutJPA.getImagen());
                usuario.setDireccionesJPA(usuarioPutJPA.getDireccionesJPA());
                entityManager.merge(usuario);
                result.correct = true;
                result.status = 200;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result UpdateImagenJPA(int IdUsuario, String imagenBase64) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioBase = entityManager.find(UsuarioJPA.class, IdUsuario);

            usuarioBase.setImagen(imagenBase64);

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result AddJPA(UsuarioJPA usuarioJPA) {
        Result result = new Result();

        try {
            TypedQuery<UsuarioJPA> queryUsuario = entityManager.createQuery(
                    "FROM UsuarioJPA usuarioJPA WHERE usuarioJPA.UserName = :username", UsuarioJPA.class).setParameter("username", usuarioJPA.getUserName());

            List<UsuarioJPA> usuarios = queryUsuario.getResultList();

            if (!usuarios.isEmpty()) {
                throw new EntityExistsException("El UserName " + usuarioJPA.getUserName() + " ya existe en la base de datos");
            }

            if (usuarioJPA.DireccionesJPA != null && !usuarioJPA.DireccionesJPA.isEmpty()) {

                usuarioJPA.DireccionesJPA.forEach(direccion -> direccion.UsuarioJPA = usuarioJPA);
            }

            entityManager.persist(usuarioJPA);
            result.correct = true;
            result.status = 201;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional
    public Result DeleteJPA(int IdUsuario) {
        Result result = new Result();

        try {
            UsuarioJPA usuarioJPA = entityManager.find(UsuarioJPA.class, IdUsuario);
            entityManager.remove(usuarioJPA);

            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result AddAllJPA(List<UsuarioJPA> usuariosJPA) {
        Result result = new Result();

        try {

//            for (Usuario usuario : usuarios) {
//                
//                UsuarioJPA usuarioJPA = modelMapper.map(usuario, UsuarioJPA.class);
//                usuarioJPA.RolJPA = modelMapper.map(usuario.Rol, RolJPA.class);
//                entityManager.persist(usuarioJPA);
//                
//            }
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result BuscarUsuarioJPA(UsuarioJPA usuario) {
        Result result = new Result();

        try {
            String queryValidar = "FROM UsuarioJPA usuarioJPA";
            boolean filtro = false;

            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                if (!filtro) {
                    queryValidar = queryValidar + " WHERE LOWER(usuarioJPA.Nombre) LIKE :nombre";
                    filtro = true;
                } else {
                    queryValidar = queryValidar + " AND LOWER(usuarioJPA.Nombre) LIKE :nombre";
                }
            }

            if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) {
                if (!filtro) {
                    queryValidar = queryValidar + " WHERE LOWER(usuarioJPA.ApellidoPaterno) LIKE :apellidoPaterno";
                    filtro = true;
                } else {
                    queryValidar = queryValidar + " AND LOWER(usuarioJPA.ApellidoPaterno) LIKE :apellidoPaterno";
                }
            }

            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) {
                if (!filtro) {
                    queryValidar = queryValidar + " WHERE LOWER(usuarioJPA.ApellidoMaterno) LIKE :apellidoMaterno";
                    filtro = true;
                } else {
                    queryValidar = queryValidar + " AND LOWER(usuarioJPA.ApellidoMaterno) LIKE :apellidoMaterno";
                }
            }

            if (usuario.RolJPA != null && usuario.RolJPA.getIdRol() > 0) {
                if (!filtro) {
                    queryValidar = queryValidar + " WHERE usuarioJPA.RolJPA.IdRol = :idRol";
                    filtro = true;
                } else {
                    queryValidar = queryValidar + " AND usuarioJPA.RolJPA.IdRol = :idRol";
                }
            }

            queryValidar = queryValidar + " ORDER BY usuarioJPA.IdUsuario";

            TypedQuery<UsuarioJPA> queryBuscar = entityManager.createQuery(queryValidar, UsuarioJPA.class);

            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                queryBuscar.setParameter("nombre", "%" + usuario.getNombre() + "%");
            }

            if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) {
                queryBuscar.setParameter("apellidoPaterno", "%" + usuario.getApellidoPaterno() + "%");
            }

            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) {
                queryBuscar.setParameter("apellidoMaterno", "%" + usuario.getApellidoMaterno() + "%");
            }
            if (usuario.RolJPA != null && usuario.RolJPA.getIdRol() > 0) {
                queryBuscar.setParameter("idRol", usuario.RolJPA.getIdRol());
            }

            List<UsuarioJPA> usuariosJPA = queryBuscar.getResultList();
            
            
            result.objects = (List<Object>) (List<?>) usuariosJPA;
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
