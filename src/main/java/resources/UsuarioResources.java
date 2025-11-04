package resources;

import dto.ApiResponse;
import dto.UsuariosDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import service.UsuariosService;

@Path("api/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Usuarios", description = "Endpoints para gestión de usuarios")
public class UsuarioResources {

  @Inject
  UsuariosService usuariosService;

  @GET
  @Operation(summary = "Listar todos los usuarios", description = "Obtiene toda la lista de los usuarios")
  @APIResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente")
  public Object listAll() {
    return usuariosService.findAll();
  }

  @POST
  @Transactional
  @Operation(summary = "Creacion de los usuarios", description = "Crea los usuarios")
  @APIResponses(value = {
      @APIResponse(responseCode = "201", description = "Usuario creado exitosamente"),
      @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")})
  public Response create(
      @RequestBody(
          description = "Usuario a crear",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UsuariosDTO.class)
          )
      )
      @Valid UsuariosDTO usuariosDTO) {
    try {
      UsuariosDTO created = usuariosService.create(usuariosDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Usuario creado exitosamente", created)).build();
    } catch (Exception e) {
      return Response.status(Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear usuario: " + e.getMessage())).build();
    }
  }
}
