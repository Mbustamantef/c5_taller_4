package models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "proveedores", schema = "public")
public class Proveedores {

  @Id
  @GeneratedValue
  @Column(name = "id_proveedor", nullable = false)
  private long id_proveedor;
  @Column(name = "titulo", nullable = false)
  private String titulo;
  @Column(name = "costo", nullable = false)
  private Float costo;
  @Column(name = "cantidad", nullable = false)
  private int cantidad;
  @Column(name = "moneda", nullable = false)
  private String moneda;
  @Column(name = "mes_compra", nullable = false)
  private Date mes_compra;
  @ManyToMany(mappedBy = "proveedores")
  private List<Productos> productos;

  public long getId_proveedor() {
    return id_proveedor;
  }

  public void setId_proveedor(long id_proveedor) {
    this.id_proveedor = id_proveedor;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Float getCosto() {
    return costo;
  }

  public void setCosto(Float costo) {
    this.costo = costo;
  }

  public int getCantidad() {
    return cantidad;
  }

  public void setCantidad(int cantidad) {
    this.cantidad = cantidad;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public Date getMes_compra() {
    return mes_compra;
  }

  public void setMes_compra(Date mes_compra) {
    this.mes_compra = mes_compra;
  }

  public List<Productos> getProductos() {
    return productos;
  }

  public void setProductos(List<Productos> productos) {
    this.productos = productos;
  }
}
