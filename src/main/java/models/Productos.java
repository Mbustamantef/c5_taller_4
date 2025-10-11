package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "productos", schema = "public")
public class Productos {

  @Id
  @GeneratedValue
  @Column(name = "id_producto", nullable = false)
  private long id_producto;
  @Column(name = "titulo", nullable = false)
  private String titulo;
  @Column(name = "precio_costo", nullable = false)
  private float precio_costo;
  @Column(name = "precio_venta", nullable = false)
  private float precio_venta;
  @Column(name = "cantidad", nullable = false)
  private int cantidad;
  @Column(name = "categoria", nullable = false)
  private String categoria;
  @ManyToMany
  @JoinTable(
    name = "productos_proveedores",
    joinColumns = @JoinColumn(name = "id_producto"),
    inverseJoinColumns = @JoinColumn(name = "id_proveedor")
  )
  private List<Proveedores> proveedores;
  @Column(name = "activo", nullable = false)
  private Boolean activo;
  @ManyToMany
  private List<Depositos> depositos;
  @Column(name = "mes_compra", nullable = false)
  private Date mes_compra;
  @ManyToOne
  @JoinColumn(name = "id_ventas")
  private Ventas venta;

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

  public Ventas getVenta() {
    return venta;
  }

  public void setVenta(Ventas venta) {
    this.venta = venta;
  }
}
