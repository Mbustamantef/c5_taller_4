package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas",schema = "public")
public class Ventas {
  @Id
  @GeneratedValue
  @Column(name = "id_ventas", nullable = false)
  private long id_ventas;
  @OneToMany(mappedBy = "venta", fetch = jakarta.persistence.FetchType.LAZY)
  private List<Clientes> clientes;
  @OneToMany(mappedBy = "venta", fetch = jakarta.persistence.FetchType.LAZY)
  private List<Productos> productos;
  @Column(name = "monto", nullable = false)
  private float monto;
  @Column(name = "cantidad", nullable = false)
  private int cantidad;
  @Column(name = "mes", nullable = false)
  private Date mes;

  public long getId_ventas() {
    return id_ventas;
  }

  public void setId_ventas(long id_ventas) {
    this.id_ventas = id_ventas;
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

  public float getMonto() {
    return monto;
  }

  public void setMonto(float monto) {
    this.monto = monto;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public Date getMes() {
    return mes;
  }

  public void setMes(Date mes) {
    this.mes = mes;
  }
}
