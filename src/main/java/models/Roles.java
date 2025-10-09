package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Roles {

  @Id
  @GeneratedValue
  private long id_rol;
  private String descripcion;

  public long getId_rol() {
    return id_rol;
  }

  public void setId_rol(long id_rol) {
    this.id_rol = id_rol;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
}
