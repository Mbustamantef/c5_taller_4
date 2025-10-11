package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes" ,schema = "public")
public class Clientes {
  @Id
  @GeneratedValue
  @Column(name = "id_cliente",nullable = false)
  private long id_cliente;
  @Column(name = "nombre_cliente",nullable = false)
  private String nombre_cliente;
  @Column(name = "apellido_cliente",nullable = false)
  private String apellido_cliente;
  @Column(name = "ci_cliente",nullable = false)
  private String ci_cliente;
  @Column(name = "correo_cliente",nullable = false)
  private String correo_cliente;

  @ManyToOne
  @JoinColumn(name = "id_ventas")
  private Ventas venta;

  public long getId_cliente() {
    return id_cliente;
  }

  public void setId_cliente(long id_cliente) {
    this.id_cliente = id_cliente;
  }

  public String getNombre_cliente() {
    return nombre_cliente;
  }

  public void setNombre_cliente(String nombre_cliente) {
    this.nombre_cliente = nombre_cliente;
  }

  public String getApellido_cliente() {
    return apellido_cliente;
  }

  public void setApellido_cliente(String apellido_cliente) {
    this.apellido_cliente = apellido_cliente;
  }

  public String getCi_cliente() {
    return ci_cliente;
  }

  public void setCi_cliente(String ci_cliente) {
    this.ci_cliente = ci_cliente;
  }

  public String getCorreo_cliente() {
    return correo_cliente;
  }

  public void setCorreo_cliente(String correo_cliente) {
    this.correo_cliente = correo_cliente;
  }

  public Ventas getVenta() {
    return venta;
  }

  public void setVenta(Ventas venta) {
    this.venta = venta;
  }
}
