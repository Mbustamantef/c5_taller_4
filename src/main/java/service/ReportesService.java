package service;

import dto.ReporteStockValorizadoDTO;
import dto.ReporteVentasDTO;
import dto.StockDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import repository.StockRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReportesService {

  @Inject
  StockRepository stockRepository;

  @Inject
  EntityManager entityManager;

  @Inject
  StockService stockService;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

  /**
   * Stock valorizado por depósito
   */
  public List<ReporteStockValorizadoDTO> getStockValorizadoPorDeposito(Long idDeposito) {
    List<Object[]> results = entityManager.createQuery(
        "SELECT s.deposito.idDeposito, s.deposito.nombreDeposito, " +
        "s.producto.idProducto, s.producto.titulo, s.producto.categoria, " +
        "s.cantidad, s.producto.precioCosto, s.producto.precioVenta " +
        "FROM Stock s " +
        (idDeposito != null ? "WHERE s.deposito.idDeposito = :idDeposito " : "") +
        "ORDER BY s.deposito.nombreDeposito, s.producto.titulo",
        Object[].class)
        .setParameter("idDeposito", idDeposito)
        .getResultList();

    return results.stream().map(row -> {
      ReporteStockValorizadoDTO dto = new ReporteStockValorizadoDTO();
      dto.setIdDeposito((Long) row[0]);
      dto.setNombreDeposito((String) row[1]);
      dto.setIdProducto((Long) row[2]);
      dto.setNombreProducto((String) row[3]);
      dto.setCategoria((String) row[4]);
      dto.setCantidad((Integer) row[5]);
      dto.setPrecioCosto(row[6] != null ? ((Number) row[6]).floatValue() : 0.0f);
      dto.setPrecioVenta(row[7] != null ? ((Number) row[7]).floatValue() : 0.0f);
      dto.setValorTotal(dto.getCantidad() * dto.getPrecioCosto());
      dto.setMargenPotencial(dto.getCantidad() * (dto.getPrecioVenta() - dto.getPrecioCosto()));
      return dto;
    }).collect(Collectors.toList());
  }

  /**
   * Stock valorizado por categoría
   */
  public List<ReporteStockValorizadoDTO> getStockValorizadoPorCategoria(String categoria) {
    List<Object[]> results = entityManager.createQuery(
        "SELECT s.deposito.idDeposito, s.deposito.nombreDeposito, " +
        "s.producto.idProducto, s.producto.titulo, s.producto.categoria, " +
        "s.cantidad, s.producto.precioCosto, s.producto.precioVenta " +
        "FROM Stock s " +
        (categoria != null ? "WHERE s.producto.categoria = :categoria " : "") +
        "ORDER BY s.producto.categoria, s.producto.titulo",
        Object[].class)
        .setParameter("categoria", categoria)
        .getResultList();

    return results.stream().map(row -> {
      ReporteStockValorizadoDTO dto = new ReporteStockValorizadoDTO();
      dto.setIdDeposito((Long) row[0]);
      dto.setNombreDeposito((String) row[1]);
      dto.setIdProducto((Long) row[2]);
      dto.setNombreProducto((String) row[3]);
      dto.setCategoria((String) row[4]);
      dto.setCantidad((Integer) row[5]);
      dto.setPrecioCosto(row[6] != null ? ((Number) row[6]).floatValue() : 0.0f);
      dto.setPrecioVenta(row[7] != null ? ((Number) row[7]).floatValue() : 0.0f);
      dto.setValorTotal(dto.getCantidad() * dto.getPrecioCosto());
      dto.setMargenPotencial(dto.getCantidad() * (dto.getPrecioVenta() - dto.getPrecioCosto()));
      return dto;
    }).collect(Collectors.toList());
  }

  /**
   * Productos con stock por debajo del mínimo
   */
  public List<StockDTO> getProductosStockBajoMinimo() {
    return stockService.findStockBajoMinimo();
  }

  /**
   * Productos sin movimiento en X días
   */
  public List<Map<String, Object>> getProductosSinMovimiento(Integer dias) {
    Date fechaLimite = new Date(System.currentTimeMillis() - (long) dias * 24 * 60 * 60 * 1000);

    List<Object[]> results = entityManager.createQuery(
        "SELECT p.idProducto, p.titulo, p.categoria, " +
        "MAX(m.fechaMovimiento) as ultimoMovimiento " +
        "FROM Productos p " +
        "LEFT JOIN MovimientosInventario m ON m.producto.idProducto = p.idProducto " +
        "GROUP BY p.idProducto, p.titulo, p.categoria " +
        "HAVING MAX(m.fechaMovimiento) < :fechaLimite OR MAX(m.fechaMovimiento) IS NULL " +
        "ORDER BY MAX(m.fechaMovimiento) ASC",
        Object[].class)
        .setParameter("fechaLimite", fechaLimite)
        .getResultList();

    return results.stream().map(row -> Map.of(
        "idProducto", row[0],
        "nombreProducto", row[1],
        "categoria", row[2],
        "ultimoMovimiento", row[3] != null ? row[3] : "Sin movimientos",
        "diasSinMovimiento", row[3] != null ?
            (System.currentTimeMillis() - ((Date) row[3]).getTime()) / (24 * 60 * 60 * 1000) :
            "N/A"
    )).collect(Collectors.toList());
  }

  /**
   * Ventas por cliente
   */
  public List<ReporteVentasDTO> getVentasPorCliente(Long idCliente) {
    String query = "SELECT c.idCliente, CONCAT(c.nombreCliente, ' ', c.apellidoCliente) as nombreCompleto, " +
        "COUNT(v.idVentas), SUM(v.monto), SUM(v.cantidad) " +
        "FROM Clientes c " +
        "JOIN c.venta v " +
        (idCliente != null ? "WHERE c.idCliente = :idCliente " : "") +
        "GROUP BY c.idCliente, c.nombreCliente, c.apellidoCliente " +
        "ORDER BY SUM(v.monto) DESC";

    jakarta.persistence.TypedQuery<Object[]> typedQuery = entityManager.createQuery(query, Object[].class);

    if (idCliente != null) {
      typedQuery.setParameter("idCliente", idCliente);
    }

    List<Object[]> results = typedQuery.getResultList();

    return results.stream().map(row -> {
      ReporteVentasDTO dto = new ReporteVentasDTO();
      dto.setIdCliente((Long) row[0]);
      dto.setNombreCliente((String) row[1]);
      dto.setNumeroVentas((Long) row[2]);
      dto.setMontoTotal(row[3] != null ? ((Number) row[3]).floatValue() : 0.0f);
      dto.setCantidadTotal(row[4] != null ? ((Number) row[4]).intValue() : 0);
      return dto;
    }).collect(Collectors.toList());
  }

  /**
   * Ventas por periodo
   */
  public List<Map<String, Object>> getVentasPorPeriodo(Date fechaInicio, Date fechaFin) {
    List<Object[]> results = entityManager.createQuery(
        "SELECT FUNCTION('TO_CHAR', v.mes, 'YYYY-MM') as periodo, " +
        "COUNT(v.idVentas), SUM(v.monto), SUM(v.cantidad) " +
        "FROM Ventas v " +
        "WHERE v.mes BETWEEN :fechaInicio AND :fechaFin " +
        "GROUP BY FUNCTION('TO_CHAR', v.mes, 'YYYY-MM') " +
        "ORDER BY FUNCTION('TO_CHAR', v.mes, 'YYYY-MM') DESC",
        Object[].class)
        .setParameter("fechaInicio", fechaInicio)
        .setParameter("fechaFin", fechaFin)
        .getResultList();

    return results.stream().map(row -> Map.of(
        "periodo", row[0],
        "numeroVentas", row[1],
        "montoTotal", row[2],
        "cantidadTotal", row[3]
    )).collect(Collectors.toList());
  }

  /**
   * Rotación de inventario (movimientos / stock promedio)
   */
  public List<Map<String, Object>> getRotacionInventario() {
    List<Object[]> results = entityManager.createQuery(
        "SELECT p.idProducto, p.titulo, p.categoria, " +
        "COUNT(m.idMovimiento) as totalMovimientos, " +
        "AVG(CAST(s.cantidad AS float)) as stockPromedio " +
        "FROM Productos p " +
        "LEFT JOIN MovimientosInventario m ON m.producto.idProducto = p.idProducto " +
        "LEFT JOIN Stock s ON s.producto.idProducto = p.idProducto " +
        "GROUP BY p.idProducto, p.titulo, p.categoria " +
        "ORDER BY COUNT(m.idMovimiento) DESC",
        Object[].class)
        .getResultList();

    return results.stream().map(row -> {
      Long totalMovimientos = (Long) row[3];
      Double stockPromedio = row[4] != null ? ((Number) row[4]).doubleValue() : 0.0;
      Double rotacion = stockPromedio > 0 ? totalMovimientos / stockPromedio : 0.0;

      return Map.of(
          "idProducto", row[0],
          "nombreProducto", row[1],
          "categoria", row[2],
          "totalMovimientos", totalMovimientos,
          "stockPromedio", stockPromedio,
          "rotacion", rotacion
      );
    }).collect(Collectors.toList());
  }
}

