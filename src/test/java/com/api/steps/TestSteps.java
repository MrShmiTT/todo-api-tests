package com.api.steps;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.model.TodoTask;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import static com.EntitiesNames.WEBSOCKET_URI;
import static com.TodoAppMethods.createTask;
import static com.utils.UtilJava.generateTaskDescription;
import static com.utils.UtilJava.generateTaskId;
import static java.util.concurrent.TimeUnit.SECONDS;

public class TestSteps {

    public static int getTaskListSize(List<TodoTask> tasks) {
        return tasks.size();
    }

    public static int getTaskId(List<TodoTask> tasks) {
        return tasks.get(0).getId();
    }

    public static TodoTask createNewTask() {
        return createTask(new TodoTask().id(generateTaskId()).description(generateTaskDescription()).completed(false));
    }

    public static WebSocketClient createWebSocketClient(CountDownLatch latch, AtomicReference<String> receivedMessage)
        throws URISyntaxException {
        return new WebSocketClient(new URI(WEBSOCKET_URI)) {
            @Override
            public void onOpen(ServerHandshake handshake) {
                System.out.println("WebSocket connection opened");
                if (latch != null) {
                    latch.countDown();
                }
            }

            @Override
            public void onMessage(String message) {
                System.out.println("Received message: " + message);
                if (receivedMessage != null) {
                    receivedMessage.set(message);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("WebSocket connection closed: " + reason);
            }

            @Override
            public void onError(Exception ex) {
                System.err.println("WebSocket error: " + ex.getMessage());
            }
        };
    }

    public static boolean waitForConnection(WebSocketClient webSocketClient, CountDownLatch latch, int timeoutInSeconds)
        throws InterruptedException {
        webSocketClient.connect();
        return latch.await(timeoutInSeconds, SECONDS);
    }

    public static void closeConnection(WebSocketClient webSocketClient) {
        if (webSocketClient.isOpen()) {
            webSocketClient.close();
        }
    }
}
