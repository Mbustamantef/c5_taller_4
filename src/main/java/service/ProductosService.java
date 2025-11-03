package service;

import dto.ProductoDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Productos;
import repository.ProductosRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductosService {

  @Inject
  ProductosRepository productosRepository;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  public List<ProductoDTO> findAll() {
    return productosRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<ProductoDTO> findById(Long id) {
    return productosRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public ProductoDTO create(ProductoDTO dto) {
    Productos producto = toEntity(dto);
    productosRepository.persist(producto);
    return toDTO(producto);
  }

  public Optional<ProductoDTO> update(Long id, ProductoDTO dto) {
    Optional<Productos> existing = productosRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Productos producto = existing.get();
    producto.setTitulo(dto.getTitulo());
    producto.setPrecioCosto(dto.getPrecio_costo());
    producto.setPrecioVenta(dto.getPrecio_venta());
    producto.setCantidad(dto.getCantidad());
    producto.setCategoria(dto.getCategoria());
    producto.setActivo(dto.getActivo());

    if (dto.getMes_compra() != null) {
      try {
        Date mesCompra = dateFormat.parse(dto.getMes_compra());
        producto.setMesCompra(mesCompra);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }

    return Optional.of(toDTO(producto));
  }

  public boolean delete(Long id) {
    return productosRepository.deleteById(id);
  }

  private ProductoDTO toDTO(Productos producto) {
    ProductoDTO dto = new ProductoDTO();
    dto.setId_producto(producto.getIdProducto());
    dto.setTitulo(producto.getTitulo());
    dto.setPrecio_costo(producto.getPrecioCosto());
    dto.setPrecio_venta(producto.getPrecioVenta());
    dto.setCantidad(producto.getCantidad());
    dto.setCategoria(producto.getCategoria());
    dto.setActivo(producto.getActivo());
    if (producto.getMesCompra() != null) {
      dto.setMes_compra(dateFormat.format(producto.getMesCompra()));
    }

    // Incluir información de depósitos
    if (producto.getDepositos() != null && !producto.getDepositos().isEmpty()) {
      dto.setIds_depositos(
          producto.getDepositos().stream()
              .map(models.Depositos::getIdDeposito)
              .collect(Collectors.toList())
      );
      dto.setNombres_depositos(
          producto.getDepositos().stream()
              .map(models.Depositos::getNombreDeposito)
              .collect(Collectors.toList())
      );
    }

    return dto;
  }

  private Productos toEntity(ProductoDTO dto) {
    Productos producto = new Productos();
    producto.setTitulo(dto.getTitulo());
    producto.setPrecioCosto(dto.getPrecio_costo());
    producto.setPrecioVenta(dto.getPrecio_venta());
    producto.setCantidad(dto.getCantidad());
    producto.setCategoria(dto.getCategoria());
    producto.setActivo(dto.getActivo());
    if (dto.getMes_compra() != null) {
      try {
        Date mesCompra = dateFormat.parse(dto.getMes_compra());
        producto.setMesCompra(mesCompra);
      } catch (ParseException e) {
        throw new RuntimeException("Formato de fecha inválido. Use yyyy-MM-dd");
      }
    }
    return producto;
  }
}

