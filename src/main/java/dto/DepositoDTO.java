package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Depósito")
public class DepositoDTO {

  @Schema(description = "ID del depósito", example = "1", readOnly = true)
  private Long id_deposito;

  @NotNull(message = "El nombre del depósito es obligatorio")
  @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
  @Schema(description = "Nombre del depósito", example = "Depósito Central", required = true)
  private String nombre_deposito;

  public DepositoDTO() {
  }

  public Long getId_deposito() {
    return id_deposito;
  }

  public void setId_deposito(Long id_deposito) {
    this.id_deposito = id_deposito;
  }

  public String getNombre_deposito() {
    return nombre_deposito;
  }

  public void setNombre_deposito(String nombre_deposito) {
    this.nombre_deposito = nombre_deposito;
  }
}
