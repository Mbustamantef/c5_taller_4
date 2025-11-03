package models;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "productos", schema = "public")
public class Productos {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_producto", nullable = false)
  private Long idProducto;

  @NotNull(message = "El título del producto es obligatorio")
  @Size(min = 3, max = 200, message = "El título debe tener entre 3 y 200 caracteres")
  @Column(name = "titulo", nullable = false, length = 200)
  private String titulo;

  @NotNull(message = "El precio de costo es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio de costo debe ser mayor a 0")
  @Column(name = "precio_costo", nullable = false)
  private Float precioCosto;

  @NotNull(message = "El precio de venta es obligatorio")
  @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
  @Column(name = "precio_venta", nullable = false)
  private Float precioVenta;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Column(name = "cantidad", nullable = false)
  private Integer cantidad;

  @NotNull(message = "La categoría es obligatoria")
  @Size(min = 3, max = 100, message = "La categoría debe tener entre 3 y 100 caracteres")
  @Column(name = "categoria", nullable = false, length = 100)
  private String categoria;

  @NotNull(message = "El estado activo es obligatorio")
  @Column(name = "activo", nullable = false)
  private Boolean activo;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
    name = "productos_depositos",
    joinColumns = @JoinColumn(name = "id_producto"),
    inverseJoinColumns = @JoinColumn(name = "id_deposito")
  )
  @JsonbTransient
  private List<Depositos> depositos = new ArrayList<>();

  @ManyToMany(mappedBy = "productos", fetch = FetchType.LAZY)
  @JsonbTransient
  private List<Ventas> ventas = new ArrayList<>();

  @NotNull(message = "La fecha de compra es obligatoria")
  @Temporal(TemporalType.DATE)
  @Column(name = "mes_compra", nullable = false)
  @JsonbDateFormat("yyyy-MM-dd")
  private Date mesCompra;


  // Getters and Setters
  public Long getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Long idProducto) {
    this.idProducto = idProducto;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Float getPrecioCosto() {
    return precioCosto;
  }

  public void setPrecioCosto(Float precioCosto) {
    this.precioCosto = precioCosto;
  }

  public Float getPrecioVenta() {
    return precioVenta;
  }

  public void setPrecioVenta(Float precioVenta) {
    this.precioVenta = precioVenta;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
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

  public Date getMesCompra() {
    return mesCompra;
  }

  public void setMesCompra(Date mesCompra) {
    this.mesCompra = mesCompra;
  }

  public List<Depositos> getDepositos() {
    return depositos;
  }

  public void setDepositos(List<Depositos> depositos) {
    this.depositos = depositos;
  }

  public List<Ventas> getVentas() {
    return ventas;
  }

  public void setVentas(List<Ventas> ventas) {
    this.ventas = ventas;
  }
}
