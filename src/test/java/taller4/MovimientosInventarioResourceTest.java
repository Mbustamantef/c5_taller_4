package taller4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MovimientosInventarioResourceTest {

    private static Integer productoId;
    private static Integer deposito1Id;
    private static Integer deposito2Id;
    private static Integer movimientoId;

    @BeforeAll
    static void setup() {
        // Crear depósitos de prueba
        String depositoBody1 = """
            {
                "nombre_deposito": "Depósito Origen Test"
            }
            """;

        deposito1Id = given()
            .contentType(ContentType.JSON)
            .body(depositoBody1)
            .when().post("/api/depositos")
            .then().statusCode(201)
            .extract().path("data.id_deposito");

        String depositoBody2 = """
            {
                "nombre_deposito": "Depósito Destino Test"
            }
            """;

        deposito2Id = given()
            .contentType(ContentType.JSON)
            .body(depositoBody2)
            .when().post("/api/depositos")
            .then().statusCode(201)
            .extract().path("data.id_deposito");

        // Crear producto de prueba
        String productoBody = """
            {
                "titulo": "Mouse Test",
                "precio_costo": 10.00,
                "precio_venta": 20.00,
                "cantidad": 0,
                "categoria": "Accesorios",
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

        System.out.println("Setup completado - Producto: " + productoId + ", Depósitos: " + deposito1Id + ", " + deposito2Id);
    }

    @Test
    @Order(1)
    void testCreateMovimientoEntrada() {
        String requestBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoDestino": %d,
                "tipoMovimiento": "ENTRADA",
                "cantidad": 100,
                "motivo": "Compra inicial de prueba"
            }
            """, productoId, deposito1Id);

        movimientoId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/movimientos")
            .then()
            .statusCode(201)
            .body("code", is(201))
            .body("data.tipoMovimiento", is("ENTRADA"))
            .body("data.cantidad", is(100))
            .body("data.nombreProducto", notNullValue())
            .body("data.nombreDepositoDestino", notNullValue())
            .extract().path("data.idMovimiento");

        System.out.println("Movimiento ENTRADA creado con ID: " + movimientoId);
    }

    @Test
    @Order(2)
    void testVerificarStockDespuesDeEntrada() {
        given()
            .pathParam("idProducto", productoId)
            .when().get("/api/stock/producto/{idProducto}")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0))
            .body("data[0].cantidad", is(100));
    }

    @Test
    @Order(3)
    void testCreateMovimientoSalida() {
        String requestBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoOrigen": %d,
                "tipoMovimiento": "SALIDA",
                "cantidad": 20,
                "motivo": "Venta de prueba"
            }
            """, productoId, deposito1Id);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/movimientos")
            .then()
            .statusCode(201)
            .body("data.tipoMovimiento", is("SALIDA"))
            .body("data.cantidad", is(20));
    }

    @Test
    @Order(4)
    void testVerificarStockDespuesDeSalida() {
        given()
            .pathParam("idProducto", productoId)
            .when().get("/api/stock/producto/{idProducto}")
            .then()
            .statusCode(200)
            .body("data[0].cantidad", is(80)); // 100 - 20
    }

    @Test
    @Order(5)
    void testCreateMovimientoTransferencia() {
        String requestBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoOrigen": %d,
                "idDepositoDestino": %d,
                "tipoMovimiento": "TRANSFERENCIA",
                "cantidad": 30,
                "motivo": "Transferencia entre depósitos"
            }
            """, productoId, deposito1Id, deposito2Id);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/movimientos")
            .then()
            .statusCode(201)
            .body("data.tipoMovimiento", is("TRANSFERENCIA"))
            .body("data.nombreDepositoOrigen", notNullValue())
            .body("data.nombreDepositoDestino", notNullValue());
    }

    @Test
    @Order(6)
    void testVerificarStockDespuesDeTransferencia() {
        given()
            .pathParam("idDeposito", deposito1Id)
            .when().get("/api/stock/deposito/{idDeposito}")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(7)
    void testGetAllMovimientos() {
        given()
            .when().get("/api/movimientos")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(8)
    void testGetMovimientosByProducto() {
        given()
            .pathParam("idProducto", productoId)
            .when().get("/api/movimientos/producto/{idProducto}")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(9)
    void testGetMovimientosByTipo() {
        given()
            .pathParam("tipo", "ENTRADA")
            .when().get("/api/movimientos/tipo/{tipo}")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(10)
    void testCreateMovimientoStockInsuficiente() {
        String requestBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoOrigen": %d,
                "tipoMovimiento": "SALIDA",
                "cantidad": 10000,
                "motivo": "Intentar sacar más de lo que hay"
            }
            """, productoId, deposito1Id);

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/movimientos")
            .then()
            .statusCode(400)
            .body("message", containsString("Stock insuficiente"));
    }

    @AfterAll
    static void cleanup() {
        // Limpiar datos de prueba
        given().pathParam("id", productoId).when().delete("/api/productos/{id}");
        given().pathParam("id", deposito1Id).when().delete("/api/depositos/{id}");
        given().pathParam("id", deposito2Id).when().delete("/api/depositos/{id}");
    }
}

