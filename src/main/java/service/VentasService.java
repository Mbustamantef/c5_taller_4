package service;

import dto.VentaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Ventas;
import repository.VentasRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class VentasService {

  @Inject
  VentasRepository ventasRepository;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public List<VentaDTO> findAll() {
    return ventasRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<VentaDTO> findById(Long id) {
    return ventasRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public VentaDTO create(VentaDTO dto) {
    Ventas venta = toEntity(dto);
    ventasRepository.persist(venta);
    return toDTO(venta);
  }

  public Optional<VentaDTO> update(Long id, VentaDTO dto) {
    Optional<Ventas> existing = ventasRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Ventas venta = existing.get();
    venta.setMonto(dto.getMonto());
    venta.setCantidad(dto.getCantidad());

    if (dto.getMes() != null) {
      try {
        Date mes = dateFormat.parse(dto.getMes());
        venta.setMes(mes);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return Optional.of(toDTO(venta));
  }

  public boolean delete(Long id) {
    return ventasRepository.deleteById(id);
  }

  private VentaDTO toDTO(Ventas venta) {
    VentaDTO dto = new VentaDTO();
    dto.setId_ventas(venta.getIdVentas());
    dto.setMonto(venta.getMonto());
    dto.setCantidad(venta.getCantidad());
    if (venta.getMes() != null) {
      dto.setMes(dateFormat.format(venta.getMes()));
    }
    return dto;
  }

  private Ventas toEntity(VentaDTO dto) {
    Ventas venta = new Ventas();
    venta.setMonto(dto.getMonto());
    venta.setCantidad(dto.getCantidad());

    if (dto.getMes() != null) {
      try {
        Date mes = dateFormat.parse(dto.getMes());
        venta.setMes(mes);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return venta;
  }
}

