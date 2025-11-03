package resources;

import dto.ApiResponse;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.ReportesService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Path("api/reportes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Reportes", description = "Endpoints para reportes de inventario, ventas y análisis")
public class ReportesResources {

  @Inject
  ReportesService reportesService;

  private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

  @GET
  @Path("stock-valorizado/deposito")
  @Operation(summary = "Stock valorizado por depósito", description = "Obtiene el stock valorizado de todos los depósitos o de uno específico")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente")})
  public Response getStockValorizadoPorDeposito(@QueryParam("idDeposito") Long idDeposito) {
    var reporte = reportesService.getStockValorizadoPorDeposito(idDeposito);
    return Response.ok(ApiResponse.success("Reporte de stock valorizado por depósito", reporte)).build();
  }

  @GET
  @Path("stock-valorizado/categoria")
  @Operation(summary = "Stock valorizado por categoría", description = "Obtiene el stock valorizado por categoría de productos")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente")})
  public Response getStockValorizadoPorCategoria(@QueryParam("categoria") String categoria) {
    var reporte = reportesService.getStockValorizadoPorCategoria(categoria);
    return Response.ok(ApiResponse.success("Reporte de stock valorizado por categoría", reporte)).build();
  }

  @GET
  @Path("stock-bajo-minimo")
  @Operation(summary = "Productos con stock bajo mínimo", description = "Obtiene los productos cuyo stock está por debajo del mínimo establecido")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente")})
  public Response getProductosStockBajoMinimo() {
    var reporte = reportesService.getProductosStockBajoMinimo();
    return Response.ok(ApiResponse.success("Productos con stock bajo mínimo", reporte)).build();
  }

  @GET
  @Path("productos-sin-movimiento")
  @Operation(summary = "Productos sin movimiento", description = "Obtiene los productos sin movimiento en los últimos X días")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente")})
  public Response getProductosSinMovimiento(@QueryParam("dias") @DefaultValue("30") Integer dias) {
    var reporte = reportesService.getProductosSinMovimiento(dias);
    return Response.ok(ApiResponse.success("Productos sin movimiento en " + dias + " días", reporte)).build();
  }

  @GET
  @Path("ventas/cliente")
  @Operation(summary = "Ventas por cliente", description = "Obtiene el reporte de ventas agrupado por cliente")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente")})
  public Response getVentasPorCliente(@QueryParam("idCliente") Long idCliente) {
    var reporte = reportesService.getVentasPorCliente(idCliente);
    return Response.ok(ApiResponse.success("Reporte de ventas por cliente", reporte)).build();
  }

  @GET
  @Path("ventas/periodo")
  @Operation(summary = "Ventas por periodo", description = "Obtiene el reporte de ventas en un periodo específico")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente"),
      @APIResponse(responseCode = "400", description = "Formato de fecha inválido")})
  public Response getVentasPorPeriodo(
      @QueryParam("fechaInicio") String fechaInicio,
      @QueryParam("fechaFin") String fechaFin) {
    try {
      Date inicio = fechaInicio != null ? dateFormat.parse(fechaInicio) : new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000);
      Date fin = fechaFin != null ? dateFormat.parse(fechaFin) : new Date();

      var reporte = reportesService.getVentasPorPeriodo(inicio, fin);
      return Response.ok(ApiResponse.success("Reporte de ventas por periodo", reporte)).build();
    } catch (ParseException e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Formato de fecha inválido. Use: yyyy-MM-dd")).build();
    }
  }

  @GET
  @Path("rotacion-inventario")
  @Operation(summary = "Rotación de inventario", description = "Obtiene la rotación de inventario de todos los productos")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Reporte generado exitosamente")})
  public Response getRotacionInventario() {
    var reporte = reportesService.getRotacionInventario();
    return Response.ok(ApiResponse.success("Reporte de rotación de inventario", reporte)).build();
  }
}

