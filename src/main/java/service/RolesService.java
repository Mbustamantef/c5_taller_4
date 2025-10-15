package service;

import dto.RolDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Roles;
import repository.RolesRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class RolesService {

  @Inject
  RolesRepository rolesRepository;

  public List<RolDTO> findAll() {
    return rolesRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<RolDTO> findById(Long id) {
    return rolesRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public RolDTO create(RolDTO dto) {
    Roles rol = new Roles();
    rol.setDescripcion(dto.getDescripcion());
    rolesRepository.persist(rol);
    return toDTO(rol);
  }

  public Optional<RolDTO> update(Long id, RolDTO dto) {
    Optional<Roles> existing = rolesRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Roles rol = existing.get();
    rol.setDescripcion(dto.getDescripcion());

    return Optional.of(toDTO(rol));
  }

  public boolean delete(Long id) {
    return rolesRepository.deleteById(id);
  }

  private RolDTO toDTO(Roles rol) {
    RolDTO dto = new RolDTO();
    dto.setId_rol(rol.getIdRol());
    dto.setDescripcion(rol.getDescripcion());
    return dto;
  }
}

