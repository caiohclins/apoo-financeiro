package br.com.ufrpe.apoo.financeiro.gestaoacesso.controller;

import br.com.ufrpe.apoo.financeiro.gestaoacesso.model.entity.Usuario;
import br.com.ufrpe.apoo.financeiro.gestaoacesso.model.service.FachadaAutenticacao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ControladorAutenticacao {

    private final FachadaAutenticacao fachadaAutenticacao;

    @PostMapping("/criar-conta")
    public ResponseEntity<?> criarConta(@RequestBody Map<String, String> body) {
        try {
            String username = body.get("username");
            String email = username;
            String password = body.get("password");

            Usuario usuario = fachadaAutenticacao.criarConta(email, password);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", "Falha ao criar conta", "message", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> solicitarLogin(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            Map<String, Object> tokens = fachadaAutenticacao.solicitarLogin(username, password);
            return ResponseEntity.ok(tokens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Falha na autenticação", "message", e.getMessage()));
        }
    }

    @GetMapping("/test-login")
    public ResponseEntity<?> testarLogin(@RequestParam String u, @RequestParam String p) {
        return ResponseEntity.ok("Login bem-sucedido para o usuário: " + u);
    }
}