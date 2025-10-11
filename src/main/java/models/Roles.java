package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles", schema = "public")
public class Roles {

  @Id
  @GeneratedValue
  @Column(name = "id_rol", nullable = false)
  private long id_rol;
  @Column(name = "descripcion", nullable = false)
  private String descripcion;
  @ManyToOne
  @JoinColumn(name = "id_responsables")
  private Responsables responsable;

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

  public Responsables getResponsable() {
    return responsable;
  }

  public void setResponsable(Responsables responsable) {
    this.responsable = responsable;
  }
}
