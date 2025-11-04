package models;

import core.generic.Auditable;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "movimientos_inventario", schema = "public")
public class MovimientosInventario extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_movimiento")
  private Long idMovimiento;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto", nullable = false)
  @NotNull(message = "El producto es obligatorio")
  private Productos producto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_deposito_origen")
  private Depositos depositoOrigen;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_deposito_destino")
  private Depositos depositoDestino;

  @NotNull(message = "El tipo de movimiento es obligatorio")
  @Enumerated(EnumType.STRING)
  @Column(name = "tipo_movimiento", nullable = false, length = 20)
  private TipoMovimiento tipoMovimiento;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 1, message = "La cantidad debe ser al menos 1")
  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @NotNull(message = "El motivo es obligatorio")
  @Size(min = 3, max = 200, message = "El motivo debe tener entre 3 y 200 caracteres")
  @Column(name = "motivo", nullable = false, length = 200)
  private String motivo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_usuario")
  private Usuarios usuario;

  @NotNull(message = "La fecha es obligatoria")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_movimiento", nullable = false)
  @JsonbDateFormat("yyyy-MM-dd HH:mm:ss")
  private Date fechaMovimiento;

  @Column(name = "observaciones", length = 500)
  private String observaciones;

  // Enum para tipo de movimiento
  public enum TipoMovimiento {
    ENTRADA,
    SALIDA,
    TRANSFERENCIA,
    AJUSTE
  }

  // Getters and Setters
  public Long getIdMovimiento() {
    return idMovimiento;
  }

  public void setIdMovimiento(Long idMovimiento) {
    this.idMovimiento = idMovimiento;
  }

  public Productos getProducto() {
    return producto;
  }

  public void setProducto(Productos producto) {
    this.producto = producto;
  }

  public Depositos getDepositoOrigen() {
    return depositoOrigen;
  }

  public void setDepositoOrigen(Depositos depositoOrigen) {
    this.depositoOrigen = depositoOrigen;
  }

  public Depositos getDepositoDestino() {
    return depositoDestino;
  }

  public void setDepositoDestino(Depositos depositoDestino) {
    this.depositoDestino = depositoDestino;
  }

  public TipoMovimiento getTipoMovimiento() {
    return tipoMovimiento;
  }

  public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
    this.tipoMovimiento = tipoMovimiento;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Usuarios getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuarios usuario) {
    this.usuario = usuario;
  }

  public Date getFechaMovimiento() {
    return fechaMovimiento;
  }

  public void setFechaMovimiento(Date fechaMovimiento) {
    this.fechaMovimiento = fechaMovimiento;
  }

  public String getObservaciones() {
    return observaciones;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }
}

