package dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Schema(description = "DTO para transferencia de datos de Stock")
public class StockDTO {

  @Schema(description = "ID del stock", readOnly = true)
  private Long idStock;

  @NotNull(message = "El ID del producto es obligatorio")
  @Schema(description = "ID del producto", required = true)
  private Long idProducto;

  @Schema(description = "Nombre del producto", readOnly = true)
  private String nombreProducto;

  @NotNull(message = "El ID del depósito es obligatorio")
  @Schema(description = "ID del depósito", required = true)
  private Long idDeposito;

  @Schema(description = "Nombre del depósito", readOnly = true)
  private String nombreDeposito;

  @NotNull(message = "La cantidad es obligatoria")
  @Min(value = 0, message = "La cantidad no puede ser negativa")
  @Schema(description = "Cantidad en stock", required = true)
  private Integer cantidad;

  @Min(value = 0, message = "El stock mínimo no puede ser negativo")
  @Schema(description = "Stock mínimo")
  private Integer stockMinimo;

  @Schema(description = "Precio de costo del producto", readOnly = true)
  private Float precioCosto;

  @Schema(description = "Precio de venta del producto", readOnly = true)
  private Float precioVenta;

  @Schema(description = "Valor total del stock (cantidad × precio costo)", readOnly = true)
  private Float valorTotal;

  // Getters and Setters
  public Long getIdStock() {
    return idStock;
  }

  public void setIdStock(Long idStock) {
    this.idStock = idStock;
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

  public Integer getCantidad() {
    return cantidad;
  }

  public void setCantidad(Integer cantidad) {
    this.cantidad = cantidad;
  }

  public Integer getStockMinimo() {
    return stockMinimo;
  }

  public void setStockMinimo(Integer stockMinimo) {
    this.stockMinimo = stockMinimo;
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
}

