package service;

import dto.ResponsableDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Depositos;
import models.Responsables;
import repository.DepositoRepository;
import repository.ResponsablesRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ResponsablesService {

  @Inject
  ResponsablesRepository responsablesRepository;

  @Inject
  DepositoRepository depositoRepository;

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

    if (dto.getId_deposito() != null) {
      Depositos deposito = depositoRepository.findById(dto.getId_deposito());
      responsable.setDeposito(deposito);
    } else {
      responsable.setDeposito(null);
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

    return responsable;
  }
}

