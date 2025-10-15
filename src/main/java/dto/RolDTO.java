package dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Rol")
public class RolDTO {

  @Schema(description = "ID del rol", example = "1", readOnly = true)
  private Long id_rol;

  @NotNull(message = "La descripción del rol es obligatoria")
  @Size(min = 3, max = 100, message = "La descripción debe tener entre 3 y 100 caracteres")
  @Schema(description = "Descripción del rol", example = "Administrador", required = true)
  private String descripcion;

  public RolDTO() {
  }

  public Long getId_rol() {
    return id_rol;
  }

  public void setId_rol(Long id_rol) {
    this.id_rol = id_rol;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
