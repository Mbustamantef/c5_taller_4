package service;

import dto.StockDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Depositos;
import models.Productos;
import models.Stock;
import repository.DepositoRepository;
import repository.ProductosRepository;
import repository.StockRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class StockService {

  @Inject
  StockRepository stockRepository;

  @Inject
  ProductosRepository productosRepository;

  @Inject
  DepositoRepository depositoRepository;

  public List<StockDTO> findAll() {
    // Opción 1: Obtener desde tabla stock (registros manuales)
    List<StockDTO> stockManual = stockRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());

    // Opción 2: Calcular desde movimientos (stock real)
    List<Object[]> stockReal = stockRepository.getStockRealPorProductoYDeposito();
    List<StockDTO> stockCalculado = stockReal.stream()
        .map(row -> {
          StockDTO dto = new StockDTO();
          dto.setIdProducto(((Number) row[0]).longValue());
          dto.setNombreProducto((String) row[1]);
          dto.setPrecioCosto(row[2] != null ? ((Number) row[2]).floatValue() : 0.0f);
          dto.setPrecioVenta(row[3] != null ? ((Number) row[3]).floatValue() : 0.0f);
          dto.setIdDeposito(((Number) row[4]).longValue());
          dto.setNombreDeposito((String) row[5]);

          Integer cantidad = row[6] != null ? ((Number) row[6]).intValue() : 0;
          dto.setCantidad(cantidad);

          if (dto.getPrecioCosto() != null && cantidad != null) {
            dto.setValorTotal(cantidad * dto.getPrecioCosto());
          }

          return dto;
        })
        .collect(Collectors.toList());

    return stockCalculado;
  }

  public Optional<StockDTO> findById(Long id) {
    return stockRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public List<StockDTO> findByProducto(Long idProducto) {
    // Calcular stock real desde movimientos para un producto específico
    List<Object[]> stockReal = stockRepository.getStockRealPorProductoYDeposito();

    return stockReal.stream()
        .filter(row -> ((Number) row[0]).longValue() == idProducto)
        .map(row -> {
          StockDTO dto = new StockDTO();
          dto.setIdProducto(((Number) row[0]).longValue());
          dto.setNombreProducto((String) row[1]);
          dto.setPrecioCosto(row[2] != null ? ((Number) row[2]).floatValue() : 0.0f);
          dto.setPrecioVenta(row[3] != null ? ((Number) row[3]).floatValue() : 0.0f);
          dto.setIdDeposito(((Number) row[4]).longValue());
          dto.setNombreDeposito((String) row[5]);

          Integer cantidad = row[6] != null ? ((Number) row[6]).intValue() : 0;
          dto.setCantidad(cantidad);

          if (dto.getPrecioCosto() != null && cantidad != null) {
            dto.setValorTotal(cantidad * dto.getPrecioCosto());
          }

          return dto;
        })
        .collect(Collectors.toList());
  }

  public List<StockDTO> findByDeposito(Long idDeposito) {
    // Calcular stock real desde movimientos para un depósito específico
    List<Object[]> stockReal = stockRepository.getStockRealPorProductoYDeposito();

    return stockReal.stream()
        .filter(row -> ((Number) row[4]).longValue() == idDeposito)
        .map(row -> {
          StockDTO dto = new StockDTO();
          dto.setIdProducto(((Number) row[0]).longValue());
          dto.setNombreProducto((String) row[1]);
          dto.setPrecioCosto(row[2] != null ? ((Number) row[2]).floatValue() : 0.0f);
          dto.setPrecioVenta(row[3] != null ? ((Number) row[3]).floatValue() : 0.0f);
          dto.setIdDeposito(((Number) row[4]).longValue());
          dto.setNombreDeposito((String) row[5]);

          Integer cantidad = row[6] != null ? ((Number) row[6]).intValue() : 0;
          dto.setCantidad(cantidad);

          if (dto.getPrecioCosto() != null && cantidad != null) {
            dto.setValorTotal(cantidad * dto.getPrecioCosto());
          }

          return dto;
        })
        .collect(Collectors.toList());
  }

  public List<StockDTO> findStockBajoMinimo() {
    return stockRepository.findStockBajoMinimo().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public StockDTO create(StockDTO dto) {
    // Verificar si ya existe stock para este producto y depósito
    Stock existing = stockRepository.findByProductoAndDeposito(dto.getIdProducto(), dto.getIdDeposito());
    if (existing != null) {
      throw new RuntimeException("Ya existe stock para este producto en este depósito. Use actualización.");
    }

    Stock stock = new Stock();
    stock.setCantidad(dto.getCantidad());
    stock.setStockMinimo(dto.getStockMinimo());

    // Obtener producto
    Productos producto = productosRepository.findByIdOptional(dto.getIdProducto())
        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getIdProducto()));
    stock.setProducto(producto);

    // Obtener depósito
    Depositos deposito = depositoRepository.findByIdOptional(dto.getIdDeposito())
        .orElseThrow(() -> new RuntimeException("Depósito no encontrado con ID: " + dto.getIdDeposito()));
    stock.setDeposito(deposito);

    stockRepository.persist(stock);
    return toDTO(stock);
  }

  public Optional<StockDTO> update(Long id, StockDTO dto) {
    Optional<Stock> existing = stockRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Stock stock = existing.get();
    stock.setCantidad(dto.getCantidad());
    if (dto.getStockMinimo() != null) {
      stock.setStockMinimo(dto.getStockMinimo());
    }

    return Optional.of(toDTO(stock));
  }

  public boolean delete(Long id) {
    return stockRepository.deleteById(id);
  }

  public void ajustarStock(Long idProducto, Long idDeposito, Integer cantidad) {
    Stock stock = stockRepository.findByProductoAndDeposito(idProducto, idDeposito);
    if (stock == null) {
      // Crear nuevo stock si no existe
      Stock nuevoStock = new Stock();
      nuevoStock.setCantidad(cantidad);

      // Obtener producto
      Productos producto = productosRepository.findByIdOptional(idProducto)
          .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + idProducto));
      nuevoStock.setProducto(producto);

      // Obtener depósito
      Depositos deposito = depositoRepository.findByIdOptional(idDeposito)
          .orElseThrow(() -> new RuntimeException("Depósito no encontrado con ID: " + idDeposito));
      nuevoStock.setDeposito(deposito);

      stockRepository.persist(nuevoStock);
    } else {
      int nuevaCantidad = stock.getCantidad() + cantidad;
      if (nuevaCantidad < 0) {
        throw new RuntimeException("Stock insuficiente. Stock actual: " + stock.getCantidad() + ", intentó reducir: " + Math.abs(cantidad));
      }
      stock.setCantidad(nuevaCantidad);
      // JPA gestiona automáticamente la actualización en la transacción
    }
  }

  private StockDTO toDTO(Stock stock) {
    StockDTO dto = new StockDTO();
    dto.setIdStock(stock.getIdStock());
    dto.setCantidad(stock.getCantidad());
    dto.setStockMinimo(stock.getStockMinimo());

    if (stock.getProducto() != null) {
      dto.setIdProducto(stock.getProducto().getIdProducto());
      dto.setNombreProducto(stock.getProducto().getTitulo());
      dto.setPrecioCosto(stock.getProducto().getPrecioCosto());
      dto.setPrecioVenta(stock.getProducto().getPrecioVenta());
      dto.setValorTotal(stock.getCantidad() * stock.getProducto().getPrecioCosto());
    }

    if (stock.getDeposito() != null) {
      dto.setIdDeposito(stock.getDeposito().getIdDeposito());
      dto.setNombreDeposito(stock.getDeposito().getNombreDeposito());
    }

    return dto;
  }
}

