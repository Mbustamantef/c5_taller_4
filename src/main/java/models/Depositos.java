package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "depositos", schema = "public")
public class Depositos {

  @Id
  @GeneratedValue
  @Column(name= "id_deposito", updatable=false,nullable=false)
  private long id_deposito;
  @Column(name = "nombre_deposito", nullable = false)
  private String nombre_deposito;
  @OneToMany(mappedBy = "deposito", fetch = FetchType.LAZY)
  private List<Responsables> responsables;
  @ManyToMany(mappedBy = "depositos")
  private List<Productos> productos;

  public long getId_deposito() {
    return id_deposito;
  }

  public void setId_deposito(long id_deposito) {
    this.id_deposito = id_deposito;
  }

  public String getNombre_deposito() {
    return nombre_deposito;
  }

  public void setNombre_deposito(String nombre_deposito) {
    this.nombre_deposito = nombre_deposito;
  }

  public List<Responsables> getResponsables() {
    return responsables;
  }

  public void setResponsables(List<Responsables> responsables) {
    this.responsables = responsables;
  }

  public List<Productos> getProductos() {
    return productos;
  }

  public void setProductos(List<Productos> productos) {
    this.productos = productos;
  }
}
