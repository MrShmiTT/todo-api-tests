package com;

import java.util.List;

import com.model.TodoTask;
import io.restassured.response.Response;

import static com.EntitiesNames.BASE_URI;
import static com.EntitiesNames.CONTENT_TYPE;
import static com.EntitiesNames.ENDPOINT;
import static com.EntitiesNames.PASSWORD;
import static com.EntitiesNames.USERNAME;
import static io.restassured.RestAssured.given;

public class TodoAppMethods {

    /**
     * Gets list of tasks with optional parameters offset and limit.
     * If parameters are not specified, the default value is used.
     *
     * @param offset - Amount of skipped tasks (by default 0)
     * @param limit - Amount of returned tasks (by default 1 000 000)
     * @return Response with data about tasks
     */
    public static List<TodoTask> getTasks(Integer offset, Integer limit) {
        offset = (offset != null) ? offset : 0;
        limit = (limit != null) ? limit : 1_000_000;

        Response response = given()
            .baseUri(BASE_URI)
            .queryParam("offset", offset)
            .queryParam("limit", limit)
        .when()
            .get(ENDPOINT)
        .then()
            .statusCode(200)
            .extract()
            .response();

        List<TodoTask> todoTasks = response.jsonPath().getList(".", TodoTask.class);

        return todoTasks;
    }

    /**
     * Gets task by id.
     *
     * @param taskId - task ID to get
     * @return Response with task object
     */
    public static TodoTask getTaskById(int taskId) {
        List<TodoTask> tasks = getTasks(null, null);

        return tasks.stream()
            .filter(t -> t.getId() == taskId)
            .findFirst()
            .orElse(null);
    }

    /**
     * Task creation.
     *
     * @param newTask - task to create
     * @return Response with task by id
     */
    public static TodoTask createTask(TodoTask newTask) {
        given()
            .baseUri(BASE_URI)
            .contentType(CONTENT_TYPE)
            .body(newTask)
        .when()
            .post(ENDPOINT)
        .then()
            .statusCode(201)
            .extract()
            .response();

        return getTaskById(newTask.getId());
    }

    /**
     * Task creation.
     *
     * @param newTask - task to create
     */
    public static void createTaskVoid(TodoTask newTask) {
        given()
            .baseUri(BASE_URI)
            .contentType(CONTENT_TYPE)
            .body(newTask)
        .when()
            .post(ENDPOINT)
        .then()
            .statusCode(201)
            .extract()
            .response();
    }

    /**
     * Updates task by ID.
     *
     * @param taskId - task ID to update
     */
    public static void updateTask(TodoTask updatedTask, Integer taskId) {
        given()
            .contentType(CONTENT_TYPE)
            .body(updatedTask)
        .when()
            .put(ENDPOINT + taskId)
        .then()
            .statusCode(200);
    }

    /**
     * Deletes task by ID.
     *
     * @param taskId - task ID to delete
     */
    public static void deleteTask(Integer taskId) {
        given()
            .auth()
            .preemptive()
            .basic(USERNAME, PASSWORD)
            .baseUri(BASE_URI)
        .when()
            .delete(ENDPOINT + taskId)
        .then()
            .statusCode(204);
    }
}
