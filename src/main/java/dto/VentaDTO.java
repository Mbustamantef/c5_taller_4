package dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Venta")
public class VentaDTO {

  @Schema(description = "ID de la venta", example = "1", readOnly = true)
  private Long id_ventas;

  @NotNull(message = "El monto es obligatorio")
  @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
  @Schema(description = "Monto total de la venta", example = "1500.50", required = true)
  private Float monto;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  @Schema(description = "Cantidad de productos vendidos", example = "10", required = true)
  private Integer cantidad;

  @NotNull(message = "La fecha es obligatoria")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
  @Schema(description = "Fecha de la venta", example = "2025-10-15", required = true)
  private String mes;

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
}
