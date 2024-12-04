package com.hitachi.userService.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitachi.userService.common.MessageConstants;
import com.hitachi.userService.entity.User;
import com.hitachi.userService.exchange.request.UserReqDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private String accessToken;

    @BeforeEach
    void setUp() throws Exception {
        accessToken = getAccessToken();
    }

    @Test
    void createUser_shouldReturnCreatedUser() {
        UserReqDTO userReqDTO = new UserReqDTO("John Doe", "john.doe@example.com", 30, User.UserQualification.Engineer);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<UserReqDTO> request = new HttpEntity<>(userReqDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getFullUrl(MessageConstants.CREATE_USER_PATH),
                HttpMethod.POST,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).contains("John Doe");
    }

    @Test
    void getUserById_shouldReturnUser() {
        // Arrange: Create a user
        UserReqDTO userReqDTO = new UserReqDTO("John Doe", "john.doe@example.com", 30, User.UserQualification.Engineer);
        long userId = createUserAndGetId(userReqDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getFullUrl(MessageConstants.GET_USER_BY_ID_PATH.replace("{id}", String.valueOf(userId))),
                HttpMethod.GET,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("John Doe");
    }

    @Test
    void updateUser_shouldReturnUpdatedUser() {
        // Arrange: Create a user
        UserReqDTO initialUserReqDTO = new UserReqDTO("John Doe", "john.doe@example.com", 30, User.UserQualification.Engineer);
        long userId = createUserAndGetId(initialUserReqDTO);

        UserReqDTO updatedUserReqDTO = new UserReqDTO("John Updated", "john.updated@example.com", 35, User.UserQualification.Doctor);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<UserReqDTO> request = new HttpEntity<>(updatedUserReqDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                getFullUrl(MessageConstants.UPDATE_USER_PATH.replace("{id}", String.valueOf(userId))),
                HttpMethod.PUT,
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("John Updated");
    }

    @Test
    void deleteUser_shouldReturnNoContent() {
        UserReqDTO userReqDTO = new UserReqDTO("John Doe", "john.doe@example.com", 30, User.UserQualification.Engineer);
        long userId = createUserAndGetId(userReqDTO);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Void> response = restTemplate.exchange(
                getFullUrl(MessageConstants.DELETE_USER_PATH.replace("{id}", String.valueOf(userId))),
                HttpMethod.DELETE,
                request,
                Void.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    private String getAccessToken() throws Exception {
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", "task-service-client");
        requestBody.add("client_secret", "HXb2ZZgTk5W8MS1Ig922E7Vj4QPBhlsj");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "http://localhost:8081/realms/task-management-realm/protocol/openid-connect/token",
                request,
                String.class
        );

        Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), Map.class);
        return responseBody.get("access_token").toString();
    }

    private String getFullUrl(String path) {
        return "http://localhost:" + port + MessageConstants.API_BASE_PATH + path;
    }

    private long createUserAndGetId(UserReqDTO userReqDTO) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        HttpEntity<UserReqDTO> request = new HttpEntity<>(userReqDTO, headers);

        ResponseEntity<User> response = restTemplate.exchange(
                getFullUrl(MessageConstants.CREATE_USER_PATH),
                HttpMethod.POST,
                request,
                User.class
        );

        return response.getBody().getId();
    }
}
