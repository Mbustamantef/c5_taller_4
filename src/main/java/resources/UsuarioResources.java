package resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
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
}
