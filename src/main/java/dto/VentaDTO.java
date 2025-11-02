package dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Venta")
public class VentaDTO {

  @Schema(description = "ID de la venta", example = "1", readOnly = true)
  private Long id_ventas;

  @Schema(description = "Monto total de la venta (calculado automáticamente)", example = "1500.50", readOnly = true)
  private Float monto;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  @Schema(description = "Cantidad de productos vendidos", example = "10", required = true)
  private Integer cantidad;

  @Schema(description = "Fecha de la venta (se establece automáticamente)", example = "2025-11-02", readOnly = true)
  private String mes;

  @NotNull(message = "El id del producto es obligatorio")
  @Min(value = 1, message = "El id del producto debe ser al menos 1")
  @Schema(description = "ID del producto", example = "1", required = true)
  private Long id_producto;

  @Schema(description = "Nombre del producto", example = "Laptop Dell", readOnly = true)
  private String nombre_producto;

  @Schema(description = "ID del cliente (si ya existe)", example = "10")
  private Long id_cliente;

  // Campos opcionales para crear un nuevo cliente si no existe
  @Schema(description = "Nombre del cliente (para crear nuevo)", example = "Juan")
  private String nombre_cliente;

  @Schema(description = "Apellido del cliente (para crear nuevo)", example = "Pérez")
  private String apellido_cliente;

  @Schema(description = "CI del cliente (para crear nuevo)", example = "12345678")
  private String ci_cliente;

  @Schema(description = "Correo del cliente (para crear nuevo)", example = "juan@example.com")
  private String correo_cliente;

  public Long getId_producto() {
    return id_producto;
  }

  public void setId_producto(Long id_producto) {
    this.id_producto = id_producto;
  }

  public String getNombre_producto() {
    return nombre_producto;
  }

  public void setNombre_producto(String nombre_producto) {
    this.nombre_producto = nombre_producto;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public String getMes() {

    return mes;
  }

  public void setMes(String mes) {
    this.mes = mes;
  }

  public VentaDTO() {
  }

  public Long getId_ventas() {
    return id_ventas;
  }

  public void setId_ventas(Long id_ventas) {
    this.id_ventas = id_ventas;
  }

  public Float getMonto() {
    return monto;
  }

  public void setMonto(Float monto) {
    this.monto = monto;
  }


  public Long getId_cliente() {
    return id_cliente;
  }

  public void setId_cliente(Long id_cliente) {
    this.id_cliente = id_cliente;
  }

  public String getNombre_cliente() {
    return nombre_cliente;
  }

  public void setNombre_cliente(String nombre_cliente) {
    this.nombre_cliente = nombre_cliente;
  }

  public String getApellido_cliente() {
    return apellido_cliente;
  }

  public void setApellido_cliente(String apellido_cliente) {
    this.apellido_cliente = apellido_cliente;
  }

  public String getCi_cliente() {
    return ci_cliente;
  }

  public void setCi_cliente(String ci_cliente) {
    this.ci_cliente = ci_cliente;
  }

  public String getCorreo_cliente() {
    return correo_cliente;
  }

  public void setCorreo_cliente(String correo_cliente) {
    this.correo_cliente = correo_cliente;
  }

}
