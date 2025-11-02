package service;

import dto.UsuariosDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import models.Usuarios;
import repository.UsuariosRepository;

@ApplicationScoped
public class UsuariosService {

  @Inject
  UsuariosRepository usuariosRepository;

  public List<UsuariosDTO> findAll() {
    return usuariosRepository.listAll().stream().map(this::toDTO).collect(Collectors.toList());
  }

  public UsuariosDTO findById(Long id) {
    return toDTO(usuariosRepository.findById(id));
  }
  public UsuariosDTO create(UsuariosDTO dto) {
    Usuarios usuarios = new Usuarios();
    usuarios.setNombreUsuario(dto.getNombreUsuario());
    usuarios.setContrasena(dto.getContrasena());
    usuariosRepository.persist(usuarios);
    return toDTO(usuarios);
  }

  private UsuariosDTO toDTO(Usuarios usuarios) {
    UsuariosDTO dto = new UsuariosDTO();
    dto.setNombreUsuario(usuarios.getNombreUsuario());
    dto.setContrasena(usuarios.getContrasena());
    dto.setId_rol(usuarios.getRol().getIdRol());
    return dto;
  }

}
