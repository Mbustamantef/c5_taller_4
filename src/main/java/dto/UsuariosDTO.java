package dto;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Rol")

public class UsuariosDTO {

  @Schema(description = "Nombre del usuario", example = "Jose", readOnly = true)
  @NotNull(message = "El nombre es obligatoria")
  @Size(min = 3, max = 100, message = "El nombre es obligatario debe tener entre 3 y 100 caracteres")
  private String nombreUsuario;

  @Schema(description = "La contraseña del usuario",examples = "Pr0y3ct0!!" , readOnly = true)
  @NotNull(message = "La contraseña del usuario es obligatoria")
  @Size(min = 3, max = 100, message = "La contraseña debe tener entre 3 y 100 caracteres")
  private String contrasena;

  @JsonbTransient
  @Schema(description = "ID del rol", example = "1", readOnly = true)
  private Long id_rol;

  public String getNombreUsuario() {
    return nombreUsuario;
  }

  public void setNombreUsuario(String nombreUsuario) {
    this.nombreUsuario = nombreUsuario;
  }

  public String getContrasena() {
    return contrasena;
  }

  public void setContrasena(String contrasena) {
    this.contrasena = contrasena;
  }

  public Long getId_rol() {
    return id_rol;
  }

  public void setId_rol(Long id_rol) {
    this.id_rol = id_rol;
  }
}
