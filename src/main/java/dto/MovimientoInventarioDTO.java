package dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Movimientos de Inventario")
public class MovimientoInventarioDTO {

  @Schema(description = "ID del movimiento", readOnly = true)
  private Long idMovimiento;

  @NotNull(message = "El ID del producto es obligatorio")
  @Schema(description = "ID del producto", required = true)
  private Long idProducto;

  @Schema(description = "Nombre del producto", readOnly = true)
  private String nombreProducto;

  @Schema(description = "ID del depósito origen (para transferencias y salidas)")
  private Long idDepositoOrigen;

  @Schema(description = "Nombre del depósito origen", readOnly = true)
  private String nombreDepositoOrigen;

  @Schema(description = "ID del depósito destino (para transferencias y entradas)")
  private Long idDepositoDestino;

  @Schema(description = "Nombre del depósito destino", readOnly = true)
  private String nombreDepositoDestino;

  @NotNull(message = "El tipo de movimiento es obligatorio")
  @Schema(description = "Tipo de movimiento: ENTRADA, SALIDA, TRANSFERENCIA, AJUSTE", required = true)
  private String tipoMovimiento;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  @Schema(description = "Cantidad del movimiento", required = true)
  private Integer cantidad;

  @NotNull(message = "El motivo es obligatorio")
  @Size(min = 3, max = 200, message = "El motivo debe tener entre 3 y 200 caracteres")
  @Schema(description = "Motivo del movimiento", required = true)
  private String motivo;

  @Schema(description = "ID del usuario que realiza el movimiento")
  private Long idUsuario;

  @Schema(description = "Nombre del usuario", readOnly = true)
  private String nombreUsuario;

  @Schema(description = "Fecha del movimiento", readOnly = true)
  private String fechaMovimiento;

  @Schema(description = "Observaciones adicionales")
  private String observaciones;

  // Getters and Setters
  public Long getIdMovimiento() {
    return idMovimiento;
  }

  public void setIdMovimiento(Long idMovimiento) {
    this.idMovimiento = idMovimiento;
  }

  public Long getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Long idProducto) {
    this.idProducto = idProducto;
  }

  public String getNombreProducto() {
    return nombreProducto;
  }

  public void setNombreProducto(String nombreProducto) {
    this.nombreProducto = nombreProducto;
  }

  public Long getIdDepositoOrigen() {
    return idDepositoOrigen;
  }

  public void setIdDepositoOrigen(Long idDepositoOrigen) {
    this.idDepositoOrigen = idDepositoOrigen;
  }

  public String getNombreDepositoOrigen() {
    return nombreDepositoOrigen;
  }

  public void setNombreDepositoOrigen(String nombreDepositoOrigen) {
    this.nombreDepositoOrigen = nombreDepositoOrigen;
  }

  public Long getIdDepositoDestino() {
    return idDepositoDestino;
  }

  public void setIdDepositoDestino(Long idDepositoDestino) {
    this.idDepositoDestino = idDepositoDestino;
  }

  public String getNombreDepositoDestino() {
    return nombreDepositoDestino;
  }

  public void setNombreDepositoDestino(String nombreDepositoDestino) {
    this.nombreDepositoDestino = nombreDepositoDestino;
  }

  public String getTipoMovimiento() {
    return tipoMovimiento;
  }

  public void setTipoMovimiento(String tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Long getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
  }

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getFechaMovimiento() {
    return fechaMovimiento;
  }

  public void setFechaMovimiento(String fechaMovimiento) {
    this.fechaMovimiento = fechaMovimiento;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}

