package br.com.ufrpe.apoo.identidade.controladores;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.identidade.dto.LoginDTO;
import br.com.ufrpe.apoo.identidade.dto.UsuarioDTO;
import br.com.ufrpe.apoo.identidade.servico.UsuarioService;

@RestController
public class IdentidadeController {

    private final UsuarioService usuarioService;

    public IdentidadeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/criar-usuario")
    public String criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.criarUsuario(usuarioDTO);
    }

    @PostMapping("/login")
    public Map<String, Object> solicitarLogin(@RequestBody LoginDTO loginDTO) {
        return usuarioService.login(loginDTO);
    }
}
