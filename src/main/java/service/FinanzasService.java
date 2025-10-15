package service;

import dto.FinanzaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Finanzas;
import repository.FinanzasRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FinanzasService {

  @Inject
  FinanzasRepository finanzasRepository;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public List<FinanzaDTO> findAll() {
    return finanzasRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<FinanzaDTO> findById(Long id) {
    return finanzasRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public FinanzaDTO create(FinanzaDTO dto) {
    Finanzas finanza = toEntity(dto);
    finanzasRepository.persist(finanza);
    return toDTO(finanza);
  }

  public Optional<FinanzaDTO> update(Long id, FinanzaDTO dto) {
    Optional<Finanzas> existing = finanzasRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Finanzas finanza = existing.get();
    finanza.setGanancias(dto.getGanancias());
    finanza.setPerdidas(dto.getPerdidas());

    if (dto.getMes() != null) {
      try {
        Date mes = dateFormat.parse(dto.getMes());
        finanza.setMes(mes);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return Optional.of(toDTO(finanza));
  }

  public boolean delete(Long id) {
    return finanzasRepository.deleteById(id);
  }

  private FinanzaDTO toDTO(Finanzas finanza) {
    FinanzaDTO dto = new FinanzaDTO();
    dto.setId_finanzas(finanza.getIdFinanzas());
    dto.setGanancias(finanza.getGanancias());
    dto.setPerdidas(finanza.getPerdidas());
    if (finanza.getMes() != null) {
      dto.setMes(dateFormat.format(finanza.getMes()));
    }
    return dto;
  }

  private Finanzas toEntity(FinanzaDTO dto) {
    Finanzas finanza = new Finanzas();
    finanza.setGanancias(dto.getGanancias());
    finanza.setPerdidas(dto.getPerdidas());

    if (dto.getMes() != null) {
      try {
        Date mes = dateFormat.parse(dto.getMes());
        finanza.setMes(mes);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return finanza;
  }
}
