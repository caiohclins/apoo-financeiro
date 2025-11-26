package br.com.ufrpe.apoo.financeiro.gestaoacesso.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProvedorKeyCloak implements IProvedorIdentidade {

    @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
    private String tokenUri;

    @Value("${keycloak.admin.create-user-uri}")
    private String createUserUri;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
    private String clientSecret;

    @Value("${keycloak.admin.client-id}")
    private String adminClientId;

    @Value("${keycloak.admin.client-secret}")
    private String adminClientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map<String, Object> autenticar(String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "password");
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("username", username);
        body.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
        return response.getBody();
    }

    @Override
    public String criarConta(String email, String password) {
        String effectiveUsername = email;

        Map<String, Object> payload = Map.of(
            "username", effectiveUsername,
            "email", email,
            "enabled", true,
            "credentials", new Map[] {
                Map.of(
                    "type", "password",
                    "value", password,
                    "temporary", false
                )
            }
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(obterAdminToken());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity(createUserUri, request, Void.class);

        String location = response.getHeaders().getFirst("Location");
        if (location == null) {
            throw new RuntimeException("Não foi possível obter o id do usuário criado no Keycloak.");
        }
        return location.substring(location.lastIndexOf('/') + 1);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String obterAdminToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", adminClientId);
        body.add("client_secret", adminClientSecret);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        System.out.println("=== DEBUG: Obtendo admin token ===");
        System.out.println("Token URI: " + tokenUri);
        System.out.println("Admin Client ID: " + adminClientId);
        System.out.println("Admin Client Secret está definido: " + (adminClientSecret != null && !adminClientSecret.isEmpty()));

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(tokenUri, request, Map.class);
            Map<String, Object> responseBody = response.getBody();
            if (responseBody == null || !responseBody.containsKey("access_token")) {
                throw new RuntimeException("Não foi possível obter admin token do Keycloak.");
            }
            return (String) responseBody.get("access_token");
        } catch (Exception e) {
            System.out.println("Erro ao obter admin token: " + e.getMessage());
            throw e;
        }
    }
}