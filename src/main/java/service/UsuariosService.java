package service;

import dto.UsuariosDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import models.Roles;
import models.Usuarios;
import repository.RolesRepository;
import repository.UsuariosRepository;

@ApplicationScoped
public class UsuariosService {

  @Inject
  UsuariosRepository usuariosRepository;

  @Inject
  RolesRepository rolesRepository;

  public List<UsuariosDTO> findAll() {
    return usuariosRepository.listAll().stream().map(this::toDTO).collect(Collectors.toList());
  }

  public UsuariosDTO findById(Long id) {
    return toDTO(usuariosRepository.findById(id));
  }

  public UsuariosDTO create(UsuariosDTO dto) {
    if (dto.getId_rol() == null) {
      throw new WebApplicationException("El id es obligatorio", Response.Status.BAD_REQUEST);
    }

    Optional<Roles> rolOpt = rolesRepository.findByIdOptional(dto.getId_rol());
    if (rolOpt.isEmpty()) {
      throw new WebApplicationException("El rol con ID " + dto.getId_rol() + " no existe", Status.NOT_FOUND);
    }

    if (dto.getContrasena() == null) {
      throw new WebApplicationException("La contraseña es obligatoria", Response.Status.BAD_REQUEST);
    }

    LocalDate today = LocalDate.now();
    String month = String.format("%02d", today.getMonthValue());
    String day = String.format("%02d", today.getDayOfMonth());
    String salt = "taller4" + month + day;
    String toHash = salt + dto.getContrasena() + month;

    String hashed;
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
      StringBuilder sb = new StringBuilder();
      for (byte b : hash) {
        sb.append(String.format("%02x", b));
      }
      hashed = sb.toString();
    } catch (NoSuchAlgorithmException e) {
      throw new WebApplicationException("Error al hashear la contraseña", Response.Status.INTERNAL_SERVER_ERROR);
    }

    Usuarios usuarios = new Usuarios();
    usuarios.setNombreUsuario(dto.getNombreUsuario());
    usuarios.setContrasena(hashed);
    usuarios.setRol(rolOpt.get());

    usuariosRepository.persist(usuarios);
    return toDTO(usuarios);
  }

  private UsuariosDTO toDTO(Usuarios usuarios) {
    UsuariosDTO dto = new UsuariosDTO();
    dto.setNombreUsuario(usuarios.getNombreUsuario());
    dto.setContrasena(usuarios.getContrasena());
    if (usuarios.getRol() != null) {
      dto.setId_rol(usuarios.getRol().getIdRol());
    } else {
      dto.setId_rol(null);
    }
    return dto;
  }

}
