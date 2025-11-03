package taller4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReportesResourceTest {

    private static Integer productoId;
    private static Integer depositoId;
    private static Integer clienteId;

    @BeforeAll
    static void setup() {
        // Crear depósito
        String depositoBody = """
            {
                "nombre_deposito": "Depósito Reportes Test"
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
                "titulo": "Impresora Test",
                "precio_costo": 200.00,
                "precio_venta": 400.00,
                "cantidad": 0,
                "categoria": "Oficina",
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

        // Crear movimiento de entrada
        String movimientoBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoDestino": %d,
                "tipoMovimiento": "ENTRADA",
                "cantidad": 100,
                "motivo": "Stock inicial reportes"
            }
            """, productoId, depositoId);

        given()
            .contentType(ContentType.JSON)
            .body(movimientoBody)
            .when().post("/api/movimientos")
            .then().statusCode(201);

        // Crear venta
        String ventaBody = String.format("""
            {
                "cantidad": 10,
                "id_producto": %d,
                "nombre_cliente": "Cliente Reportes",
                "apellido_cliente": "Test",
                "ci_cliente": "9999999-R",
                "correo_cliente": "reportes@test.com"
            }
            """, productoId);

        clienteId = given()
            .contentType(ContentType.JSON)
            .body(ventaBody)
            .when().post("/api/ventas")
            .then().statusCode(201)
            .extract().path("data.id_cliente");

        System.out.println("Setup Reportes - Producto: " + productoId + ", Depósito: " + depositoId);
    }

    @Test
    @Order(1)
    void testReporteStockValorizadoPorDeposito() {
        given()
            .queryParam("idDeposito", depositoId)
            .when().get("/api/reportes/stock-valorizado/deposito")
            .then()
            .statusCode(200)
            .body("code", is(200))
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testReporteStockValorizadoPorCategoria() {
        given()
            .queryParam("categoria", "Oficina")
            .when().get("/api/reportes/stock-valorizado/categoria")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(3)
    void testReporteStockBajoMinimo() {
        given()
            .when().get("/api/reportes/stock-bajo-minimo")
            .then()
            .statusCode(200)
            .body("data", notNullValue());
    }

    @Test
    @Order(4)
    void testReporteProductosSinMovimiento() {
        given()
            .queryParam("dias", 30)
            .when().get("/api/reportes/productos-sin-movimiento")
            .then()
            .statusCode(200)
            .body("data", notNullValue());
    }

    @Test
    @Order(5)
    void testReporteVentasPorCliente() {
        given()
            .queryParam("idCliente", clienteId)
            .when().get("/api/reportes/ventas/cliente")
            .then()
            .statusCode(200)
            .body("data", notNullValue());
    }

    @Test
    @Order(6)
    void testReporteVentasPorPeriodo() {
        given()
            .queryParam("fechaInicio", "2025-11-01")
            .queryParam("fechaFin", "2025-11-30")
            .when().get("/api/reportes/ventas/periodo")
            .then()
            .statusCode(200)
            .body("data", notNullValue());
    }

    @Test
    @Order(7)
    void testReporteRotacionInventario() {
        given()
            .when().get("/api/reportes/rotacion-inventario")
            .then()
            .statusCode(200)
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(8)
    void testReporteTodosLosDepositos() {
        given()
            .when().get("/api/reportes/stock-valorizado/deposito")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0));
    }

    @AfterAll
    static void cleanup() {
        if (clienteId != null) {
            given().pathParam("id", clienteId).when().delete("/api/clientes/{id}");
        }
        given().pathParam("id", productoId).when().delete("/api/productos/{id}");
        given().pathParam("id", depositoId).when().delete("/api/depositos/{id}");
    }
}

