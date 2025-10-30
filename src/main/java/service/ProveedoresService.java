package service;

import dto.ProveedorDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Proveedores;
import repository.ProveedoresRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProveedoresService {

  @Inject
  ProveedoresRepository proveedoresRepository;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public List<ProveedorDTO> findAll() {
    return proveedoresRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<ProveedorDTO> findById(Long id) {
    return proveedoresRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public ProveedorDTO create(ProveedorDTO dto) {
    Proveedores proveedor = toEntity(dto);
    proveedoresRepository.persist(proveedor);
    return toDTO(proveedor);
  }

  public Optional<ProveedorDTO> update(Long id, ProveedorDTO dto) {
    Optional<Proveedores> existing = proveedoresRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Proveedores proveedor = existing.get();
    proveedor.setTitulo(dto.getTitulo());
    proveedor.setNombreProveedor(dto.getNombreProveedor());
    proveedor.setCosto(dto.getCosto());
    proveedor.setCantidad(dto.getCantidad());
    proveedor.setMoneda(dto.getMoneda());

    if (dto.getMes_compra() != null) {
      try {
        Date mesCompra = dateFormat.parse(dto.getMes_compra());
        proveedor.setMesCompra(mesCompra);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return Optional.of(toDTO(proveedor));
  }

  public boolean delete(Long id) {
    return proveedoresRepository.deleteById(id);
  }

  private ProveedorDTO toDTO(Proveedores proveedor) {
    ProveedorDTO dto = new ProveedorDTO();
    dto.setId_proveedor(proveedor.getIdProveedor());
    dto.setTitulo(proveedor.getTitulo());
    dto.setNombreProveedor(proveedor.getNombreProveedor());
    dto.setCosto(proveedor.getCosto());
    dto.setCantidad(proveedor.getCantidad());
    dto.setMoneda(proveedor.getMoneda());
    if (proveedor.getMesCompra() != null) {
      dto.setMes_compra(dateFormat.format(proveedor.getMesCompra()));
    }
    return dto;
  }

  private Proveedores toEntity(ProveedorDTO dto) {
    Proveedores proveedor = new Proveedores();
    proveedor.setTitulo(dto.getTitulo());
    proveedor.setNombreProveedor(dto.getNombreProveedor());
    proveedor.setCosto(dto.getCosto());
    proveedor.setCantidad(dto.getCantidad());
    proveedor.setMoneda(dto.getMoneda());

    if (dto.getMes_compra() != null) {
      try {
        Date mesCompra = dateFormat.parse(dto.getMes_compra());
        proveedor.setMesCompra(mesCompra);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return proveedor;
  }
}
