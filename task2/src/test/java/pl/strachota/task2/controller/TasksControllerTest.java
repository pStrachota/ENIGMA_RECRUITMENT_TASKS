package pl.strachota.task2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import pl.strachota.task2.model.TaskStatus;

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
    public void shouldCreateTask() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

        given()
                .contentType(ContentType.JSON)
                .body(createTaskDTO)
                .when()
                .post("/tasks")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);
    }

    @Test
    @Sql(statements = "DELETE FROM tasks")
    public void shouldThrowExceptionWhenTaskDueDateIsInvalid() {

        given()
                .contentType(ContentType.JSON)
                .body(createUserDTO)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);

        given()
                .contentType(ContentType.JSON)
                .body(createTaskDTOInvalidDate)
                .when()
                .post("/tasks")
                .then()
                .statusCode(400);
    }

    @Test
    public void shouldGetAllTasks() {
        given()
                .when()
                .get("/tasks")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void shouldGetTaskById() {
        given()
                .when()
                .get("/tasks/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void shouldUpdateTask() {
        given()
                .contentType(ContentType.JSON)
                .body(DataForTests.updateTaskDTO)
                .when()
                .put("/tasks/1")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test
    public void shouldDeleteTask() {
        given()
                .when()
                .delete("/tasks/1")
                .then()
                .statusCode(204)
                .contentType(ContentType.TEXT);
    }

    @Test
    void shouldThrowExceptionWhenTryToDeleteNonExistingTask() {
        given()
                .pathParam("id", 100L)
                .when()
                .delete("/tasks/{id}")
                .then()
                .statusCode(404)
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
                .statusCode(200)
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
                .statusCode(400)
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
                .statusCode(404)
                .body("exceptionMessage", containsString("Task not found"))
                .body("httpStatus", equalTo("NOT_FOUND"));
    }

    @Test
    void shouldThrowExceptionWhenTryToDeleteTaskWithInProgressStatus() {
        given()
                .pathParam("id", 3L)
                .when()
                .delete("/tasks/{id}")
                .then()
                .statusCode(400)
                .body("exceptionMessage", containsString("Cannot delete task in progress"))
                .body("httpStatus", equalTo("BAD_REQUEST"));
    }

//    @Test
//    public void shouldGetAllTasksByUserId() {
//        given()
//                .when()
//                .get("/tasks/user/1")
//                .then()
//                .statusCode(200)
//                .contentType(ContentType.JSON);
//    }

}
