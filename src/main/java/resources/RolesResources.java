package resources;

import dto.ApiResponse;
import dto.RolDTO;
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
import service.RolesService;
import java.util.List;
import java.util.Optional;

@Path("api/roles")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Roles", description = "Endpoints para gestión de roles")
public class RolesResources {

  @Inject
  RolesService rolesService;

  @GET
  @Operation(summary = "Listar todos los roles", description = "Obtiene una lista de todos los roles registrados")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Lista de roles obtenida exitosamente")})
  public Response listAll() {
    List<RolDTO> roles = rolesService.findAll();
    return Response.ok(ApiResponse.success("Roles obtenidos exitosamente", roles)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener rol por ID", description = "Obtiene un rol específico por su ID")
  @APIResponses(value = {@APIResponse(responseCode = "200", description = "Rol encontrado"),
      @APIResponse(responseCode = "404", description = "Rol no encontrado")})
  public Response getById(@PathParam("id") Long id) {
    Optional<RolDTO> rol = rolesService.findById(id);
    if (rol.isPresent()) {
      return Response.ok(ApiResponse.success("Rol encontrado", rol.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Rol no encontrado con ID: " + id)).build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo rol", description = "Registra un nuevo rol en el sistema")
  @APIResponses(value = {
      @APIResponse(responseCode = "201", description = "Rol creado exitosamente"),
      @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")})
  public Response create(@Valid RolDTO rolDTO) {
    try {
      RolDTO created = rolesService.create(rolDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Rol creado exitosamente", created)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear rol: " + e.getMessage())).build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un rol", description = "Actualiza los datos de un rol existente")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Rol actualizado exitosamente"),
      @APIResponse(responseCode = "404", description = "Rol no encontrado"),
      @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")})
  public Response update(@PathParam("id") Long id, @Valid RolDTO rolDTO) {
    try {
      Optional<RolDTO> updated = rolesService.update(id, rolDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Rol actualizado exitosamente", updated.get()))
            .build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Rol no encontrado con ID: " + id)).build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar rol: " + e.getMessage())).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un rol", description = "Elimina un rol del sistema")
  @APIResponses(value = {
      @APIResponse(responseCode = "200", description = "Rol eliminado exitosamente"),
      @APIResponse(responseCode = "404", description = "Rol no encontrado")})
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = rolesService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Rol eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Rol no encontrado con ID: " + id)).build();
  }
}
