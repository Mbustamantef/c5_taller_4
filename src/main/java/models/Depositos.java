package models;

import core.generic.Auditable;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "depositos", schema = "public")
public class Depositos extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_deposito", updatable = false, nullable = false)
  private Long idDeposito;

  @NotNull(message = "El nombre del depósito es obligatorio")
  @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
  @Column(name = "nombre_deposito", nullable = false, length = 150)
  private String nombreDeposito;

  @OneToMany(mappedBy = "deposito", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonbTransient
  private List<Responsables> responsables = new ArrayList<>();

  @ManyToMany(mappedBy = "depositos", fetch = FetchType.LAZY)
  @JsonbTransient
  private List<Productos> productos = new ArrayList<>();

  // Getters and Setters
  public Long getIdDeposito() {
    return idDeposito;
  }

  public void setIdDeposito(Long idDeposito) {
    this.idDeposito = idDeposito;
  }

  public String getNombreDeposito() {
    return nombreDeposito;
  }

  public void setNombreDeposito(String nombreDeposito) {
    this.nombreDeposito = nombreDeposito;
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
