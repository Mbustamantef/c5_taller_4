package dto;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para reportes de ventas")
public class ReporteVentasDTO {

  @Schema(description = "ID del cliente")
  private Long idCliente;

  @Schema(description = "Nombre del cliente")
  private String nombreCliente;

  @Schema(description = "ID del producto")
  private Long idProducto;

  @Schema(description = "Nombre del producto")
  private String nombreProducto;

  @Schema(description = "Categoría del producto")
  private String categoria;

  @Schema(description = "Cantidad total vendida")
  private Integer cantidadTotal;

  @Schema(description = "Monto total de ventas")
  private Float montoTotal;

  @Schema(description = "Número de ventas")
  private Long numeroVentas;

  @Schema(description = "Periodo del reporte")
  private String periodo;

  @Schema(description = "Margen bruto total")
  private Float margenBruto;

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

  public Integer getCantidadTotal() {
    return cantidadTotal;
  }

  public void setCantidadTotal(Integer cantidadTotal) {
    this.cantidadTotal = cantidadTotal;
  }

  public Float getMontoTotal() {
    return montoTotal;
  }

  public void setMontoTotal(Float montoTotal) {
    this.montoTotal = montoTotal;
  }

  public Long getNumeroVentas() {
    return numeroVentas;
  }

  public void setNumeroVentas(Long numeroVentas) {
    this.numeroVentas = numeroVentas;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public Float getMargenBruto() {
    return margenBruto;
  }

  public void setMargenBruto(Float margenBruto) {
    this.margenBruto = margenBruto;
  }
}

