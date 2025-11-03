package taller4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VentasResourceTest {

    private static Integer productoId;
    private static Integer depositoId;
    private static Integer clienteId;
    private static Integer ventaId;

    @BeforeAll
    static void setup() {
        // Crear depósito
        String depositoBody = """
            {
                "nombre_deposito": "Depósito Ventas Test"
            }
            """;

        depositoId = given()
            .contentType(ContentType.JSON)
            .body(depositoBody)
            .when().post("/api/depositos")
            .then().statusCode(201)
            .extract().path("data.id_deposito");

        // Crear producto
        String productoBody = """
            {
                "titulo": "Monitor Test",
                "precio_costo": 100.00,
                "precio_venta": 200.00,
                "cantidad": 0,
                "categoria": "Electrónica",
                "activo": true,
                "mes_compra": "2025-11-02"
            }
            """;

        productoId = given()
            .contentType(ContentType.JSON)
            .body(productoBody)
            .when().post("/api/productos")
            .then().statusCode(201)
            .extract().path("data.id_producto");

        // Agregar stock
        String movimientoBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoDestino": %d,
                "tipoMovimiento": "ENTRADA",
                "cantidad": 50,
                "motivo": "Stock para ventas test"
            }
            """, productoId, depositoId);

        given()
            .contentType(ContentType.JSON)
            .body(movimientoBody)
            .when().post("/api/movimientos")
            .then().statusCode(201);

        System.out.println("Setup Ventas Tests - Producto: " + productoId);
    }

    @Test
    @Order(1)
    void testCreateVentaConClienteNuevo() {
        String requestBody = String.format("""
            {
                "cantidad": 5,
                "id_producto": %d,
                "nombre_cliente": "Juan Test",
                "apellido_cliente": "Pérez Test",
                "ci_cliente": "1234567-T",
                "correo_cliente": "juan.test@example.com"
            }
            """, productoId);

        ventaId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/ventas")
            .then()
            .statusCode(201)
            .body("code", is(201))
            .body("data.cantidad", is(5))
            .body("data.monto", is(1000.0f)) // 5 × 200
            .body("data.mes", notNullValue())
            .body("data.nombre_producto", is("Monitor Test"))
            .body("data.id_cliente", notNullValue())
            .body("data.nombre_cliente", is("Juan Test"))
            .extract().path("data.id_ventas");

        clienteId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/ventas")
            .then()
            .extract().path("data.id_cliente");

        System.out.println("Venta creada con ID: " + ventaId + ", Cliente: " + clienteId);
    }

    @Test
    @Order(2)
    void testCreateVentaConClienteExistente() {
        String requestBody = String.format("""
            {
                "cantidad": 3,
                "id_producto": %d,
                "id_cliente": %d
            }
            """, productoId, clienteId);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/ventas")
            .then()
            .statusCode(201)
            .body("data.cantidad", is(3))
            .body("data.monto", is(600.0f)) // 3 × 200
            .body("data.id_cliente", is(clienteId))
            .body("data.nombre_cliente", is("Juan Test"));
    }

    @Test
    @Order(3)
    void testGetAllVentas() {
        given()
            .when().get("/api/ventas")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(4)
    void testGetVentaById() {
        given()
            .pathParam("id", ventaId)
            .when().get("/api/ventas/{id}")
            .then()
            .statusCode(200)
            .body("data.id_ventas", is(ventaId))
            .body("data.monto", notNullValue())
            .body("data.cantidad", notNullValue());
    }

    @Test
    @Order(5)
    void testCreateVentaSinDatosCliente() {
        String requestBody = String.format("""
            {
                "cantidad": 2,
                "id_producto": %d
            }
            """, productoId);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/ventas")
            .then()
            .statusCode(400)
            .body("message", containsString("cliente"));
    }

    @Test
    @Order(6)
    void testMontoCalculadoAutomaticamente() {
        String requestBody = String.format("""
            {
                "cantidad": 10,
                "id_producto": %d,
                "id_cliente": %d
            }
            """, productoId, clienteId);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/ventas")
            .then()
            .statusCode(201)
            .body("data.monto", is(2000.0f)) // 10 × 200
            .body("data.mes", notNullValue());
    }

    @AfterAll
    static void cleanup() {
        given().pathParam("id", productoId).when().delete("/api/productos/{id}");
        given().pathParam("id", depositoId).when().delete("/api/depositos/{id}");
        if (clienteId != null) {
            given().pathParam("id", clienteId).when().delete("/api/clientes/{id}");
        }
    }
}

