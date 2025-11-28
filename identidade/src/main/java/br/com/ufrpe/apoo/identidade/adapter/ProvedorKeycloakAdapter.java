package br.com.ufrpe.apoo.identidade.adapter;

import br.com.ufrpe.apoo.identidade.dominio.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Map;
import java.util.List;

@Component
public class ProvedorKeycloakAdapter implements IProvedorIdentidade {

    @Value("${keycloak.auth-server-url}")
    private String keycloakServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestClient restClient;

    public ProvedorKeycloakAdapter(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.build();
    }

    @Override
    public String criarUsuario(Usuario usuario, String senha) {
        String adminToken = getAdminToken();

        Map<String, Object> userRepresentation = Map.of(
                "username", usuario.getEmail(),
                "email", usuario.getEmail(),
                "firstName", usuario.getNome(),
                "enabled", true,
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", senha,
                        "temporary", false)));

        var response = restClient.post()
                .uri(keycloakServerUrl + "/admin/realms/" + realm + "/users")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepresentation)
                .retrieve()
                .toBodilessEntity();

        java.net.URI location = response.getHeaders().getLocation();
        if (location == null) {
            throw new RuntimeException("Failed to retrieve ID from Location header");
        }
        String path = location.getPath();
        return path.substring(path.lastIndexOf('/') + 1);
    }

    @Override
    public Map<String, Object> login(String email, String senha) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("username", email);
        formData.add("password", senha);
        formData.add("grant_type", "password");

        Map<String, Object> response = restClient.post()
                .uri(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(Map.class);

        return response;
    }

    private String getAdminToken() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId); // Assuming the service client has admin rights or use specific admin-cli
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "client_credentials");

        Map<String, Object> response = restClient.post()
                .uri(keycloakServerUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(formData)
                .retrieve()
                .body(Map.class);

        return (String) response.get("access_token");
    }
}
