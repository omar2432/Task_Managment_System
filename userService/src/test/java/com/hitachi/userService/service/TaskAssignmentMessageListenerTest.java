package com.hitachi.userService.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.userService.exchange.response.TaskAssignmentMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

public class TaskAssignmentMessageListenerTest {

    @InjectMocks
    private TaskAssignmentMessageListener messageListener;

    @Mock
    private ObjectMapper objectMapper;

    private String message;
    private TaskAssignmentMessage taskAssignmentMessage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        message = "{\"taskId\": 1, \"userId\": 2}";
        taskAssignmentMessage = new TaskAssignmentMessage(1L, 2L);
    }

    @Test
    public void handleTaskAssignmentMessage_ShouldPrintMessage() throws IOException {
        when(objectMapper.readValue(message, TaskAssignmentMessage.class)).thenReturn(taskAssignmentMessage);

        messageListener.handleTaskAssignmentMessage(message);

        verify(objectMapper, times(1)).readValue(message, TaskAssignmentMessage.class);
    }

    @Test
    public void handleTaskAssignmentMessage_ShouldHandleException() throws IOException {
        when(objectMapper.readValue(message, TaskAssignmentMessage.class)).thenThrow(new RuntimeException(new IOException("Invalid JSON")));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> messageListener.handleTaskAssignmentMessage(message));

        verify(objectMapper, times(1)).readValue(message, TaskAssignmentMessage.class);
    }
}
