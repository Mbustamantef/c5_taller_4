package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class Depositos {

  @Id
  @GeneratedValue
  private long id_deposito;
  private String nombre;
  private List<Responsables> responsables;

  public long getId_deposito() {
    return id_deposito;
  }

  public void setId_deposito(long id_deposito) {
    this.id_deposito = id_deposito;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Responsables> getResponsables() {
    return responsables;
  }

  public void setResponsables(List<Responsables> responsables) {
    this.responsables = responsables;
  }
}
