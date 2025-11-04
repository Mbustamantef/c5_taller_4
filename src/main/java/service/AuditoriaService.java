package service;

import core.exceptions.UnauthorizedException;
import core.generic.Auditable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Usuarios;
import repository.UsuariosRepository;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
public class AuditoriaService {

  @Inject
  UsuariosRepository usuariosRepository;

  private static final String ROL_CREACION = "Creacion";

  /**
   * Valida que el usuario tenga el rol de "Creacion"
   * @param idUsuario ID del usuario a validar
   * @throws UnauthorizedException si el usuario no tiene el rol requerido
   */
  public void validarPermisoCreacion(Long idUsuario) {
    if (idUsuario == null) {
      throw new UnauthorizedException("Se requiere autenticación. Debe proporcionar el ID del usuario.");
    }

    Optional<Usuarios> usuarioOpt = usuariosRepository.findByIdOptional(idUsuario);

    if (usuarioOpt.isEmpty()) {
      throw new UnauthorizedException("Usuario no encontrado con ID: " + idUsuario);
    }

    Usuarios usuario = usuarioOpt.get();

    if (usuario.getRol() == null) {
      throw new UnauthorizedException("El usuario no tiene un rol asignado");
    }

    if (!ROL_CREACION.equalsIgnoreCase(usuario.getRol().getDescripcion())) {
      throw new UnauthorizedException(
          "No tiene permisos para realizar esta operación. Se requiere el rol '" + ROL_CREACION + "'");
    }
  }

  /**
   * Aplica auditoría de creación a una entidad
   */
  public void aplicarAuditoriaCreacion(Auditable entidad, Long idUsuario) {
    validarPermisoCreacion(idUsuario);

    Optional<Usuarios> usuario = usuariosRepository.findByIdOptional(idUsuario);
    usuario.ifPresent(u -> {
      entidad.setCreadoPor(u);
      entidad.setFechaCreacion(new Date());
    });
  }

  /**
   * Aplica auditoría de modificación a una entidad
   */
  public void aplicarAuditoriaModificacion(Auditable entidad, Long idUsuario) {
    validarPermisoCreacion(idUsuario);

    Optional<Usuarios> usuario = usuariosRepository.findByIdOptional(idUsuario);
    usuario.ifPresent(u -> {
      entidad.setModificadoPor(u);
      entidad.setFechaModificacion(new Date());
    });
  }

  /**
   * Obtiene un usuario por su ID
   */
  public Optional<Usuarios> obtenerUsuario(Long idUsuario) {
    if (idUsuario == null) {
      return Optional.empty();
    }
    return usuariosRepository.findByIdOptional(idUsuario);
  }
}

