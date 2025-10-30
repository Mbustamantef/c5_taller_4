package models;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "responsables", schema = "public")
public class Responsables {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_responsables", nullable = false)
  private Long idResponsables;

  @NotNull(message = "El nombre del responsable es obligatorio")
  @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @NotNull(message = "El apellido del responsable es obligatorio")
  @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
  @Column(name = "apellido", nullable = false, length = 100)
  private String apellido;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_rol") // foreign key column in responsables table
  private Roles rol;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_deposito")
  private Depositos deposito;

  // Getters and Setters
  public Long getIdResponsables() {
    return idResponsables;
  }

  public void setIdResponsables(Long idResponsables) {
    this.idResponsables = idResponsables;
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

  public Roles getRol() {
    return rol;
  }

  public void setRol(Roles rol) {
    this.rol = rol;
  }

  public Depositos getDeposito() {
    return deposito;
  }

  public void setDeposito(Depositos deposito) {
    this.deposito = deposito;
  }
}
