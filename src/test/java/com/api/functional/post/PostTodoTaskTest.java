package com.api.functional.post;


import java.util.List;
import java.util.stream.Stream;

import com.model.TodoTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.TodoAppMethods.createTask;
import static com.TodoAppMethods.createTaskVoid;
import static com.TodoAppMethods.getTasks;
import static com.api.checks.TestChecks.checkTasksAmount;
import static com.model.TodoTask.getTodoTask;
import static com.utils.UtilJava.cleanApp;
import static com.utils.UtilJava.generateTaskDescription;
import static com.utils.UtilJava.generateTaskId;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PostTodoTaskTest {

    @BeforeEach
    public void setup() {
        cleanApp();
    }

    @Test
    public void addTodoTaskSuccessfully() {
        createTaskVoid(getTodoTask());
        List<TodoTask> tasks = getTasks(null, null);
        checkTasksAmount(tasks, 1);

    }

    @ParameterizedTest(name = "[{index}] {1}")
    @MethodSource("invalidTaskParams")
    public void checkRequiredFields(TodoTask task, String caseName) {
        assertThrows(AssertionError.class, () -> {
            createTask(task);
            throw new AssertionError("Expected 400 Bad Request but no error occurred");
        });
    }

    @Test
    public void addDuplicatedTodoTask() {
        TodoTask task = getTodoTask();
        createTask(task);

        assertThrows(AssertionError.class, () -> {
            createTask(task);
            throw new AssertionError("Expected 400 Bad Request but no error occurred");
        });
    }

    private static Stream<Arguments> invalidTaskParams() {
        return Stream.of(
            Arguments.of(new TodoTask(), "All fields are null"),
            Arguments.of(new TodoTask().id(generateTaskId()).description(null).completed(null),
                "Only Task id is filled"),
            Arguments.of(new TodoTask().id(generateTaskId()).description(generateTaskDescription()).completed(null),
                "Only task id and description are filled")
        );
    }

    @AfterEach
    public void cleanUp() {
        cleanApp();
    }

// TODO: Validate successful creation
// 1. Test creating a task with maximum allowable data (e.g., max string length, max numbers, etc.).

// TODO: Data validation
// 2. Test creating a task with incorrect data types (e.g., string instead of number).
// 3. Test creating a task with missing required fields and verify proper error response.
// 4. Test creating a task with extra fields to ensure they are either ignored or handled correctly.

// TODO: Validate constraints
// 1. Test exceeding maximum length for text fields and verify error response.
// 2. Test providing negative values for numeric fields (e.g., negative ID) and check server response.
// 3. Test handling of special characters in text fields and ensure correct behavior.

// TODO: Response validation
// 1. Test that the response structure matches the expected task representation.
// 2. Verify response headers (e.g., Content-Type, Location).

// TODO: Performance testing
// 1. Test creating a large number of tasks to check performance and stability.
// 2. Test concurrent creation of tasks to ensure unique and correct creation in multi-threaded environments.

// TODO: Security checks
// 1. Test creating a task with XSS vectors and ensure proper sanitization and handling.
// 2. Test creating a task without authorization or with an invalid token to verify rejection (if authentication is required).

// TODO: Edge cases
// 1. Test creating a task with an empty request body and verify proper error handling.
// 2. Test creating a task with an invalid JSON format and check error response.
// 3. Test creating a task with duplicate IDs (if the client specifies IDs) and verify server behavior.

}
