package resources;

import dto.ApiResponse;
import dto.VentaDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.VentasService;
import java.util.List;
import java.util.Optional;

@Path("api/ventas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Ventas", description = "Endpoints para gestión de ventas")
public class VentasResources {

  @Inject
  VentasService ventasService;

  @GET
  @Operation(summary = "Listar todas las ventas")
  public Response listAll() {
    List<VentaDTO> ventas = ventasService.findAll();
    return Response.ok(ApiResponse.success("Ventas obtenidas exitosamente", ventas)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener venta por ID")
  public Response getById(@PathParam("id") Long id) {
    Optional<VentaDTO> venta = ventasService.findById(id);
    if (venta.isPresent()) {
      return Response.ok(ApiResponse.success("Venta encontrada", venta.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Venta no encontrada con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear una nueva venta")
  public Response create(@Valid VentaDTO ventaDTO) {
    try {
      VentaDTO created = ventasService.create(ventaDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Venta creada exitosamente", created))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear venta: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar una venta")
  public Response update(@PathParam("id") Long id, @Valid VentaDTO ventaDTO) {
    try {
      Optional<VentaDTO> updated = ventasService.update(id, ventaDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Venta actualizada exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Venta no encontrada con ID: " + id))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar venta: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar una venta")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = ventasService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Venta eliminada exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Venta no encontrada con ID: " + id))
        .build();
  }
}
