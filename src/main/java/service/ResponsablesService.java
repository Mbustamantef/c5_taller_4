package service;

import dto.ResponsableDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import models.Depositos;
import models.Responsables;
import models.Roles;
import repository.DepositoRepository;
import repository.ResponsablesRepository;
import repository.RolesRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResponsablesService {

  @Inject
  ResponsablesRepository responsablesRepository;

  @Inject
  DepositoRepository depositoRepository;

  @Inject
  RolesRepository rolesRepository;

  public List<ResponsableDTO> findAll() {
    return responsablesRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<ResponsableDTO> findById(Long id) {
    return responsablesRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public ResponsableDTO create(ResponsableDTO dto) {
    // Validar que el depósito exista
    if (dto.getId_deposito() == null) {
      throw new WebApplicationException("El depósito es obligatorio", Response.Status.BAD_REQUEST);
    }
    Depositos deposito = depositoRepository.findById(dto.getId_deposito());
    if (deposito == null) {
      throw new WebApplicationException("El depósito con ID " + dto.getId_deposito() + " no existe", Response.Status.NOT_FOUND);
    }

    // Validar que el rol exista
    if (dto.getRol() == null) {
      throw new WebApplicationException("El rol es obligatorio", Response.Status.BAD_REQUEST);
    }
    Roles rol = rolesRepository.findById(dto.getRol());
    if (rol == null) {
      throw new WebApplicationException("El rol con ID " + dto.getRol() + " no existe", Response.Status.NOT_FOUND);
    }

    Responsables responsable = toEntity(dto);
    responsablesRepository.persist(responsable);
    return toDTO(responsable);
  }

  public Optional<ResponsableDTO> update(Long id, ResponsableDTO dto) {
    Optional<Responsables> existing = responsablesRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Responsables responsable = existing.get();
    responsable.setNombre(dto.getNombre());
    responsable.setApellido(dto.getApellido());

    // Validar y actualizar depósito
    if (dto.getId_deposito() != null) {
      Depositos deposito = depositoRepository.findById(dto.getId_deposito());
      if (deposito == null) {
        throw new WebApplicationException("El depósito con ID " + dto.getId_deposito() + " no existe", Response.Status.NOT_FOUND);
      }
      responsable.setDeposito(deposito);
    } else {
      responsable.setDeposito(null);
    }

    // Validar y actualizar rol
    if (dto.getRol() != null) {
      Roles rol = rolesRepository.findById(dto.getRol());
      if (rol == null) {
        throw new WebApplicationException("El rol con ID " + dto.getRol() + " no existe", Response.Status.NOT_FOUND);
      }
      responsable.setRol(rol);
    } else {
      responsable.setRol(null);
    }

    return Optional.of(toDTO(responsable));
  }

  public boolean delete(Long id) {
    return responsablesRepository.deleteById(id);
  }

  private ResponsableDTO toDTO(Responsables responsable) {
    ResponsableDTO dto = new ResponsableDTO();
    dto.setId_responsables(responsable.getIdResponsables());
    dto.setNombre(responsable.getNombre());
    dto.setApellido(responsable.getApellido());
    if (responsable.getDeposito() != null) {
      dto.setId_deposito(responsable.getDeposito().getIdDeposito());
    }
    if (responsable.getRol() != null) {
      dto.setRol(responsable.getRol().getIdRol());
    }
    return dto;
  }

  private Responsables toEntity(ResponsableDTO dto) {
    Responsables responsable = new Responsables();
    responsable.setNombre(dto.getNombre());
    responsable.setApellido(dto.getApellido());

    if (dto.getId_deposito() != null) {
      Depositos deposito = depositoRepository.findById(dto.getId_deposito());
      responsable.setDeposito(deposito);
    }

    if (dto.getRol() != null) {
      Roles rol = rolesRepository.findById(dto.getRol());
      responsable.setRol(rol);
    }

    return responsable;
  }
}

