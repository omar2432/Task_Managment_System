package com.hitachi.acmService.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    @Value("${spring.security.oauth2.client.registration.keycloak-client.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak-client.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.server.url}")
    private String serverUrl;
    @Value("${spring.security.oauth2.realm}")
    private String realm;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .realm(realm)
                .build();
    }

}