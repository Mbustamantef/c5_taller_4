package resources;

import dto.ApiResponse;
import dto.DepositoDTO;
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
import service.DepositosService;
import java.util.List;
import java.util.Optional;

@Path("api/depositos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Depósitos", description = "Endpoints para gestión de depósitos")
public class DepositosResources {

  @Inject
  DepositosService depositosService;

  @GET
  @Operation(summary = "Listar todos los depósitos")
  public Response listAll() {
    List<DepositoDTO> depositos = depositosService.findAll();
    return Response.ok(ApiResponse.success("Depósitos obtenidos exitosamente", depositos)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener depósito por ID")
  public Response getById(@PathParam("id") Long id) {
    Optional<DepositoDTO> deposito = depositosService.findById(id);
    if (deposito.isPresent()) {
      return Response.ok(ApiResponse.success("Depósito encontrado", deposito.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Depósito no encontrado con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo depósito")
  public Response create(@Valid DepositoDTO depositoDTO) {
    try {
      DepositoDTO created = depositosService.create(depositoDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Depósito creado exitosamente", created))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear depósito: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un depósito")
  public Response update(@PathParam("id") Long id, @Valid DepositoDTO depositoDTO) {
    try {
      Optional<DepositoDTO> updated = depositosService.update(id, depositoDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Depósito actualizado exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Depósito no encontrado con ID: " + id))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar depósito: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un depósito")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = depositosService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Depósito eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Depósito no encontrado con ID: " + id))
        .build();
  }
}
