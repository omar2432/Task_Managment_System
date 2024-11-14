package com.hitachi.taskService.service;

import com.hitachi.taskService.DTO.TaskAssignmentRequest;
import com.hitachi.taskService.DTO.UserResponse;
import com.hitachi.taskService.common.MessageConstants;
import com.hitachi.taskService.entity.Task;
import com.hitachi.taskService.repository.TaskRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserClient userClient;
    private final TaskAssignmentMessagePublisher taskAssignmentMessagePublisher;
    private final RestTemplate restTemplate;


    private String cachedToken;
    private LocalDateTime tokenExpirationTime;

    @Value("${spring.security.oauth2.client.registration.keycloak-client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak-client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.keycloak-client.authorization-grant-type}")
    private String grantType;

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;


    @Autowired
    public TaskService(TaskRepository taskRepository, UserClient userClient,TaskAssignmentMessagePublisher taskAssignmentMessagePublisher, RestTemplate restTemplate){

        this.taskRepository=taskRepository;
        this.userClient=userClient;
        this.taskAssignmentMessagePublisher=taskAssignmentMessagePublisher;
        this.restTemplate = restTemplate;
    }
    public Task createTask(Task task){
        if(task.getStatus().equals(Task.Status.COMPLETED)){
            task.setCompletedAt(LocalDateTime.now());
        }
        return taskRepository.save(task);
    }

    public Task getTask(long id){
        return taskRepository.findById(id).orElseThrow(() -> new RuntimeException(MessageConstants.TASK_NOT_FOUND+id));
    }

    public Task updateTask(long id,Task newTask){
        Task task=getTask(id);
        task.setDescription(newTask.getDescription());
        // if the task was not completed but is completed now update completedAt
        if((!task.getStatus().equals(Task.Status.COMPLETED)) && newTask.getStatus().equals(Task.Status.COMPLETED)){
            task.setCompletedAt(LocalDateTime.now());
        }
        task.setStatus(newTask.getStatus());

        return taskRepository.save(task);
    }

    public void deleteTask(long id){
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException(MessageConstants.TASK_NOT_FOUND+id);
        }
        taskRepository.deleteById(id);
    }

    public Task assignTask(TaskAssignmentRequest taskAssignmentRequest){
        Task task=getTask(taskAssignmentRequest.getTaskId());
        //get the token using client credentials grant type flow
        String token = getAccessToken();

        try {
            //append the token to the request going to the user service
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            //get user from userService
            ResponseEntity<UserResponse> response = userClient.getUserById(taskAssignmentRequest.getUserId(), headers);
            UserResponse user = response.getBody();
            System.out.println(MessageConstants.USER_FOUND +user.getId());

        } catch (FeignException.NotFound e) {
            throw new RuntimeException(MessageConstants.USER_NOT_FOUND);
        }

        //assign task
        task.setAssignedUserId(taskAssignmentRequest.getUserId());
        if(task.getStatus().equals(Task.Status.NOTASSIGNED)){
            task.setStatus(Task.Status.ASSIGNED);
        }

        // Notify via RabbitMQ
        taskAssignmentMessagePublisher.publishTaskAssignment(taskAssignmentRequest);


        return  taskRepository.save(task);
    }

    private String getAccessToken() {
        // If the token is cached and still valid, return it
        if (cachedToken != null && tokenExpirationTime.isAfter(LocalDateTime.now())) {
            return cachedToken;
        }

        // Prepare the form data
        String body = "client_id=" + clientId + "&client_secret=" + clientSecret + "&grant_type=" + grantType;

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create HttpEntity with headers and body
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        // Make the POST request to the token endpoint
        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                entity,
                Map.class
        );

        // Extract the access token and expiration time
        String token = (String) response.getBody().get("access_token");
        int expiresIn = (int) response.getBody().get("expires_in");

        // Cache the token and expiration time
        cachedToken = token;
        tokenExpirationTime = LocalDateTime.now().plusSeconds(expiresIn);

        return token;
    }


}
