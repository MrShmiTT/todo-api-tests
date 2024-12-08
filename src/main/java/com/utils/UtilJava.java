package com.utils;

import java.util.List;

import com.model.TodoTask;

import static com.TodoAppMethods.deleteTask;
import static com.TodoAppMethods.getTasks;
import static java.util.concurrent.ThreadLocalRandom.current;
import static org.apache.commons.lang3.RandomStringUtils.random;

public class UtilJava {

    public static void cleanApp() {
        List<TodoTask> tasks = getTasks(null, null);
        for (TodoTask task : tasks) {
            deleteTask(task.getId());
        }
    }

    public static Integer generateTaskId() {
        return current().nextInt(1, 10000001);
    }

    public static String generateTaskDescription() {
        return "Autotest_Task_" + random(5, true, false);
    }

}
