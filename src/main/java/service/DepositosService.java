package service;

import dto.DepositoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Depositos;
import repository.DepositoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class DepositosService {

  @Inject
  DepositoRepository depositoRepository;

  public List<DepositoDTO> findAll() {
    return depositoRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<DepositoDTO> findById(Long id) {
    return depositoRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public DepositoDTO create(DepositoDTO dto) {
    Depositos deposito = new Depositos();
    deposito.setNombreDeposito(dto.getNombre_deposito());
    depositoRepository.persist(deposito);
    return toDTO(deposito);
  }

  public Optional<DepositoDTO> update(Long id, DepositoDTO dto) {
    Optional<Depositos> existing = depositoRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Depositos deposito = existing.get();
    deposito.setNombreDeposito(dto.getNombre_deposito());

    return Optional.of(toDTO(deposito));
  }

  public boolean delete(Long id) {
    return depositoRepository.deleteById(id);
  }

  private DepositoDTO toDTO(Depositos deposito) {
    DepositoDTO dto = new DepositoDTO();
    dto.setId_deposito(deposito.getIdDeposito());
    dto.setNombre_deposito(deposito.getNombreDeposito());
    return dto;
  }
}

