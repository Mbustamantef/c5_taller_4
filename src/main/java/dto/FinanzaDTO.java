package dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Finanza")
public class FinanzaDTO {

  @Schema(description = "ID del registro financiero", example = "1", readOnly = true)
  private Long id_finanzas;

  @NotNull(message = "Las ganancias son obligatorias")
  @DecimalMin(value = "0.0", message = "Las ganancias no pueden ser negativas")
  @Schema(description = "Ganancias del período", example = "15000.50", required = true)
  private Double ganancias;

  @NotNull(message = "Las pérdidas son obligatorias")
  @DecimalMin(value = "0.0", message = "Las pérdidas no pueden ser negativas")
  @Schema(description = "Pérdidas del período", example = "3500.25", required = true)
  private Double perdidas;

  @NotNull(message = "La fecha es obligatoria")
  @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha debe tener el formato yyyy-MM-dd")
  @Schema(description = "Fecha del registro financiero", example = "2025-10-15", required = true)
  private String mes;

  public FinanzaDTO() {
  }

  public Long getId_finanzas() {
    return id_finanzas;
  }

  public void setId_finanzas(Long id_finanzas) {
    this.id_finanzas = id_finanzas;
  }

  public Double getGanancias() {
    return ganancias;
  }

  public void setGanancias(Double ganancias) {
    this.ganancias = ganancias;
  }

  public Double getPerdidas() {
    return perdidas;
  }

  public void setPerdidas(Double perdidas) {
    this.perdidas = perdidas;
  }

  public String getMes() {
    return mes;
  }

  public void setMes(String mes) {
    this.mes = mes;
  }
}
