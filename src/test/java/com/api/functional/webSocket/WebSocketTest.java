package com.api.functional.webSocket;

import java.net.URISyntaxException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.model.TodoTask;
import org.java_websocket.client.WebSocketClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.TodoAppMethods.createTask;
import static com.api.steps.TestSteps.closeConnection;
import static com.api.steps.TestSteps.createWebSocketClient;
import static com.api.steps.TestSteps.waitForConnection;
import static com.utils.UtilJava.cleanApp;
import static com.utils.UtilJava.generateTaskDescription;
import static com.utils.UtilJava.generateTaskId;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebSocketTest {

    @Test
    public void successfulWebSocketConnection() throws URISyntaxException, InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        WebSocketClient webSocketClient = createWebSocketClient(latch, null);
        boolean connected = waitForConnection(webSocketClient, latch, 5);

        closeConnection(webSocketClient);
        assertTrue(connected, "WebSocket connection was not established");
    }

    @Test
    public void webSocketReconnection() throws URISyntaxException, InterruptedException {
        CountDownLatch firstConnectionLatch = new CountDownLatch(1);
        WebSocketClient webSocketClient = createWebSocketClient(firstConnectionLatch, null);
        boolean firstConnection = waitForConnection(webSocketClient, firstConnectionLatch, 5);

        closeConnection(webSocketClient);

        CountDownLatch reconnectionLatch = new CountDownLatch(1);
        webSocketClient = createWebSocketClient(reconnectionLatch, null);
        boolean reconnection = waitForConnection(webSocketClient, reconnectionLatch, 5);

        closeConnection(webSocketClient);

        assertTrue(firstConnection, "First WebSocket connection was not established");
        assertTrue(reconnection, "WebSocket reconnection was not established");
    }

    @Test
    public void webSocketReceivesNewTaskNotification() throws URISyntaxException, InterruptedException {
        AtomicReference<String> receivedMessage = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);
        WebSocketClient webSocketClient = createWebSocketClient(latch, receivedMessage);
        boolean connected = waitForConnection(webSocketClient, latch, 5);

        Integer taskId = generateTaskId();
        createTask(new TodoTask().id(taskId).description(generateTaskDescription()).completed(false));

        closeConnection(webSocketClient);

        assertTrue(connected, "WebSocket connection was not established");
        assertTrue(receivedMessage.get() != null && receivedMessage.get().contains("\"id\":" + taskId),
            "Message does not contain the correct task ID");
    }

    @AfterEach
    public void cleanUp() {
        cleanApp();
    }

//TODO: Additional tests
//1. Test for closing the connection by the server after a while
//2. Test for sending a message to the server
//3. Error handling test
//4. Test for a large number of messages to receive

}
