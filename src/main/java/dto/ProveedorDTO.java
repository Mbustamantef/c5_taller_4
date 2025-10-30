package dto;

import jakarta.validation.constraints.*;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Proveedor")
public class ProveedorDTO {

  @Schema(description = "ID del proveedor", example = "1", readOnly = true)
  private Long id_proveedor;

  @NotNull(message = "El título es obligatorio")
  @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
  @Schema(description = "Título del producto/servicio", example = "Laptop HP", required = true)
  private String titulo;

  @NotNull(message = "El nombre del proveedor es obligatorio")
  @Size(min = 3, max = 255, message = "El nombre del proveedor debe tener entre 3 y 255 caracteres")
  @Schema(description = "Nombre del proveedor", example = "Tech Suppliers S.A.", required = true)
  private String nombreProveedor;

  @NotNull(message = "El costo es obligatorio")
  @DecimalMin(value = "0.01", message = "El costo debe ser mayor a 0")
  @Schema(description = "Costo del producto/servicio", example = "450.00", required = true)
  private Float costo;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Schema(description = "Cantidad suministrada", example = "100", required = true)
  private Integer cantidad;

  @NotNull(message = "La moneda es obligatoria")
  @Size(min = 2, max = 10, message = "La moneda debe tener entre 2 y 10 caracteres")
  @Schema(description = "Moneda de la transacción", example = "USD", required = true)
  private String moneda;

  @NotNull(message = "La fecha de compra es obligatoria")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
  @Schema(description = "Fecha de compra al proveedor", example = "2025-10-15", required = true)
  private String mes_compra;

  public ProveedorDTO() {
  }

  public Long getId_proveedor() {
    return id_proveedor;
  }

  public void setId_proveedor(Long id_proveedor) {
    this.id_proveedor = id_proveedor;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getNombreProveedor() {
    return nombreProveedor;
  }

  public void setNombreProveedor(String nombreProveedor) {
    this.nombreProveedor = nombreProveedor;
  }

  public Float getCosto() {
    return costo;
  }

  public void setCosto(Float costo) {
    this.costo = costo;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public String getMes_compra() {
    return mes_compra;
  }

  public void setMes_compra(String mes_compra) {
    this.mes_compra = mes_compra;
  }
}
