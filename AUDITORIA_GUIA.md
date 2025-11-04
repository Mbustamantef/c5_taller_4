# Sistema de Auditoría - Guía de Implementación

## 📋 Resumen

Se ha implementado un sistema completo de auditoría que:
- ✅ Registra quién creó y quién modificó cada registro
- ✅ Registra la fecha/hora de creación y modificación
- ✅ Valida que el usuario tenga el rol "Creacion" para hacer cambios
- ✅ Se aplica a TODOS los modelos del sistema

## 🏗️ Arquitectura

### 1. Clase Base Auditable
**Archivo:** `core/generic/Auditable.java`

Todos los modelos ahora heredan de esta clase que proporciona:
- `creado_por` - Usuario que creó el registro
- `fecha_creacion` - Fecha y hora de creación
- `modificado_por` - Usuario que modificó el registro
- `fecha_modificacion` - Fecha y hora de última modificación

### 2. Servicio de Auditoría
**Archivo:** `service/AuditoriaService.java`

Funciones principales:
- `validarPermisoCreacion(Long idUsuario)` - Valida que el usuario tenga rol "Creacion"
- `aplicarAuditoriaCreacion(Auditable entidad, Long idUsuario)` - Aplica auditoría en creación
- `aplicarAuditoriaModificacion(Auditable entidad, Long idUsuario)` - Aplica auditoría en modificación

### 3. Excepción Personalizada
**Archivo:** `core/exceptions/UnauthorizedException.java`

Se lanza cuando un usuario no tiene permisos.

## 📊 Base de Datos

### Paso 1: Ejecutar Script SQL
```sql
-- Ejecutar: src/main/resources/db/migration/add_audit_columns.sql
```

Este script agrega las siguientes columnas a TODAS las tablas:
- `creado_por` (BIGINT, FK a usuarios)
- `fecha_creacion` (TIMESTAMP)
- `modificado_por` (BIGINT, FK a usuarios)
- `fecha_modificacion` (TIMESTAMP)

### Paso 2: Asegurar que existe el rol "Creacion"
```sql
-- Verificar o crear el rol
INSERT INTO roles (descripcion) VALUES ('Creacion')
ON CONFLICT DO NOTHING;

-- Asignar el rol a un usuario
UPDATE usuarios SET id_rol = (SELECT id_rol FROM roles WHERE descripcion = 'Creacion')
WHERE id_usuario = 1;
```

## 🔧 Modelos Actualizados

Todos los siguientes modelos ahora extienden `Auditable`:
- ✅ Productos
- ✅ Clientes
- ✅ Depositos
- ✅ Finanzas
- ✅ Stock
- ✅ Ventas
- ✅ Responsables
- ✅ MovimientosInventario

## 📝 Ejemplo: ProductosService

### Antes:
```java
public ProductoDTO create(ProductoDTO dto) {
    Productos producto = toEntity(dto);
    productosRepository.persist(producto);
    return toDTO(producto);
}
```

### Después:
```java
@Inject
AuditoriaService auditoriaService;

public ProductoDTO create(ProductoDTO dto, Long idUsuario) {
    Productos producto = toEntity(dto);
    auditoriaService.aplicarAuditoriaCreacion(producto, idUsuario);
    productosRepository.persist(producto);
    return toDTO(producto);
}

public Optional<ProductoDTO> update(Long id, ProductoDTO dto, Long idUsuario) {
    Optional<Productos> existing = productosRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
        return Optional.empty();
    }
    
    Productos producto = existing.get();
    auditoriaService.aplicarAuditoriaModificacion(producto, idUsuario);
    
    // ... actualizar campos ...
    
    return Optional.of(toDTO(producto));
}
```

## 🌐 Ejemplo: ProductosResources

### Crear Producto
```java
@POST
@Transactional
@Operation(summary = "Crear un nuevo producto (requiere rol 'Creacion')")
public Response create(
    @HeaderParam("X-User-Id") @Parameter(required = true) Long userId,
    @Valid ProductoDTO productoDTO) {
    try {
        ProductoDTO created = productosService.create(productoDTO, userId);
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.created("Producto creado exitosamente", created))
            .build();
    } catch (UnauthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN)
            .entity(ApiResponse.error(e.getMessage()))
            .build();
    }
}
```

## 🚀 Uso de la API

### Crear un Producto (ejemplo)
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "titulo": "Laptop HP",
    "precio_costo": 800.0,
    "precio_venta": 1200.0,
    "cantidad": 10,
    "categoria": "Electrónica",
    "activo": true,
    "mes_compra": "2024-11-01"
  }'
```

### Respuesta Exitosa (201 Created)
```json
{
  "status": "created",
  "message": "Producto creado exitosamente",
  "data": {
    "id_producto": 5,
    "titulo": "Laptop HP",
    "precio_costo": 800.0,
    "precio_venta": 1200.0,
    "cantidad": 10,
    "categoria": "Electrónica",
    "activo": true,
    "mes_compra": "2024-11-01"
  }
}
```

### Respuesta de Error - Sin Permisos (403 Forbidden)
```json
{
  "status": "error",
  "message": "No tiene permisos para realizar esta operación. Se requiere el rol 'Creacion'",
  "data": null
}
```

### Respuesta de Error - Usuario No Encontrado (403 Forbidden)
```json
{
  "status": "error",
  "message": "Usuario no encontrado con ID: 999",
  "data": null
}
```

### Respuesta de Error - Sin Header (403 Forbidden)
```json
{
  "status": "error",
  "message": "Se requiere autenticación. Debe proporcionar el ID del usuario.",
  "data": null
}
```

### Actualizar un Producto
```bash
curl -X PUT http://localhost:8080/api/productos/5 \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 1" \
  -d '{
    "titulo": "Laptop HP Actualizada",
    "precio_costo": 850.0,
    "precio_venta": 1250.0,
    "cantidad": 15,
    "categoria": "Electrónica",
    "activo": true,
    "mes_compra": "2024-11-01"
  }'
```

## 📋 Checklist para Implementar en Otros Services

Para cada servicio (ClientesService, VentasService, etc.):

1. ✅ Inyectar `AuditoriaService`
```java
@Inject
AuditoriaService auditoriaService;
```

2. ✅ Modificar método `create` para aceptar `Long idUsuario`
```java
public XxxDTO create(XxxDTO dto, Long idUsuario) {
    Xxx entidad = toEntity(dto);
    auditoriaService.aplicarAuditoriaCreacion(entidad, idUsuario);
    repository.persist(entidad);
    return toDTO(entidad);
}
```

3. ✅ Modificar método `update` para aceptar `Long idUsuario`
```java
public Optional<XxxDTO> update(Long id, XxxDTO dto, Long idUsuario) {
    Optional<Xxx> existing = repository.findByIdOptional(id);
    if (existing.isEmpty()) {
        return Optional.empty();
    }
    
    Xxx entidad = existing.get();
    auditoriaService.aplicarAuditoriaModificacion(entidad, idUsuario);
    
    // ... actualizar campos ...
    
    return Optional.of(toDTO(entidad));
}
```

4. ✅ Actualizar el Resource correspondiente
```java
import core.exceptions.UnauthorizedException;

@POST
@Transactional
public Response create(
    @HeaderParam("X-User-Id") Long userId,
    @Valid XxxDTO dto) {
    try {
        XxxDTO created = service.create(dto, userId);
        return Response.status(Response.Status.CREATED)
            .entity(ApiResponse.created("...", created))
            .build();
    } catch (UnauthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN)
            .entity(ApiResponse.error(e.getMessage()))
            .build();
    }
}

@PUT
@Path("{id}")
@Transactional
public Response update(
    @PathParam("id") Long id,
    @HeaderParam("X-User-Id") Long userId,
    @Valid XxxDTO dto) {
    try {
        Optional<XxxDTO> updated = service.update(id, dto, userId);
        // ... resto del código
    } catch (UnauthorizedException e) {
        return Response.status(Response.Status.FORBIDDEN)
            .entity(ApiResponse.error(e.getMessage()))
            .build();
    }
}
```

## 🔍 Consultar Información de Auditoría

Para ver quién creó o modificó un registro, se puede consultar directamente la base de datos:

```sql
-- Ver auditoría de productos
SELECT 
    p.id_producto,
    p.titulo,
    uc.nombre_usuario as creado_por_usuario,
    p.fecha_creacion,
    um.nombre_usuario as modificado_por_usuario,
    p.fecha_modificacion
FROM productos p
LEFT JOIN usuarios uc ON p.creado_por = uc.id_usuario
LEFT JOIN usuarios um ON p.modificado_por = um.id_usuario;
```

## ⚠️ Notas Importantes

1. **Header obligatorio**: Todos los endpoints de creación y actualización requieren el header `X-User-Id`
2. **Rol requerido**: El usuario debe tener el rol con descripción exacta "Creacion"
3. **Fechas automáticas**: Las fechas se establecen automáticamente mediante los hooks `@PrePersist` y `@PreUpdate`
4. **Validación**: La validación ocurre ANTES de persistir, si falla se lanza `UnauthorizedException`

## 🎯 Beneficios

- ✅ Trazabilidad completa de cambios
- ✅ Seguridad mediante validación de roles
- ✅ Auditoría automática sin código duplicado
- ✅ Información histórica de quién y cuándo modificó registros
- ✅ Fácil de extender a nuevos modelos

