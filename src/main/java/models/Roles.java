package models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "roles", schema = "public")
public class Roles {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_rol", nullable = false)
  private Long idRol;

  @NotNull(message = "La descripción del rol es obligatoria")
  @Size(min = 3, max = 100, message = "La descripción debe tener entre 3 y 100 caracteres")
  @Column(name = "descripcion", nullable = false, length = 100)
  private String descripcion;


  // Getters and Setters
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