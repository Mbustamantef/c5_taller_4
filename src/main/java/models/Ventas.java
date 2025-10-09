package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Ventas {
  @Id
  @GeneratedValue
  private long id_ventas;
  private List<Clientes> clientes;
  private List<Productos>productos;
  private float monto;
  private int cantidad;
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
