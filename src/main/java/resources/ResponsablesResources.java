package resources;

import dto.ApiResponse;
import dto.ResponsableDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.ResponsablesService;
import java.util.List;
import java.util.Optional;

@Path("api/responsables")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Responsables", description = "Endpoints para gestión de responsables")
public class ResponsablesResources {

  @Inject
  ResponsablesService responsablesService;

  @GET
  @Operation(summary = "Listar todos los responsables")
  public Response listAll() {
    List<ResponsableDTO> responsables = responsablesService.findAll();
    return Response.ok(ApiResponse.success("Responsables obtenidos exitosamente", responsables)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener responsable por ID")
  public Response getById(@PathParam("id") Long id) {
    Optional<ResponsableDTO> responsable = responsablesService.findById(id);
    if (responsable.isPresent()) {
      return Response.ok(ApiResponse.success("Responsable encontrado", responsable.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Responsable no encontrado con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo responsable")
  public Response create(@Valid ResponsableDTO responsableDTO) {
    try {
      ResponsableDTO created = responsablesService.create(responsableDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Responsable creado exitosamente", created))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear responsable: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un responsable")
  public Response update(@PathParam("id") Long id, @Valid ResponsableDTO responsableDTO) {
    try {
      Optional<ResponsableDTO> updated = responsablesService.update(id, responsableDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Responsable actualizado exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Responsable no encontrado con ID: " + id))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar responsable: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un responsable")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = responsablesService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Responsable eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Responsable no encontrado con ID: " + id))
        .build();
  }
}
