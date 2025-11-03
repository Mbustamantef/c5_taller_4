package service;

import dto.MovimientoInventarioDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Depositos;
import models.MovimientosInventario;
import models.Productos;
import models.Usuarios;
import repository.DepositoRepository;
import repository.MovimientosInventarioRepository;
import repository.ProductosRepository;
import repository.UsuariosRepository;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class MovimientosInventarioService {

  @Inject
  MovimientosInventarioRepository movimientosRepository;

  @Inject
  ProductosRepository productosRepository;

  @Inject
  DepositoRepository depositoRepository;

  @Inject
  UsuariosRepository usuariosRepository;

  @Inject
  StockService stockService;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public List<MovimientoInventarioDTO> findAll() {
    return movimientosRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<MovimientoInventarioDTO> findById(Long id) {
    return movimientosRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public List<MovimientoInventarioDTO> findByProducto(Long idProducto) {
    return movimientosRepository.findByProductoId(idProducto).stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public List<MovimientoInventarioDTO> findByTipo(String tipo) {
    return movimientosRepository.findByTipoMovimiento(tipo).stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public MovimientoInventarioDTO create(MovimientoInventarioDTO dto) {
    // Validar producto
    Productos producto = productosRepository.findByIdOptional(dto.getIdProducto())
        .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + dto.getIdProducto()));

    MovimientosInventario movimiento = new MovimientosInventario();
    movimiento.setProducto(producto);
    movimiento.setCantidad(dto.getCantidad());
    movimiento.setMotivo(dto.getMotivo());
    movimiento.setObservaciones(dto.getObservaciones());

    // Establecer fecha actual de Paraguay
    ZonedDateTime fechaParaguay = ZonedDateTime.now(ZoneId.of("America/Asuncion"));
    movimiento.setFechaMovimiento(Date.from(fechaParaguay.toInstant()));

    // Validar y establecer tipo de movimiento
    try {
      MovimientosInventario.TipoMovimiento tipo = MovimientosInventario.TipoMovimiento.valueOf(dto.getTipoMovimiento().toUpperCase());
      movimiento.setTipoMovimiento(tipo);

      // Procesar según tipo de movimiento
      switch (tipo) {
        case ENTRADA:
          procesarEntrada(movimiento, dto);
          break;
        case SALIDA:
          procesarSalida(movimiento, dto);
          break;
        case TRANSFERENCIA:
          procesarTransferencia(movimiento, dto);
          break;
        case AJUSTE:
          procesarAjuste(movimiento, dto);
          break;
      }
    } catch (IllegalArgumentException e) {
      throw new RuntimeException("Tipo de movimiento inválido. Use: ENTRADA, SALIDA, TRANSFERENCIA, AJUSTE");
    }

    // Asignar usuario si se proporciona
    if (dto.getIdUsuario() != null) {
      Usuarios usuario = usuariosRepository.findByIdOptional(dto.getIdUsuario())
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + dto.getIdUsuario()));
      movimiento.setUsuario(usuario);
    }

    movimientosRepository.persist(movimiento);
    return toDTO(movimiento);
  }

  private void procesarEntrada(MovimientosInventario movimiento, MovimientoInventarioDTO dto) {
    if (dto.getIdDepositoDestino() == null) {
      throw new RuntimeException("Se requiere depósito destino para ENTRADA");
    }
    Depositos deposito = depositoRepository.findByIdOptional(dto.getIdDepositoDestino())
        .orElseThrow(() -> new RuntimeException("Depósito destino no encontrado"));
    movimiento.setDepositoDestino(deposito);

    // Incrementar stock
    stockService.ajustarStock(dto.getIdProducto(), dto.getIdDepositoDestino(), dto.getCantidad());
  }

  private void procesarSalida(MovimientosInventario movimiento, MovimientoInventarioDTO dto) {
    if (dto.getIdDepositoOrigen() == null) {
      throw new RuntimeException("Se requiere depósito origen para SALIDA");
    }
    Depositos deposito = depositoRepository.findByIdOptional(dto.getIdDepositoOrigen())
        .orElseThrow(() -> new RuntimeException("Depósito origen no encontrado"));
    movimiento.setDepositoOrigen(deposito);

    // Decrementar stock
    stockService.ajustarStock(dto.getIdProducto(), dto.getIdDepositoOrigen(), -dto.getCantidad());
  }

  private void procesarTransferencia(MovimientosInventario movimiento, MovimientoInventarioDTO dto) {
    if (dto.getIdDepositoOrigen() == null || dto.getIdDepositoDestino() == null) {
      throw new RuntimeException("Se requieren depósito origen y destino para TRANSFERENCIA");
    }
    if (dto.getIdDepositoOrigen().equals(dto.getIdDepositoDestino())) {
      throw new RuntimeException("El depósito origen y destino no pueden ser iguales");
    }

    Depositos depositoOrigen = depositoRepository.findByIdOptional(dto.getIdDepositoOrigen())
        .orElseThrow(() -> new RuntimeException("Depósito origen no encontrado"));
    Depositos depositoDestino = depositoRepository.findByIdOptional(dto.getIdDepositoDestino())
        .orElseThrow(() -> new RuntimeException("Depósito destino no encontrado"));

    movimiento.setDepositoOrigen(depositoOrigen);
    movimiento.setDepositoDestino(depositoDestino);

    // Decrementar del origen e incrementar en destino
    stockService.ajustarStock(dto.getIdProducto(), dto.getIdDepositoOrigen(), -dto.getCantidad());
    stockService.ajustarStock(dto.getIdProducto(), dto.getIdDepositoDestino(), dto.getCantidad());
  }

  private void procesarAjuste(MovimientosInventario movimiento, MovimientoInventarioDTO dto) {
    if (dto.getIdDepositoDestino() == null) {
      throw new RuntimeException("Se requiere depósito para AJUSTE");
    }
    Depositos deposito = depositoRepository.findByIdOptional(dto.getIdDepositoDestino())
        .orElseThrow(() -> new RuntimeException("Depósito no encontrado"));
    movimiento.setDepositoDestino(deposito);

    // Puede ser positivo o negativo
    // Para ajuste negativo, usar cantidad negativa en el DTO
    stockService.ajustarStock(dto.getIdProducto(), dto.getIdDepositoDestino(), dto.getCantidad());
  }

  public boolean delete(Long id) {
    return movimientosRepository.deleteById(id);
  }

  private MovimientoInventarioDTO toDTO(MovimientosInventario movimiento) {
    MovimientoInventarioDTO dto = new MovimientoInventarioDTO();
    dto.setIdMovimiento(movimiento.getIdMovimiento());
    dto.setCantidad(movimiento.getCantidad());
    dto.setMotivo(movimiento.getMotivo());
    dto.setObservaciones(movimiento.getObservaciones());
    dto.setTipoMovimiento(movimiento.getTipoMovimiento().toString());

    if (movimiento.getFechaMovimiento() != null) {
      dto.setFechaMovimiento(dateFormat.format(movimiento.getFechaMovimiento()));
    }

    if (movimiento.getProducto() != null) {
      dto.setIdProducto(movimiento.getProducto().getIdProducto());
      dto.setNombreProducto(movimiento.getProducto().getTitulo());
    }

    if (movimiento.getDepositoOrigen() != null) {
      dto.setIdDepositoOrigen(movimiento.getDepositoOrigen().getIdDeposito());
      dto.setNombreDepositoOrigen(movimiento.getDepositoOrigen().getNombreDeposito());
    }

    if (movimiento.getDepositoDestino() != null) {
      dto.setIdDepositoDestino(movimiento.getDepositoDestino().getIdDeposito());
      dto.setNombreDepositoDestino(movimiento.getDepositoDestino().getNombreDeposito());
    }

    if (movimiento.getUsuario() != null) {
      dto.setIdUsuario(movimiento.getUsuario().getIdUsuario());
      dto.setNombreUsuario(movimiento.getUsuario().getNombreUsuario());
    }

    return dto;
  }
}

