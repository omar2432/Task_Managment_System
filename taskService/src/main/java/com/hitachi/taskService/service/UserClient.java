package com.hitachi.taskService.service;

import com.hitachi.taskService.exchange.response.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service",url = "${user.service.url}")
public interface UserClient {
    @GetMapping("users/{id}")
    ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id,@RequestHeader HttpHeaders headers);
}
