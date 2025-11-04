package resources;

import core.exceptions.UnauthorizedException;
import dto.ApiResponse;
import dto.ClienteDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.ClientesService;
import java.util.List;
import java.util.Optional;

@Path("api/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Clientes", description = "Endpoints para gestión de clientes")
public class ClientesResources {

  @Inject
  ClientesService clientesService;

  @GET
  @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista de todos los clientes registrados")
  @APIResponses(value = {
    @APIResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
  })
  public Response listAll() {
    List<ClienteDTO> clientes = clientesService.findAll();
    return Response.ok(ApiResponse.success("Clientes obtenidos exitosamente", clientes)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener cliente por ID", description = "Obtiene un cliente específico por su ID")
  @APIResponses(value = {
    @APIResponse(responseCode = "200", description = "Cliente encontrado"),
    @APIResponse(responseCode = "404", description = "Cliente no encontrado")
  })
  public Response getById(@PathParam("id") Long id) {
    Optional<ClienteDTO> cliente = clientesService.findById(id);
    if (cliente.isPresent()) {
      return Response.ok(ApiResponse.success("Cliente encontrado", cliente.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Cliente no encontrado con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo cliente (requiere rol 'Creacion')", description = "Registra un nuevo cliente en el sistema")
  @APIResponses(value = {
    @APIResponse(responseCode = "201", description = "Cliente creado exitosamente"),
    @APIResponse(responseCode = "400", description = "Datos de entrada inválidos"),
    @APIResponse(responseCode = "403", description = "Sin permisos")
  })
  public Response create(
      @HeaderParam("X-User-Id") @Parameter(description = "ID del usuario que realiza la operación", required = true) Long userId,
      @Valid ClienteDTO clienteDTO) {
    try {
      ClienteDTO created = clientesService.create(clienteDTO, userId);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Cliente creado exitosamente", created))
          .build();
    } catch (UnauthorizedException e) {
      return Response.status(Response.Status.FORBIDDEN)
          .entity(ApiResponse.error(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear cliente: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un cliente (requiere rol 'Creacion')", description = "Actualiza los datos de un cliente existente")
  @APIResponses(value = {
    @APIResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
    @APIResponse(responseCode = "404", description = "Cliente no encontrado"),
    @APIResponse(responseCode = "400", description = "Datos de entrada inválidos"),
    @APIResponse(responseCode = "403", description = "Sin permisos")
  })
  public Response update(
      @PathParam("id") Long id,
      @HeaderParam("X-User-Id") @Parameter(description = "ID del usuario que realiza la operación", required = true) Long userId,
      @Valid ClienteDTO clienteDTO) {
    try {
      Optional<ClienteDTO> updated = clientesService.update(id, clienteDTO, userId);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Cliente actualizado exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Cliente no encontrado con ID: " + id))
          .build();
    } catch (UnauthorizedException e) {
      return Response.status(Response.Status.FORBIDDEN)
          .entity(ApiResponse.error(e.getMessage()))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar cliente: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un cliente", description = "Elimina un cliente del sistema")
  @APIResponses(value = {
    @APIResponse(responseCode = "200", description = "Cliente eliminado exitosamente"),
    @APIResponse(responseCode = "404", description = "Cliente no encontrado")
  })
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = clientesService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Cliente eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Cliente no encontrado con ID: " + id))
        .build();
  }
}

