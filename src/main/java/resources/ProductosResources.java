package resources;

import dto.ApiResponse;
import dto.ProductoDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import service.ProductosService;
import java.util.List;
import java.util.Optional;

@Path("api/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Productos", description = "Endpoints para gestión de productos")
public class ProductosResources {

  @Inject
  ProductosService productosService;

  @GET
  @Operation(summary = "Listar todos los productos")
  public Response listAll() {
    List<ProductoDTO> productos = productosService.findAll();
    return Response.ok(ApiResponse.success("Productos obtenidos exitosamente", productos)).build();
  }

  @GET
  @Path("{id}")
  @Operation(summary = "Obtener producto por ID")
  public Response getById(@PathParam("id") Long id) {
    Optional<ProductoDTO> producto = productosService.findById(id);
    if (producto.isPresent()) {
      return Response.ok(ApiResponse.success("Producto encontrado", producto.get())).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Producto no encontrado con ID: " + id))
        .build();
  }

  @POST
  @Transactional
  @Operation(summary = "Crear un nuevo producto")
  public Response create(@Valid ProductoDTO productoDTO) {
    try {
      ProductoDTO created = productosService.create(productoDTO);
      return Response.status(Response.Status.CREATED)
          .entity(ApiResponse.created("Producto creado exitosamente", created))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al crear producto: " + e.getMessage()))
          .build();
    }
  }

  @PUT
  @Path("{id}")
  @Transactional
  @Operation(summary = "Actualizar un producto")
  public Response update(@PathParam("id") Long id, @Valid ProductoDTO productoDTO) {
    try {
      Optional<ProductoDTO> updated = productosService.update(id, productoDTO);
      if (updated.isPresent()) {
        return Response.ok(ApiResponse.success("Producto actualizado exitosamente", updated.get())).build();
      }
      return Response.status(Response.Status.NOT_FOUND)
          .entity(ApiResponse.notFound("Producto no encontrado con ID: " + id))
          .build();
    } catch (Exception e) {
      return Response.status(Response.Status.BAD_REQUEST)
          .entity(ApiResponse.badRequest("Error al actualizar producto: " + e.getMessage()))
          .build();
    }
  }

  @DELETE
  @Path("{id}")
  @Transactional
  @Operation(summary = "Eliminar un producto")
  public Response delete(@PathParam("id") Long id) {
    boolean deleted = productosService.delete(id);
    if (deleted) {
      return Response.ok(ApiResponse.success("Producto eliminado exitosamente", null)).build();
    }
    return Response.status(Response.Status.NOT_FOUND)
        .entity(ApiResponse.notFound("Producto no encontrado con ID: " + id))
        .build();
  }
}
