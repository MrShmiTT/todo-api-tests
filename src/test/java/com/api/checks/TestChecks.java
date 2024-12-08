package com.api.checks;

import java.util.List;

import com.model.TodoTask;

import static com.api.steps.TestSteps.getTaskListSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestChecks {

    public static void checkTasksAmount(List<TodoTask> tasks, int expectedAmount) {
        int tasksAmount = getTaskListSize(tasks);
        assertEquals(expectedAmount, tasksAmount, "Amount of tasks doesn't match with expected amount");
    }

    public static void checkTaskId(List<TodoTask> tasks, int expectedId) {
        boolean idExists = tasks.stream()
            .anyMatch(task -> task.getId() == expectedId);
        assertTrue(idExists, "Task with expected Id doesn't exist");
    }
}
