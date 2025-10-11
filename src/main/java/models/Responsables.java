package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "responsables", schema = "public")
public class Responsables {
  @Id
  @GeneratedValue
  @Column(name = "id_responsables", nullable = false)
  private long id_responsables;
  @Column(name = "nombre", nullable = false)
  private String nombre;
  @Column(name = "apellido", nullable = false)
  private String apellido;
  @OneToMany(mappedBy = "responsable", fetch = jakarta.persistence.FetchType.LAZY)
  private List<Roles> roles;

  @ManyToOne
  @JoinColumn(name = "id_deposito")
  private Depositos deposito;

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

  public Depositos getDeposito() {
    return deposito;
  }

  public void setDeposito(Depositos deposito) {
    this.deposito = deposito;
  }
}
