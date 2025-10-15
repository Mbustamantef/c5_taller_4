package resources;

import dto.ApiResponse;
import dto.ProveedorDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.ProveedoresService;
import java.util.List;
import java.util.Optional;

@Path("api/proveedores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Proveedores", description = "Endpoints para gestión de proveedores")
public class ProveedoresResources {

  @Inject
  ProveedoresService proveedoresService;

  @GET
  @Operation(summary = "Listar todos los proveedores")
  public Response listAll() {
    List<ProveedorDTO> proveedores = proveedoresService.findAll();
    return Response.ok(ApiResponse.success("Proveedores obtenidos exitosamente", proveedores)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener proveedor por ID")
  public Response getById(@PathParam("id") Long id) {
    Optional<ProveedorDTO> proveedor = proveedoresService.findById(id);
    if (proveedor.isPresent()) {
      return Response.ok(ApiResponse.success("Proveedor encontrado", proveedor.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Proveedor no encontrado con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo proveedor")
  public Response create(@Valid ProveedorDTO proveedorDTO) {
    try {
      ProveedorDTO created = proveedoresService.create(proveedorDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Proveedor creado exitosamente", created))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear proveedor: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un proveedor")
  public Response update(@PathParam("id") Long id, @Valid ProveedorDTO proveedorDTO) {
    try {
      Optional<ProveedorDTO> updated = proveedoresService.update(id, proveedorDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Proveedor actualizado exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Proveedor no encontrado con ID: " + id))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar proveedor: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un proveedor")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = proveedoresService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Proveedor eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Proveedor no encontrado con ID: " + id))
        .build();
  }
}
