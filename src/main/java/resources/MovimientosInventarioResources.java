package resources;

import dto.ApiResponse;
import dto.MovimientoInventarioDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.MovimientosInventarioService;

import java.util.List;
import java.util.Optional;

@Path("api/movimientos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Movimientos de Inventario", description = "Endpoints para gestión de movimientos de inventario (entradas, salidas, transferencias, ajustes)")
public class MovimientosInventarioResources {

  @Inject
  MovimientosInventarioService movimientosService;

  @GET
  @Operation(summary = "Listar todos los movimientos", description = "Obtiene una lista de todos los movimientos de inventario")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Movimientos obtenidos exitosamente")})
  public Response listAll() {
    List<MovimientoInventarioDTO> movimientos = movimientosService.findAll();
    return Response.ok(ApiResponse.success("Movimientos obtenidos exitosamente", movimientos)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener movimiento por ID", description = "Obtiene un movimiento específico por su ID")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Movimiento encontrado"),
      @APIResponse(responseCode = "404", description = "Movimiento no encontrado")})
  public Response getById(@PathParam("id") Long id) {
    Optional<MovimientoInventarioDTO> movimiento = movimientosService.findById(id);
    if (movimiento.isPresent()) {
      return Response.ok(ApiResponse.success("Movimiento encontrado", movimiento.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Movimiento no encontrado con ID: " + id)).build();
  }

  @GET
  @Path("producto/{idProducto}")
  @Operation(summary = "Obtener movimientos por producto", description = "Obtiene todos los movimientos de un producto")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Movimientos obtenidos exitosamente")})
  public Response getByProducto(@PathParam("idProducto") Long idProducto) {
    List<MovimientoInventarioDTO> movimientos = movimientosService.findByProducto(idProducto);
    return Response.ok(ApiResponse.success("Movimientos del producto obtenidos exitosamente", movimientos)).build();
  }

  @GET
  @Path("tipo/{tipo}")
  @Operation(summary = "Obtener movimientos por tipo", description = "Obtiene todos los movimientos de un tipo específico (ENTRADA, SALIDA, TRANSFERENCIA, AJUSTE)")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Movimientos obtenidos exitosamente")})
  public Response getByTipo(@PathParam("tipo") String tipo) {
    try {
      List<MovimientoInventarioDTO> movimientos = movimientosService.findByTipo(tipo.toUpperCase());
      return Response.ok(ApiResponse.success("Movimientos de tipo " + tipo + " obtenidos exitosamente", movimientos)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Tipo de movimiento inválido: " + e.getMessage())).build();
    }
  }

  @POST
  @Transactional
  @Operation(summary = "Registrar movimiento de inventario", description = "Registra un nuevo movimiento de inventario y actualiza el stock automáticamente")
  @APIResponses(value = {
      @APIResponse(responseCode = "201", description = "Movimiento registrado exitosamente"),
      @APIResponse(responseCode = "400", description = "Datos de entrada inválidos o stock insuficiente")})
  public Response create(@Valid MovimientoInventarioDTO movimientoDTO) {
    try {
      MovimientoInventarioDTO created = movimientosService.create(movimientoDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Movimiento registrado exitosamente", created)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al registrar movimiento: " + e.getMessage())).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar movimiento", description = "Elimina un movimiento de inventario (solo auditoría, no revierte stock)")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Movimiento eliminado exitosamente"),
      @APIResponse(responseCode = "404", description = "Movimiento no encontrado")})
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = movimientosService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Movimiento eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Movimiento no encontrado con ID: " + id)).build();
  }
}

