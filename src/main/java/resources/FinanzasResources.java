package resources;

import dto.ApiResponse;
import dto.FinanzaDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.FinanzasService;
import java.util.List;
import java.util.Optional;

@Path("api/finanzas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Finanzas", description = "Endpoints para gestión de finanzas")
public class FinanzasResources {

  @Inject
  FinanzasService finanzasService;

  @GET
  @Operation(summary = "Listar todos los registros financieros")
  public Response listAll() {
    List<FinanzaDTO> finanzas = finanzasService.findAll();
    return Response.ok(ApiResponse.success("Registros financieros obtenidos exitosamente", finanzas)).build();
  }

  @GET
  @Path("/calcular")
  @Transactional
  @Operation(summary = "Calcular finanzas por mes y año")
  public Response calcularPorMesYAnio(
      @QueryParam("mes") Integer mes,
      @QueryParam("anio") Integer anio) {

    if (mes == null || anio == null) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Debe proporcionar los parámetros 'mes' (1-12) y 'anio'"))
          .build();
    }

    if (mes < 1 || mes > 12) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("El mes debe estar entre 1 y 12"))
          .build();
    }

    try {
      FinanzaDTO finanzas = finanzasService.calcularFinanzasPorMesYAnio(mes, anio);
      return Response.ok(ApiResponse.success(
          "Finanzas calculadas para " + mes + "/" + anio, finanzas))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
          .entity(ApiResponse.error("Error al calcular finanzas: " + e.getMessage()))
          .build();
    }
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener registro financiero por ID")
  public Response getById(@PathParam("id") Long id) {
    Optional<FinanzaDTO> finanza = finanzasService.findById(id);
    if (finanza.isPresent()) {
      return Response.ok(ApiResponse.success("Registro financiero encontrado", finanza.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Registro financiero no encontrado con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo registro financiero")
  public Response create(@Valid FinanzaDTO finanzaDTO) {
    try {
      FinanzaDTO created = finanzasService.create(finanzaDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Registro financiero creado exitosamente", created))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear registro financiero: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un registro financiero")
  public Response update(@PathParam("id") Long id, @Valid FinanzaDTO finanzaDTO) {
    try {
      Optional<FinanzaDTO> updated = finanzasService.update(id, finanzaDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Registro financiero actualizado exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Registro financiero no encontrado con ID: " + id))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar registro financiero: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un registro financiero")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = finanzasService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Registro financiero eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Registro financiero no encontrado con ID: " + id))
        .build();
  }
}
