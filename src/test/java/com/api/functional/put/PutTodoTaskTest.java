package com.api.functional.put;


import java.util.List;

import com.model.TodoTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.TodoAppMethods.deleteTask;
import static com.TodoAppMethods.getTaskById;
import static com.TodoAppMethods.getTasks;
import static com.TodoAppMethods.updateTask;
import static com.api.checks.TestChecks.checkTaskId;
import static com.api.steps.TestSteps.createNewTask;
import static com.utils.UtilJava.cleanApp;
import static com.utils.UtilJava.generateTaskDescription;
import static com.utils.UtilJava.generateTaskId;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PutTodoTaskTest {
    int taskId;
    String taskDescription;
    Boolean taskCompletedFlag;
    static TodoTask task;
    boolean skipCleanUp;

    @BeforeEach
    public void setup() {
        cleanApp();
        task = createNewTask();
        taskId = task.getId();
        taskDescription = task.getText();
        taskCompletedFlag = task.isCompleted();
    }

    @Test
    public void changeTodoTaskSuccessfully() {
        skipCleanUp = true;

        int newTaskId = generateTaskId();
        String newTaskDescription = generateTaskDescription();
        boolean newCompletedFlag = true;

        TodoTask updatedTask = new TodoTask().id(newTaskId).description(newTaskDescription).completed(newCompletedFlag);
        updateTask(updatedTask, taskId);

        List<TodoTask>tasks = getTasks(null, null);
        checkTaskId(tasks, newTaskId);
        assertEquals(tasks.get(0).getText(), newTaskDescription);
        assertEquals(tasks.get(0).isCompleted(), newCompletedFlag);

        deleteTask(newTaskId);
    }

    @Test
    public void changeTodoTaskRepeatedRequest() {
        skipCleanUp = true;

        String newTaskDescription = generateTaskDescription();
        boolean newCompletedFlag = true;

        TodoTask updatedTask = new TodoTask().id(taskId).description(newTaskDescription).completed(newCompletedFlag);
        updateTask(updatedTask, taskId);
        updateTask(updatedTask, taskId);

        List<TodoTask>tasks = getTasks(null, null);
        checkTaskId(tasks, taskId);
        assertEquals(tasks.get(0).getText(), newTaskDescription);
        assertEquals(tasks.get(0).isCompleted(), newCompletedFlag);

        deleteTask(taskId);
    }

    @Test
    public void changeTaskIdSuccessfully() {
        skipCleanUp = true;

        int newTaskId = generateTaskId();
        TodoTask updatedTask = new TodoTask().id(newTaskId).description(task.getText()).completed(task.isCompleted());
        updateTask(updatedTask, taskId);

        task = getTaskById(newTaskId);
        assertEquals(newTaskId, task.getId());
        deleteTask(newTaskId);
    }

    @Test
    public void changeTaskDescriptionSuccessfully() {
        String newTaskDescription = generateTaskDescription();
        TodoTask updatedTask =
            new TodoTask().id(task.getId()).description(newTaskDescription).completed(task.isCompleted());
        updateTask(updatedTask, taskId);

        task = getTaskById(taskId);
        assertEquals(newTaskDescription, task.getText());
    }

    @Test
    public void changeTaskCompletedFlagSuccessfully() {
        boolean newTaskCompletedFlag = true;
        TodoTask updatedTask =
            new TodoTask().id(task.getId()).description(task.getText()).completed(newTaskCompletedFlag);
        updateTask(updatedTask, taskId);

        task = getTaskById(taskId);
        assertEquals(newTaskCompletedFlag, task.isCompleted());
    }

    @AfterEach
    public void cleanUp() {
        if (!skipCleanUp) {
            deleteTask(taskId);
        }
    }

// TODO: Idempotency:
// 1.Ensure repeated PUT requests with the same data return consistent results.

// TODO: Invalid Data:
// 1. Send an empty request body.
// 2. Send null for required fields.
// 3. Provide data in an invalid format.
// 4. Update a task with ID -1
// 5. Exceed maximum length for fields.
// 6. Attempt to update a task with a non-existent ID.
// 7. Include additional fields not supported by the API.

// TODO: ID Boundary Values:
// 1. Update a task with ID 0
// 2. Use extremely large ID values (e.g., Integer.MAX_VALUE).

// TODO: Field Boundary Values:
// 1. Update a task with an empty description.
// 2. Send data with the maximum allowable characters in description.

// TODO: `completed` Status:
// 1. Test boundary values for the completed flag (e.g., true, false).

}
