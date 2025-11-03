package dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para reportes de stock valorizado")
public class ReporteStockValorizadoDTO {

  @Schema(description = "ID del depósito")
  private Long idDeposito;

  @Schema(description = "Nombre del depósito")
  private String nombreDeposito;

  @Schema(description = "ID del producto")
  private Long idProducto;

  @Schema(description = "Nombre del producto")
  private String nombreProducto;

  @Schema(description = "Categoría del producto")
  private String categoria;

  @Schema(description = "Cantidad en stock")
  private Integer cantidad;

  @Schema(description = "Precio de costo unitario")
  private Float precioCosto;

  @Schema(description = "Precio de venta unitario")
  private Float precioVenta;

  @Schema(description = "Valor total del stock (cantidad × precio costo)")
  private Float valorTotal;

  @Schema(description = "Margen potencial (cantidad × (precio venta - precio costo))")
  private Float margenPotencial;

  // Getters and Setters
  public Long getIdDeposito() {
    return idDeposito;
  }

  public void setIdDeposito(Long idDeposito) {
    this.idDeposito = idDeposito;
  }

  public String getNombreDeposito() {
    return nombreDeposito;
  }

  public void setNombreDeposito(String nombreDeposito) {
    this.nombreDeposito = nombreDeposito;
  }

  public Long getIdProducto() {
    return idProducto;
  }

  public void setIdProducto(Long idProducto) {
    this.idProducto = idProducto;
  }

  public String getNombreProducto() {
    return nombreProducto;
  }

  public void setNombreProducto(String nombreProducto) {
    this.nombreProducto = nombreProducto;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
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

  public Float getValorTotal() {
    return valorTotal;
  }

  public void setValorTotal(Float valorTotal) {
    this.valorTotal = valorTotal;
  }

  public Float getMargenPotencial() {
    return margenPotencial;
  }

  public void setMargenPotencial(Float margenPotencial) {
    this.margenPotencial = margenPotencial;
  }
}

