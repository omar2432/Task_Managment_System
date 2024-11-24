package com.hitachi.acmService.service;

import com.hitachi.acmService.common.MessageConstants;
import com.hitachi.acmService.exchange.request.LoginDTO;
import com.hitachi.acmService.exchange.request.RegisterDTO;
import com.hitachi.acmService.exchange.request.UpdateUserDTO;
import com.hitachi.acmService.exchange.response.ResponseExchange;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

import jakarta.ws.rs.core.Response;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeycloakUserService {
    @Value("${spring.security.oauth2.realm}")
    private String realm;
    @Value("${spring.security.oauth2.client.registration.keycloak-client.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.keycloak-client.client-secret}")
    private String clientSecret;
    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;

    private final Keycloak keycloak;

    @Autowired
    public KeycloakUserService(Keycloak keycloak) {
        this.keycloak = keycloak;
    }

    public ResponseExchange createUser(RegisterDTO request) {
        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(request.isEnabled());
        user.setEmailVerified(request.isEmailVerified());

        // Assign groups
        user.setGroups(request.getGroups());

        // Assign credentials
        user.setCredentials(request.getCredentials().stream()
                .map(credential -> {
                    CredentialRepresentation cred = new CredentialRepresentation();
                    cred.setType(credential.getType());
                    cred.setValue(credential.getValue());
                    cred.setTemporary(credential.isTemporary());
                    return cred;
                }).toList());

        Response response = keycloak.realm(realm)
                .users()
                .create(user);

        if (response.getStatus() == 201) {
            String userId = response.getLocation().getPath().replaceAll(".*/(.*)$", "$1");
            return new ResponseExchange(MessageConstants.USER_CREATED, userId);
        } else {
            throw new RuntimeException(MessageConstants.USER_NOT_CREATED + response.getStatusInfo());
        }
    }

    public ResponseExchange updateUser(String userId, UpdateUserDTO request) {
        UserRepresentation user = keycloak.realm(realm)
                .users()
                .get(userId)
                .toRepresentation();

        // Update fields if provided
        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }

        if (request.getEmailVerified() != null) {
            user.setEmailVerified(request.getEmailVerified());
        }

        if (request.getGroups() != null) {
            user.setGroups(request.getGroups());
        }

        // Update credentials if provided
        if (request.getCredentials() != null) {
            user.setCredentials(request.getCredentials().stream()
                    .map(credential -> {
                        CredentialRepresentation cred = new CredentialRepresentation();
                        cred.setType(credential.getType());
                        cred.setValue(credential.getValue());
                        cred.setTemporary(credential.isTemporary());
                        return cred;
                    }).toList());
        }

        keycloak.realm(realm).users().get(userId).update(user);

        return new ResponseExchange(MessageConstants.USER_UPDATED, userId);
    }


    public ResponseExchange deleteUser(String userId) {
        keycloak.realm(realm).users().get(userId).remove();
        return new ResponseExchange(MessageConstants.USER_DELETED, userId);
    }



    public Map<String, Object> login(LoginDTO loginDTO) {
        String username= loginDTO.getUsername();
        String password= loginDTO.getPassword();
        RestTemplate restTemplate = new RestTemplate();

        // Create request body
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("username", username);
        requestBody.add("password", password);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Create HTTP entity
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);

        // Make the POST request
        ResponseEntity<Map> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                entity,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            return (Map<String, Object>) response.getBody();
        } else {
            throw new RuntimeException(MessageConstants.LOGIN_FAILED + response.getStatusCode());
        }
    }


}