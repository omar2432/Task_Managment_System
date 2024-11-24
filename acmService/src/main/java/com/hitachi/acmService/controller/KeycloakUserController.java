package com.hitachi.acmService.controller;

import com.hitachi.acmService.common.MessageConstants;
import com.hitachi.acmService.exchange.request.LoginDTO;
import com.hitachi.acmService.exchange.request.RegisterDTO;
import com.hitachi.acmService.exchange.request.UpdateUserDTO;
import com.hitachi.acmService.exchange.response.ResponseExchange;
import com.hitachi.acmService.service.KeycloakUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(MessageConstants.API_BASE_PATH)
public class KeycloakUserController {

    private final KeycloakUserService keycloakUserService;

    @Autowired
    public KeycloakUserController(KeycloakUserService keycloakUserService) {
        this.keycloakUserService = keycloakUserService;
    }

    @PostMapping(MessageConstants.CREATE_KEYCLOAK_USER_PATH)
    public ResponseEntity<ResponseExchange> createUser(@RequestBody RegisterDTO request) {
        return ResponseEntity.ok(keycloakUserService.createUser(request));
    }

    @PutMapping(MessageConstants.UPDATE_KEYCLOAK_USER_PATH)
    public ResponseEntity<ResponseExchange> updateUser(
            @PathVariable String userId,
            @RequestBody UpdateUserDTO request) {
        return ResponseEntity.ok(keycloakUserService.updateUser(userId, request));
    }

    @DeleteMapping(MessageConstants.DELETE_KEYCLOAK_USER_PATH)
    public ResponseEntity<ResponseExchange> deleteUser(@PathVariable String userId) {
        return ResponseEntity.ok(keycloakUserService.deleteUser(userId));
    }


    @PostMapping(MessageConstants.LOGIN_PATH)
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody LoginDTO loginDTO) {
        Map<String, Object> accessToken = keycloakUserService.login(loginDTO);
        return ResponseEntity.ok(accessToken);
    }

}
