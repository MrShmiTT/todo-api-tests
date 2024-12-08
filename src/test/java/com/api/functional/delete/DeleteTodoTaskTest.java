package com.api.functional.delete;


import java.util.stream.Stream;

import com.model.TodoTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.TodoAppMethods.deleteTask;
import static com.api.steps.TestSteps.createNewTask;
import static com.utils.UtilJava.cleanApp;
import static com.utils.UtilJava.generateTaskId;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteTodoTaskTest {
    int taskId;

    @BeforeEach
    public void setup() {
        TodoTask task = createNewTask();
        taskId = task.getId();
    }

    @Test
    public void deleteTodoTaskSuccessfully() {
        deleteTask(taskId);
    }

    @Test
    public void deleteTodoTaskRepeatedRequest() {
        deleteTask(taskId);
        assertThrows(AssertionError.class, () -> {
            deleteTask(taskId);
            throw new AssertionError("Expected 404 Bad Request but no error occurred");
        });
    }

    @Test
    public void deleteNoneExistingTodoTask() {
        assertThrows(AssertionError.class, () -> {
            deleteTask(generateTaskId());
            throw new AssertionError("Expected 404 Bad Request but no error occurred");
        });
    }

    @ParameterizedTest(name = "[{index}] {1}")
    @MethodSource("invalidTaskId")
    public void deleteTodoTask(Object taskId, String caseName) {
        if (taskId instanceof Integer || taskId == null) {
            assertThrows(AssertionError.class, () -> {
                deleteTask((Integer) taskId);
                throw new AssertionError("Expected 404 Bad Request but no error occurred");
            });
        } else {
            assertThrows(ClassCastException.class, () -> {
                deleteTask((Integer) taskId);
                throw new AssertionError("Expected 404 Bad Request but no error occurred");
            });
        }
    }

    private static Stream<Arguments> invalidTaskId() {
        return Stream.of(
            Arguments.of( null, "Null ID"),
            Arguments.of(0, "Zero ID"),
            Arguments.of(-1, "Negative ID"),
            Arguments.of("", "Empty String"),
            Arguments.of("abc", "Non-numeric string")

        );
    }

    @AfterEach
    public void cleanUp() {
        cleanApp();
    }

// TODO: Negative Tests
// 1. Test deletion without the Authorization header (status 401).
// 2. Test deletion with incorrect login credentials (status 401).
// 3. Test deletion with an incorrectly formatted Authorization header (status 401).
// 4. Verify the behavior when the Content-Type header is missing.

// TODO: Security Tests
// 1. Verify that users without admin rights receive a 403 Forbidden when attempting to delete.
// 2. Ensure the server is protected against frequent requests (Rate Limiting).


}
