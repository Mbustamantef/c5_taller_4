package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;
import java.util.List;

@Entity
public class Productos {

  @Id
  @GeneratedValue
  private long id_producto;
  private String titulo;
  private float precio_costo;
  private float precio_venta;
  private int cantidad;
  private String categoria;
  private List<Proveedores> proveedores;
  private Boolean activo;
  private List<Depositos> depositos;
  private Date mes_compra;

  public long getId_producto() {
    return id_producto;
  }

  public void setId_producto(long id_producto) {
    this.id_producto = id_producto;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public float getPrecio_costo() {
    return precio_costo;
  }

  public void setPrecio_costo(float precio_costo) {
    this.precio_costo = precio_costo;
  }

  public float getPrecio_venta() {
    return precio_venta;
  }

  public void setPrecio_venta(float precio_venta) {
    this.precio_venta = precio_venta;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Boolean getActivo() {
    return activo;
  }

  public void setActivo(Boolean activo) {
    this.activo = activo;
  }

  public Date getMes_compra() {
    return mes_compra;
  }

  public void setMes_compra(Date mes_compra) {
    this.mes_compra = mes_compra;
  }

  public List<Proveedores> getProveedores() {
    return proveedores;
  }

  public void setProveedores(List<Proveedores> proveedores) {
    this.proveedores = proveedores;
  }

  public List<Depositos> getDepositos() {
    return depositos;
  }

  public void setDepositos(List<Depositos> depositos) {
    this.depositos = depositos;
  }
}
