package dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Cliente")
public class ClienteDTO {

  @Schema(description = "ID del cliente", example = "1", readOnly = true)
  private Long id_cliente;

  @NotNull(message = "El nombre del cliente es obligatorio")
  @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
  @Schema(description = "Nombre del cliente", example = "Juan", required = true)
  private String nombre_cliente;

  @NotNull(message = "El apellido del cliente es obligatorio")
  @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
  @Schema(description = "Apellido del cliente", example = "Pérez", required = true)
  private String apellido_cliente;

  @NotNull(message = "El CI del cliente es obligatorio")
  @Size(min = 5, max = 20, message = "El CI debe tener entre 5 y 20 caracteres")
  @Schema(description = "Cédula de identidad del cliente", example = "12345678", required = true)
  private String ci_cliente;

  @NotNull(message = "El correo del cliente es obligatorio")
  @Email(message = "El correo debe ser válido")
  @Schema(description = "Correo electrónico del cliente", example = "juan@example.com", required = true)
  private String correo_cliente;


  public ClienteDTO() {
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
