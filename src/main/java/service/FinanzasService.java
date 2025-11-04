package service;

import dto.FinanzaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Finanzas;
import models.Productos;
import models.Ventas;
import repository.FinanzasRepository;
import repository.VentasRepository;
import repository.ProductosRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class FinanzasService {

  @Inject
  FinanzasRepository finanzasRepository;

  @Inject
  VentasRepository ventasRepository;

  @Inject
  ProductosRepository productosRepository;

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

  /**
   * Calcula las finanzas para un mes y año específico
   * Ganancias = suma de ventas (cantidad * precio_venta de cada producto vendido)
   * Pérdidas = suma de productos comprados ese mes (cantidad * precio_costo de cada producto)
   */
  public FinanzaDTO calcularFinanzasPorMesYAnio(int mes, int anio) {
    // Calcular ganancias desde las ventas del mes
    List<Ventas> ventas = ventasRepository.findByMesAndAnio(mes, anio);
    double ganancias = calcularGanancias(ventas);

    // Calcular pérdidas desde los productos comprados en ese mes
    List<Productos> productosComprados = productosRepository.findByMesCompra(mes, anio);
    double perdidas = calcularPerdidas(productosComprados);

    // Crear o actualizar el registro de finanzas
    Calendar cal = Calendar.getInstance();
    cal.set(anio, mes - 1, 1, 0, 0, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date fechaMes = cal.getTime();

    Optional<Finanzas> existente = finanzasRepository.findByMes(fechaMes);
    Finanzas finanza;

    if (existente.isPresent()) {
      finanza = existente.get();
      finanza.setGanancias(ganancias);
      finanza.setPerdidas(perdidas);
    } else {
      finanza = new Finanzas();
      finanza.setMes(fechaMes);
      finanza.setGanancias(ganancias);
      finanza.setPerdidas(perdidas);
      finanzasRepository.persist(finanza);
    }

    return toDTO(finanza);
  }

  private double calcularGanancias(List<Ventas> ventas) {
    double total = 0.0;
    for (Ventas venta : ventas) {
      // Sumar el monto total de cada venta
      // El monto ya debería representar cantidad * precio_venta
      if (venta.getMonto() != null) {
        total += venta.getMonto();
      }
    }
    return total;
  }

  private double calcularPerdidas(List<Productos> productos) {
    double total = 0.0;
    for (Productos producto : productos) {
      if (producto.getCantidad() != null && producto.getPrecioCosto() != null) {
        // Pérdidas = cantidad comprada * precio_costo
        total += producto.getCantidad() * producto.getPrecioCosto();
      }
    }
    return total;
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
