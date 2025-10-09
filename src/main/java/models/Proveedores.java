package models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Proveedores {

  @Id
  @GeneratedValue
  private long id_proveedor;
  private String titulo;
  private Float costo;
  private int cantidad;
  private String moneda;
  private Date mes_compra;

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
}
