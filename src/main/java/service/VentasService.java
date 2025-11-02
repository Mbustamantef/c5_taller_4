package service;

import dto.VentaDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Clientes;
import models.Productos;
import models.Ventas;
import repository.ClientesRepository;
import repository.ProductosRepository;
import repository.VentasRepository;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class VentasService {

  @Inject
  VentasRepository ventasRepository;

  @Inject
  ClientesRepository clientesRepository;

  @Inject
  ProductosRepository productosRepository;

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
    // 1. Validar y obtener el producto
    if (dto.getId_producto() == null) {
      throw new RuntimeException("El id_producto es obligatorio");
    }

    Optional<Productos> productoOpt = productosRepository.findByIdOptional(dto.getId_producto());
    if (productoOpt.isEmpty()) {
      throw new RuntimeException("Producto con ID " + dto.getId_producto() + " no encontrado");
    }

    Productos producto = productoOpt.get();

    // 2. Calcular el monto automáticamente (cantidad × precio de venta)
    float montoCalculado = dto.getCantidad() * producto.getPrecioVenta();

    // 3. Obtener fecha actual de Paraguay (zona horaria America/Asuncion)
    ZonedDateTime fechaParaguay = ZonedDateTime.now(ZoneId.of("America/Asuncion"));
    Date fechaVenta = Date.from(fechaParaguay.toInstant());

    // 4. Crear la venta
    Ventas venta = new Ventas();
    venta.setMonto(montoCalculado);
    venta.setCantidad(dto.getCantidad());
    venta.setMes(fechaVenta);

    // 5. Asociar el producto a la venta
    venta.getProductos().add(producto);

    ventasRepository.persist(venta);

    // 6. Manejar el cliente: usar existente o crear nuevo
    Clientes cliente;

    if (dto.getId_cliente() != null) {
      // Caso 1: Cliente ya existe, buscar por ID
      Optional<Clientes> clienteExistente = clientesRepository.findByIdOptional(dto.getId_cliente());
      if (clienteExistente.isEmpty()) {
        throw new RuntimeException("Cliente con ID " + dto.getId_cliente() + " no encontrado");
      }
      cliente = clienteExistente.get();
    } else {
      // Caso 2: Cliente nuevo, validar que se enviaron todos los datos necesarios
      if (dto.getNombre_cliente() == null || dto.getApellido_cliente() == null ||
          dto.getCi_cliente() == null || dto.getCorreo_cliente() == null) {
        throw new RuntimeException("Debe proporcionar id_cliente o todos los datos del nuevo cliente (nombre_cliente, apellido_cliente, ci_cliente, correo_cliente)");
      }

      // Crear nuevo cliente
      cliente = new Clientes();
      cliente.setNombreCliente(dto.getNombre_cliente());
      cliente.setApellidoCliente(dto.getApellido_cliente());
      cliente.setCiCliente(dto.getCi_cliente());
      cliente.setCorreoCliente(dto.getCorreo_cliente());
    }

    // Asociar el cliente a la venta
    cliente.setVenta(venta);
    clientesRepository.persist(cliente);

    // Agregar el cliente a la lista de clientes de la venta
    venta.getClientes().add(cliente);

    return toDTO(venta);
  }

  public Optional<VentaDTO> update(Long id, VentaDTO dto) {
    Optional<Ventas> existing = ventasRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Ventas venta = existing.get();

    // Si se proporciona nuevo id_producto, actualizar el producto y recalcular monto
    if (dto.getId_producto() != null) {
      Optional<Productos> productoOpt = productosRepository.findByIdOptional(dto.getId_producto());
      if (productoOpt.isEmpty()) {
        throw new RuntimeException("Producto con ID " + dto.getId_producto() + " no encontrado");
      }
      Productos producto = productoOpt.get();

      // Actualizar producto en la venta
      venta.getProductos().clear();
      venta.getProductos().add(producto);

      // Recalcular monto
      float montoCalculado = dto.getCantidad() * producto.getPrecioVenta();
      venta.setMonto(montoCalculado);
    } else if (dto.getCantidad() != null && !venta.getProductos().isEmpty()) {
      // Si solo cambia la cantidad, recalcular con el producto actual
      Productos productoActual = venta.getProductos().get(0);
      float montoCalculado = dto.getCantidad() * productoActual.getPrecioVenta();
      venta.setMonto(montoCalculado);
    }

    venta.setCantidad(dto.getCantidad());

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

    // Incluir información del cliente si existe
    if (!venta.getClientes().isEmpty()) {
      Clientes cliente = venta.getClientes().get(0);
      dto.setId_cliente(cliente.getIdCliente());
      dto.setNombre_cliente(cliente.getNombreCliente());
      dto.setApellido_cliente(cliente.getApellidoCliente());
      dto.setCi_cliente(cliente.getCiCliente());
      dto.setCorreo_cliente(cliente.getCorreoCliente());
    }

    // Incluir información del producto si existe
    if (!venta.getProductos().isEmpty()) {
      Productos producto = venta.getProductos().get(0);
      dto.setId_producto(producto.getIdProducto());
      dto.setNombre_producto(producto.getTitulo());
    }

    return dto;
  }
}

