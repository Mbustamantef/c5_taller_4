package resources;

import dto.ApiResponse;
import dto.StockDTO;
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
import service.StockService;

import java.util.List;
import java.util.Optional;

@Path("api/stock")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Stock", description = "Endpoints para gestión de stock de productos en depósitos")
public class StockResources {

  @Inject
  StockService stockService;

  @GET
  @Operation(summary = "Listar todo el stock", description = "Obtiene una lista de todo el stock en todos los depósitos")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Stock obtenido exitosamente")})
  public Response listAll() {
    List<StockDTO> stock = stockService.findAll();
    return Response.ok(ApiResponse.success("Stock obtenido exitosamente", stock)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener stock por ID", description = "Obtiene un registro de stock específico por su ID")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Stock encontrado"),
      @APIResponse(responseCode = "404", description = "Stock no encontrado")})
  public Response getById(@PathParam("id") Long id) {
    Optional<StockDTO> stock = stockService.findById(id);
    if (stock.isPresent()) {
      return Response.ok(ApiResponse.success("Stock encontrado", stock.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Stock no encontrado con ID: " + id)).build();
  }

  @GET
  @Path("producto/{idProducto}")
  @Operation(summary = "Obtener stock por producto", description = "Obtiene todo el stock de un producto en todos los depósitos")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Stock obtenido exitosamente")})
  public Response getByProducto(@PathParam("idProducto") Long idProducto) {
    List<StockDTO> stock = stockService.findByProducto(idProducto);
    return Response.ok(ApiResponse.success("Stock del producto obtenido exitosamente", stock)).build();
  }

  @GET
  @Path("deposito/{idDeposito}")
  @Operation(summary = "Obtener stock por depósito", description = "Obtiene todo el stock de un depósito")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Stock obtenido exitosamente")})
  public Response getByDeposito(@PathParam("idDeposito") Long idDeposito) {
    List<StockDTO> stock = stockService.findByDeposito(idDeposito);
    return Response.ok(ApiResponse.success("Stock del depósito obtenido exitosamente", stock)).build();
  }

  @GET
  @Path("bajo-minimo")
  @Operation(summary = "Obtener productos con stock bajo mínimo", description = "Obtiene todos los productos cuyo stock está por debajo del mínimo establecido")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Productos con stock bajo obtenidos exitosamente")})
  public Response getStockBajoMinimo() {
    List<StockDTO> stock = stockService.findStockBajoMinimo();
    return Response.ok(ApiResponse.success("Productos con stock bajo mínimo", stock)).build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear registro de stock", description = "Crea un nuevo registro de stock para un producto en un depósito")
  @APIResponses(value = {
      @APIResponse(responseCode = "201", description = "Stock creado exitosamente"),
      @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")})
  public Response create(@Valid StockDTO stockDTO) {
    try {
      StockDTO created = stockService.create(stockDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Stock creado exitosamente", created)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear stock: " + e.getMessage())).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar stock", description = "Actualiza la cantidad de stock")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Stock actualizado exitosamente"),
      @APIResponse(responseCode = "404", description = "Stock no encontrado"),
      @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")})
  public Response update(@PathParam("id") Long id, @Valid StockDTO stockDTO) {
    try {
      Optional<StockDTO> updated = stockService.update(id, stockDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Stock actualizado exitosamente", updated.get()))
            .build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Stock no encontrado con ID: " + id)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar stock: " + e.getMessage())).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar registro de stock", description = "Elimina un registro de stock")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Stock eliminado exitosamente"),
      @APIResponse(responseCode = "404", description = "Stock no encontrado")})
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = stockService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Stock eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Stock no encontrado con ID: " + id)).build();
  }
}

