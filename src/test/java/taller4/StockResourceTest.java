package taller4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StockResourceTest {

    private static Integer productoId;
    private static Integer depositoId;

    @BeforeAll
    static void setup() {
        // Crear depósito
        String depositoBody = """
            {
                "nombre_deposito": "Depósito Stock Test"
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
                "titulo": "Teclado Test",
                "precio_costo": 15.00,
                "precio_venta": 30.00,
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

        // Crear movimiento de entrada para tener stock
        String movimientoBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoDestino": %d,
                "tipoMovimiento": "ENTRADA",
                "cantidad": 200,
                "motivo": "Stock inicial para pruebas"
            }
            """, productoId, depositoId);

        given()
            .contentType(ContentType.JSON)
            .body(movimientoBody)
            .when().post("/api/movimientos")
            .then().statusCode(201);

        System.out.println("Setup Stock Tests - Producto: " + productoId + ", Depósito: " + depositoId);
    }

    @Test
    @Order(1)
    void testGetAllStock() {
        given()
            .when().get("/api/stock")
            .then()
            .statusCode(200)
            .body("code", is(200))
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testGetStockByProducto() {
        given()
            .pathParam("idProducto", productoId)
            .when().get("/api/stock/producto/{idProducto}")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0))
            .body("data[0].idProducto", is(productoId))
            .body("data[0].cantidad", is(200))
            .body("data[0].nombreProducto", is("Teclado Test"))
            .body("data[0].precioCosto", is(15.0f))
            .body("data[0].precioVenta", is(30.0f))
            .body("data[0].valorTotal", notNullValue());
    }

    @Test
    @Order(3)
    void testGetStockByDeposito() {
        given()
            .pathParam("idDeposito", depositoId)
            .when().get("/api/stock/deposito/{idDeposito}")
            .then()
            .statusCode(200)
            .body("data.size()", greaterThan(0))
            .body("data[0].idDeposito", is(depositoId))
            .body("data[0].nombreDeposito", notNullValue());
    }

    @Test
    @Order(4)
    void testGetStockBajoMinimo() {
        given()
            .when().get("/api/stock/bajo-minimo")
            .then()
            .statusCode(200)
            .body("data", notNullValue());
    }

    @Test
    @Order(5)
    void testStockCalculadoCorrectamente() {
        // Hacer una salida
        String salidaBody = String.format("""
            {
                "idProducto": %d,
                "idDepositoOrigen": %d,
                "tipoMovimiento": "SALIDA",
                "cantidad": 50,
                "motivo": "Venta de prueba"
            }
            """, productoId, depositoId);

        given()
            .contentType(ContentType.JSON)
            .body(salidaBody)
            .when().post("/api/movimientos")
            .then().statusCode(201);

        // Verificar que el stock se actualizó
        given()
            .pathParam("idProducto", productoId)
            .when().get("/api/stock/producto/{idProducto}")
            .then()
            .statusCode(200)
            .body("data[0].cantidad", is(150)); // 200 - 50
    }

    @AfterAll
    static void cleanup() {
        given().pathParam("id", productoId).when().delete("/api/productos/{id}");
        given().pathParam("id", depositoId).when().delete("/api/depositos/{id}");
    }
}

