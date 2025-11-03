package taller4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductosResourceTest {

    private static Integer productoId;
    private static Integer depositoId;

    @Test
    @Order(1)
    void testCreateDeposito() {
        String requestBody = """
            {
                "nombre_deposito": "Depósito Test"
            }
            """;

        depositoId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/depositos")
            .then()
            .log().all()  // Agregar logging
            .statusCode(201)
            .extract().path("data.id_deposito");

        System.out.println("Depósito creado con ID: " + depositoId);
    }

    @Test
    @Order(2)
    void testCreateProducto() {
        String requestBody = """
            {
                "titulo": "Laptop Test HP",
                "precio_costo": 500.00,
                "precio_venta": 750.00,
                "cantidad": 50,
                "categoria": "Electrónica",
                "activo": true,
                "mes_compra": "2025-11-02"
            }
            """;

        productoId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/productos")
            .then()
            .statusCode(201)
            .body("code", is(201))
            .body("data.titulo", is("Laptop Test HP"))
            .body("data.precio_costo", is(500.0f))
            .body("data.precio_venta", is(750.0f))
            .extract().path("data.id_producto");

        System.out.println("Producto creado con ID: " + productoId);
    }

    @Test
    @Order(3)
    void testGetAllProductos() {
        given()
            .when().get("/api/productos")
            .then()
            .statusCode(200)
            .body("code", is(200))
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(4)
    void testGetProductoById() {
        given()
            .pathParam("id", productoId)
            .when().get("/api/productos/{id}")
            .then()
            .statusCode(200)
            .body("data.id_producto", is(productoId))
            .body("data.titulo", is("Laptop Test HP"));
    }

    @Test
    @Order(5)
    void testUpdateProducto() {
        String requestBody = """
            {
                "titulo": "Laptop Test HP Actualizada",
                "precio_costo": 550.00,
                "precio_venta": 800.00,
                "cantidad": 60,
                "categoria": "Electrónica",
                "activo": true,
                "mes_compra": "2025-11-02"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", productoId)
            .body(requestBody)
            .when().put("/api/productos/{id}")
            .then()
            .statusCode(200)
            .body("data.titulo", is("Laptop Test HP Actualizada"))
            .body("data.precio_costo", is(550.0f));
    }

    @Test
    @Order(6)
    void testCreateProductoValidationError() {
        String requestBody = """
            {
                "titulo": "AB",
                "precio_costo": -10.00,
                "precio_venta": 750.00
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/productos")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(7)
    void testDeleteProducto() {
        given()
            .pathParam("id", productoId)
            .when().delete("/api/productos/{id}")
            .then()
            .statusCode(200);
    }

    @Test
    @Order(8)
    void testDeleteDeposito() {
        given()
            .pathParam("id", depositoId)
            .when().delete("/api/depositos/{id}")
            .then()
            .statusCode(200);
    }
}

