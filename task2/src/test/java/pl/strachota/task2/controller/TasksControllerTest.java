package pl.strachota.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import pl.strachota.task2.model.TaskStatus;

import java.util.List;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static pl.strachota.task2.controller.DataForTests.*;

class TasksControllerTest extends BaseTest {

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
    @Sql(statements = "DELETE FROM tasks")
    void shouldCreateTask() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON);

        given()
                .contentType(ContentType.JSON)
                .body(createTaskDTO)
                .when()
                .post("/tasks")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON);
    }

    @Test
    @Sql(statements = "DELETE FROM tasks")
    void shouldThrowExceptionWhenTaskDueDateIsInvalid() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON);

        given()
                .contentType(ContentType.JSON)
                .body(createTaskDTOInvalidDate)
                .when()
                .post("/tasks")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void shouldGetAllTasks() {
        given()
                .when()
                .get("/tasks")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON);
    }

    @Test
    void shouldGetTaskById() {
        given()
                .when()
                .get("/tasks/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON);
    }

    @Test
    void shouldUpdateTask() {
        given()
                .contentType(ContentType.JSON)
                .body(DataForTests.updateTaskDTO)
                .when()
                .put("/tasks/1")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON);
    }

    @Test
    void shouldDeleteTask() {
        given()
                .when()
                .delete("/tasks/1")
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .contentType(ContentType.TEXT);
    }

    @Test
    void shouldThrowExceptionWhenTryToDeleteNonExistingTask() {
        given()
                .pathParam("id", 100L)
                .when()
                .delete("/tasks/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("exceptionMessage", containsString("Task not found"))
                .body("httpStatus", equalTo("NOT_FOUND"));
    }

    @Test
    void shouldChangeTaskStatus_Positive() {
        TaskStatus newStatus = TaskStatus.IN_PROGRESS;

        given()
                .pathParam("id", 1L)
                .param("newStatus", newStatus)
                .when()
                .patch("/tasks/{id}/change-status")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("status", equalTo(newStatus.toString()));
    }

    @Test
    void shouldThrowExceptionWhenTryToChangeTaskStatusFromDone() {
        TaskStatus newStatus = TaskStatus.IN_PROGRESS;

        given()
                .pathParam("id", 2L)
                .param("newStatus", newStatus)
                .when()
                .patch("/tasks/{id}/change-status")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", containsString("Cannot change status from"))
                .body("httpStatus", equalTo("BAD_REQUEST"));
    }

    @Test
    void shouldThrowTaskNotFoundException_WhenTaskNotFound() {
        given()
                .pathParam("id", 100L)
                .when()
                .get("/tasks/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("exceptionMessage", containsString("Task not found"))
                .body("httpStatus", equalTo("NOT_FOUND"));
    }

    @Test
    void shouldThrowInvalidUserNumberException_WhenUserNumberIsInvalid() {
        given()
                .contentType(ContentType.JSON)
                .body(createTaskDTOInvalidUserNumber)
                .when()
                .post("/tasks")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", containsString("Cannot assign more than 10 users or no users to task"))
                .body("httpStatus", equalTo("BAD_REQUEST"));
    }

    @Test
    void shouldThrowCannotChangeStatusExceptionWhenTryToDeleteTaskWithInProgressStatus() {
        given()
                .pathParam("id", 3L)
                .when()
                .delete("/tasks/{id}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", containsString("Cannot delete task in progress"))
                .body("httpStatus", equalTo("BAD_REQUEST"));
    }

    @Test
    void shouldAssignNewUsersToTask() {
        List<String> userIds = List.of("8", "9");
        given()
                .contentType(ContentType.JSON)
                .queryParam("userIds", userIds)
                .when()
                .patch("/tasks/1/assign")
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON);
    }

    @Test
    void shouldThrowInvalidUserNumberException_WhenUserNumberIsInvalidWhileAssigningUsersToTask() {
        List<String> userIds = List.of("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
        given()
                .contentType(ContentType.JSON)
                .queryParam("userIds", userIds)
                .when()
                .patch("/tasks/1/assign")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .body("exceptionMessage", containsString("User john.doe@example.com is already assigned to task"))
                .body("httpStatus", equalTo("BAD_REQUEST"));
    }

}
