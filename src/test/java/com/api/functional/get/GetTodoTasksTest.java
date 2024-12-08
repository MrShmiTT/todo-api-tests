package com.api.functional.get;

import java.util.List;

import com.model.TodoTask;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.TodoAppMethods.deleteTask;
import static com.TodoAppMethods.getTasks;
import static com.api.checks.TestChecks.checkTaskId;
import static com.api.checks.TestChecks.checkTasksAmount;
import static com.api.steps.TestSteps.createNewTask;
import static com.api.steps.TestSteps.getTaskId;
import static com.utils.UtilJava.cleanApp;

public class GetTodoTasksTest {
    int taskId;
    TodoTask task;
    List<TodoTask> tasks;

    @BeforeEach
    public void setup() {
        task = createNewTask();
        taskId = task.getId();
    }

    @Test
    public void getTodoTasks() {
        tasks = getTasks(null, null);
        checkTasksAmount(tasks, 1);
    }

    @Test
    public void getTodoTasksWithOffset() {
        TodoTask additionalTask = createNewTask();

        tasks = getTasks(1, null);
        checkTasksAmount(tasks, 1);
        checkTaskId(tasks, additionalTask.getId());

        deleteTask(additionalTask.getId());
    }

    @Test
    public void getTodoTasksWithLimit() {
        TodoTask additionalTask = createNewTask();

        tasks = getTasks(null, 1);
        checkTasksAmount(tasks, 1);
        checkTaskId(tasks, getTaskId(tasks));

        deleteTask(additionalTask.getId());
    }

    @Test
    public void getEmptyTasksList() {
        cleanApp();

        tasks = getTasks(null, null);
        checkTasksAmount(tasks, 0);
    }

    @Test
    public void getTasksListWithOffsetAndLimit() {
        TodoTask additionalTask = createNewTask();
        createNewTask();

        tasks = getTasks(1, 1);
        checkTasksAmount(tasks, 1);
        checkTaskId(tasks, additionalTask.getId());
    }

    @AfterEach
    public void cleanUp() {
        cleanApp();
    }

// TODO: Additional tests
// 1. Test with offset value greater than the total number of tasks
// 2. Test with limit value greater than the total number of tasks
// 3. Test with limit = 0
// 4. Test with offset = 0
// 5. Test with negative values for offset and limit
// 6. Test with non-numeric values for offset and limit
// 7. Test the order of tasks in the response
// 8. Test performance with large values of offset and limit
// 9. Test with the maximum allowable values for offset and limit

}
