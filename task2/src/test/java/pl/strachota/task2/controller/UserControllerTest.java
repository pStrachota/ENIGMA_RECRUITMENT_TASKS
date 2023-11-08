package pl.strachota.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.*;
import static pl.strachota.task2.controller.DataForTests.*;

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
                .statusCode(HttpStatus.OK.value())
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
                .statusCode(HttpStatus.OK.value())
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
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON);

    }

    @Test
    @Sql(statements = "DELETE FROM users")
    void shouldThrowException_WhenUserMailIsInvalid() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTOInvalidEmail)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @Sql(statements = "DELETE FROM users")
    void shouldThrowException_WhenUserNameIsInvalid() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTOInvalidName)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    void shouldDeleteUser() {
        Long userId = 1L;
        given()
                .pathParam("id", userId)
                .when()
                .delete("/users/{id}")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void shouldThrowUserNotFoundException_WhenUserDoesNotExist() {
        Long userId = 100L;
        given()
                .pathParam("id", userId)
                .when()
                .get("/users/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("exceptionMessage", containsString("User not found"));
    }

}
