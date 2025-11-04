package models;

import core.generic.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "stock", schema = "public")
public class Stock extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_stock")
  private Long idStock;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_producto", nullable = false)
  @NotNull(message = "El producto es obligatorio")
  private Productos producto;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_deposito", nullable = false)
  @NotNull(message = "El depósito es obligatorio")
  private Depositos deposito;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @Min(value = 0, message = "El stock mínimo no puede ser negativo")
  @Column(name = "stock_minimo")
  private Integer stockMinimo;

  // Getters and Setters
  public Long getIdStock() {
    return idStock;
  }

  public void setIdStock(Long idStock) {
    this.idStock = idStock;
  }

  public Productos getProducto() {
    return producto;
  }

  public void setProducto(Productos producto) {
    this.producto = producto;
  }

  public Depositos getDeposito() {
    return deposito;
  }

  public void setDeposito(Depositos deposito) {
    this.deposito = deposito;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Integer getStockMinimo() {
    return stockMinimo;
  }

  public void setStockMinimo(Integer stockMinimo) {
    this.stockMinimo = stockMinimo;
  }
}

