package models;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clientes", schema = "public")
public class Clientes {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_cliente", nullable = false)
  private Long idCliente;

  @NotNull(message = "El nombre del cliente es obligatorio")
  @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
  @Column(name = "nombre_cliente", nullable = false, length = 100)
  private String nombreCliente;

  @NotNull(message = "El apellido del cliente es obligatorio")
  @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
  @Column(name = "apellido_cliente", nullable = false, length = 100)
  private String apellidoCliente;

  @NotNull(message = "El CI del cliente es obligatorio")
  @Size(min = 5, max = 20, message = "El CI debe tener entre 5 y 20 caracteres")
  @Column(name = "ci_cliente", nullable = false, unique = true, length = 20)
  private String ciCliente;

  @NotNull(message = "El correo del cliente es obligatorio")
  @Email(message = "El correo debe ser válido")
  @Column(name = "correo_cliente", nullable = false, unique = true, length = 150)
  private String correoCliente;

  @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JsonbTransient
  private List<Ventas> ventas = new ArrayList<>();

  // Getters and Setters
  public Long getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(Long idCliente) {
    this.idCliente = idCliente;
  }

  public String getNombreCliente() {
    return nombreCliente;
  }

  public void setNombreCliente(String nombreCliente) {
    this.nombreCliente = nombreCliente;
  }

  public String getApellidoCliente() {
    return apellidoCliente;
  }

  public void setApellidoCliente(String apellidoCliente) {
    this.apellidoCliente = apellidoCliente;
  }

  public String getCiCliente() {
    return ciCliente;
  }

  public void setCiCliente(String ciCliente) {
    this.ciCliente = ciCliente;
  }

  public String getCorreoCliente() {
    return correoCliente;
  }

  public void setCorreoCliente(String correoCliente) {
    this.correoCliente = correoCliente;
  }

  public List<Ventas> getVentas() {
    return ventas;
  }

  public void setVentas(List<Ventas> ventas) {
    this.ventas = ventas;
  }

}
