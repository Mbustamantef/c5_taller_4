package models;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "proveedores", schema = "public")
public class Proveedores {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_proveedor", nullable = false)
  private Long idProveedor;

  @NotNull(message = "El título es obligatorio")
  @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
  @Column(name = "titulo", nullable = false, length = 200)
  private String titulo;

  @NotNull(message = "El nombre del proveedor es obligatorio")
  @Size(min = 3, max = 255, message = "El nombre del proveedor debe tener entre 3 y 255 caracteres")
  @Column(name = "nombre_proveedor", nullable = false, length = 255)
  private String nombreProveedor;

  @NotNull(message = "El costo es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El costo debe ser mayor a 0")
  @Column(name = "costo", nullable = false)
  private Float costo;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @NotNull(message = "La moneda es obligatoria")
  @Size(min = 2, max = 10, message = "La moneda debe tener entre 2 y 10 caracteres")
  @Column(name = "moneda", nullable = false, length = 10)
  private String moneda;

  @NotNull(message = "La fecha de compra es obligatoria")
  @Temporal(TemporalType.DATE)
  @Column(name = "mes_compra", nullable = false)
  @JsonbDateFormat("yyyy-MM-dd")
  private Date mesCompra;

  @ManyToMany(mappedBy = "proveedores", fetch = FetchType.LAZY)
  @JsonbTransient
  private List<Productos> productos = new ArrayList<>();

  // Getters and Setters
  public Long getIdProveedor() {
    return idProveedor;
  }

  public void setIdProveedor(Long idProveedor) {
    this.idProveedor = idProveedor;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getNombreProveedor() {
    return nombreProveedor;
  }

  public void setNombreProveedor(String nombreProveedor) {
    this.nombreProveedor = nombreProveedor;
  }

  public Float getCosto() {
    return costo;
  }

  public void setCosto(Float costo) {
    this.costo = costo;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public String getMoneda() {
    return moneda;
  }

  public void setMoneda(String moneda) {
    this.moneda = moneda;
  }

  public Date getMesCompra() {
    return mesCompra;
  }

  public void setMesCompra(Date mesCompra) {
    this.mesCompra = mesCompra;
  }

  public List<Productos> getProductos() {
    return productos;
  }

  public void setProductos(List<Productos> productos) {
    this.productos = productos;
  }
}
