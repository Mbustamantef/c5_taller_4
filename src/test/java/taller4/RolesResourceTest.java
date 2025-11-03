package taller4;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.greaterThan;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RolesResourceTest {

    private static Integer rolId;

    @Test
    @Order(1)
    void testCreateRol() {
        String requestBody = """
            {
                "descripcion": "Administrador de Prueba"
            }
            """;

        rolId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/roles")
            .then()
            .statusCode(201)
            .body("code", is(201))
            .body("message", containsString("exitosamente"))
            .body("data.descripcion", is("Administrador de Prueba"))
            .body("data.id_rol", notNullValue())
            .extract().path("data.id_rol");

        System.out.println("Rol creado con ID: " + rolId);
    }

    @Test
    @Order(2)
    void testGetAllRoles() {
        given()
            .when().get("/api/roles")
            .then()
            .statusCode(200)
            .body("code", is(200))
            .body("data", notNullValue())
            .body("data.size()", greaterThan(0));
    }

    @Test
    @Order(3)
    void testGetRolById() {
        given()
            .pathParam("id", rolId)
            .when().get("/api/roles/{id}")
            .then()
            .statusCode(200)
            .body("code", is(200))
            .body("data.id_rol", is(rolId))
            .body("data.descripcion", is("Administrador de Prueba"));
    }

    @Test
    @Order(4)
    void testUpdateRol() {
        String requestBody = """
            {
                "descripcion": "Administrador Actualizado"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .pathParam("id", rolId)
            .body(requestBody)
            .when().put("/api/roles/{id}")
            .then()
            .statusCode(200)
            .body("code", is(200))
            .body("data.descripcion", is("Administrador Actualizado"));
    }

    @Test
    @Order(5)
    void testCreateRolValidationError() {
        String requestBody = """
            {
                "descripcion": "AB"
            }
            """;

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
            .when().post("/api/roles")
            .then()
            .statusCode(400);
    }

    @Test
    @Order(6)
    void testDeleteRol() {
        given()
            .pathParam("id", rolId)
            .when().delete("/api/roles/{id}")
            .then()
            .statusCode(200)
            .body("code", is(200));
    }

    @Test
    @Order(7)
    void testGetRolNotFound() {
        given()
            .pathParam("id", 99999)
            .when().get("/api/roles/{id}")
            .then()
            .statusCode(404)
            .body("code", is(404));
    }
}

