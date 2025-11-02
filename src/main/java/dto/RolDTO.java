package dto;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Rol")
public class RolDTO {

  @Schema(description = "ID del rol", example = "1", readOnly = true)
  @JsonbProperty("id_rol")
  private Long idRol;

  @NotNull(message = "La descripción del rol es obligatoria")
  @Size(min = 3, max = 100, message = "La descripción debe tener entre 3 y 100 caracteres")
  @Schema(description = "Descripción del rol", example = "Administrador", required = true)
  private String descripcion;

  public RolDTO() {
  }

  public Long getIdRol() {
    return idRol;
  }

  public void setIdRol(Long idRol) {
    this.idRol = idRol;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
