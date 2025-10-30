package dto;

import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Producto")
public class ProductoDTO {

  @Schema(description = "ID del producto", example = "1", readOnly = true)
  private Long id_producto;

  @NotNull(message = "El título del producto es obligatorio")
  @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
  @Schema(description = "Nombre del producto", example = "Laptop HP", required = true)
  private String titulo;

  @NotNull(message = "El precio de costo es obligatorio")
  @DecimalMin(value = "0.01", message = "El precio de costo debe ser mayor a 0")
  @Schema(description = "Precio de costo del producto", example = "500.00", required = true)
  private Float precio_costo;

  @NotNull(message = "El precio de venta es obligatorio")
  @DecimalMin(value = "0.01", message = "El precio de venta debe ser mayor a 0")
  @Schema(description = "Precio de venta del producto", example = "750.00", required = true)
  private Float precio_venta;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Schema(description = "Cantidad disponible en inventario", example = "50", required = true)
  private Integer cantidad;

  @NotNull(message = "La categoría es obligatoria")
  @Size(min = 3, max = 100, message = "La categoría debe tener entre 3 y 100 caracteres")
  @Schema(description = "Categoría del producto", example = "Electrónica", required = true)
  private String categoria;

  @NotNull(message = "El estado activo es obligatorio")
  @Schema(description = "Indica si el producto está activo", example = "true", required = true)
  private Boolean activo;

  @NotNull(message = "La fecha de compra es obligatoria")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
  @Schema(description = "Fecha de compra del producto", example = "2025-10-15", required = true)
  private String mes_compra;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Schema(description = "Inserte el deposito correspondiente", example = "50", required = true)
  private Integer id_deposito;

  public ProductoDTO() {
  }

  public Integer getId_deposito() {
    return id_deposito;
  }

  public void setId_deposito(Integer id_deposito) {
    this.id_deposito = id_deposito;
  }

  public Long getId_producto() {
    return id_producto;
  }

  public void setId_producto(Long id_producto) {
    this.id_producto = id_producto;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Float getPrecio_costo() {
    return precio_costo;
  }

  public void setPrecio_costo(Float precio_costo) {
    this.precio_costo = precio_costo;
  }

  public Float getPrecio_venta() {
    return precio_venta;
  }

  public void setPrecio_venta(Float precio_venta) {
    this.precio_venta = precio_venta;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public String getMes_compra() {
    return mes_compra;
  }

  public void setMes_compra(String mes_compra) {
    this.mes_compra = mes_compra;
  }
}
