package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Responsable")
public class ResponsableDTO {

  @Schema(description = "ID del responsable", example = "1", readOnly = true)
  private Long id_responsables;

  @NotNull(message = "El nombre del responsable es obligatorio")
  @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
  @Schema(description = "Nombre del responsable", example = "María", required = true)
  private String nombre;

  @NotNull(message = "El apellido del responsable es obligatorio")
  @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
  @Schema(description = "Apellido del responsable", example = "González", required = true)
  private String apellido;

  @Schema(description = "ID del depósito asociado", example = "1")
  private Long id_deposito;

  public ResponsableDTO() {
  }

  public Long getId_responsables() {
    return id_responsables;
  }

  public void setId_responsables(Long id_responsables) {
    this.id_responsables = id_responsables;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public Long getId_deposito() {
    return id_deposito;
  }

  public void setId_deposito(Long id_deposito) {
    this.id_deposito = id_deposito;
  }
}