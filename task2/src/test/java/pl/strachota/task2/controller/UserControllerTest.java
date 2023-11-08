package pl.strachota.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static pl.strachota.task2.controller.DataForTests.createUserDTO;
import static pl.strachota.task2.controller.DataForTests.createUserDTOInvalidEmail;

class UserControllerTest extends BaseTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;


    @BeforeEach
    void setup() {
        RestAssuredMockMvc.mockMvc(mockMvc);
        RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void shouldRetrieveAllUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(greaterThan(0)));
    }

    @Test
    void shouldRetrieveUserById() {

        Long userId = 1L;
        given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(userId.intValue()));

    }

    @Test
    @Order(3)
    @Sql(statements = "DELETE FROM users")
    void shouldCreateUser() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

    }

    @Test
    @Sql(statements = "DELETE FROM users")
    void shouldThrowExceptionWhenUserMailIsInvalid() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTOInvalidEmail)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

    }

    @Test
    void shouldDeleteUser() {
        Long userId = 1L;
        given()
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        Long userId = 100L;
        given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(404)
                .body("exceptionMessage", containsString("User not found"));
    }

}
