package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class Responsables {
  @Id
  @GeneratedValue
  private long id_responsables;
  private String nombre;
  private String apellido;
  private List<Roles>roles;

  public long getId_responsables() {
    return id_responsables;
  }

  public void setId_responsables(long id_responsables) {
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

  public List<Roles> getRoles() {
    return roles;
  }

  public void setRoles(List<Roles> roles) {
    this.roles = roles;
  }
}
