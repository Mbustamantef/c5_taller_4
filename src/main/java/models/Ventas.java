package models;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas", schema = "public")
public class Ventas {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_ventas", nullable = false)
  private Long idVentas;

  @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonbTransient
  private List<Clientes> clientes = new ArrayList<>();

  @OneToMany(mappedBy = "venta", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonbTransient
  private List<Productos> productos = new ArrayList<>();

  @NotNull(message = "El monto es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
  @Column(name = "monto", nullable = false)
  private Float monto;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @NotNull(message = "La fecha es obligatoria")
  @Temporal(TemporalType.DATE)
  @Column(name = "mes", nullable = false)
  @JsonbDateFormat("yyyy-MM-dd")
  private Date mes;

  // Getters and Setters
  public Long getIdVentas() {
    return idVentas;
  }

  public void setIdVentas(Long idVentas) {
    this.idVentas = idVentas;
  }

  public List<Clientes> getClientes() {
    return clientes;
  }

  public void setClientes(List<Clientes> clientes) {
    this.clientes = clientes;
  }

  public List<Productos> getProductos() {
    return productos;
  }

  public void setProductos(List<Productos> productos) {
    this.productos = productos;
  }

  public Float getMonto() {
    return monto;
  }

  public void setMonto(Float monto) {
    this.monto = monto;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Date getMes() {
    return mes;
  }

  public void setMes(Date mes) {
    this.mes = mes;
  }
}
