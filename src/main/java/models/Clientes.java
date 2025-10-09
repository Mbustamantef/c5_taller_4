package models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Clientes {

  @Id
  @GeneratedValue
  private long id_cliente;
  private String nombre_cliente;
  private String apellido_cliente;
  private String ci_cliente;
  private String correo_cliente;

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
}
