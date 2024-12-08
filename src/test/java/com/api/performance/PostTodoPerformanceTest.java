package com.api.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.TodoAppMethods.createTaskVoid;
import static com.model.TodoTask.getTodoTask;
import static com.utils.UtilJava.cleanApp;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostTodoPerformanceTest {

    private static final int THREAD_COUNT = 10;
    private static final int TASKS_PER_THREAD = 100;

    @Test
    public void testPostTodoPerformance() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Long>> futures = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        IntStream.range(0, THREAD_COUNT).forEach(i -> futures.add(executor.submit(() -> {
            long totalTime = 0;
            latch.countDown();
            latch.await();

            for (int j = 0; j < TASKS_PER_THREAD; j++) {
                long startTime = System.nanoTime();
                createTaskVoid(getTodoTask());

                long endTime = System.nanoTime();
                totalTime += (endTime - startTime);
            }
            return totalTime;
        })));

        executor.shutdown();
        executor.awaitTermination(1, MINUTES);

        long totalRequests = THREAD_COUNT * TASKS_PER_THREAD;
        long totalTime = 0;
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;

        for (Future<Long> future : futures) {
            try {
                long threadTime = future.get();
                totalTime += threadTime;
                maxTime = Math.max(maxTime, threadTime);
                minTime = Math.min(minTime, threadTime);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        double averageTime = totalTime / (double) totalRequests;
        System.out.println("Total requests: " + totalRequests);
        System.out.println("Average time per request: " + (averageTime / 1_000_000) + " ms");
        System.out.println("Max time per thread: " + (maxTime / 1_000_000) + " ms");
        System.out.println("Min time per thread: " + (minTime / 1_000_000) + " ms");

        assertTrue(averageTime < 500_000_000, "Average request time is too high!"); // 500ms
    }

    @AfterEach
    public void cleanUp() {
        cleanApp();
    }
}
