package br.com.ufrpe.apoo.identidade.controladores;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.identidade.dto.LoginDTO;
import br.com.ufrpe.apoo.identidade.dto.UsuarioDTO;
import br.com.ufrpe.apoo.identidade.adapter.IProvedorIdentidade;
import br.com.ufrpe.apoo.identidade.dominio.Usuario;

@RestController
public class IdentidadeController {

    private final IProvedorIdentidade provedorIdentidade;
    private final br.com.ufrpe.apoo.identidade.repositorio.UsuarioRepository usuarioRepository;

    public IdentidadeController(IProvedorIdentidade provedorIdentidade,
            br.com.ufrpe.apoo.identidade.repositorio.UsuarioRepository usuarioRepository) {
        this.provedorIdentidade = provedorIdentidade;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/usuarios")
    public String criarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        String keycloakId = provedorIdentidade.criarUsuario(usuario, usuarioDTO.getSenha());
        usuario.setKeycloakId(keycloakId);
        usuarioRepository.save(usuario);
        return keycloakId;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDTO loginDTO) {
        return provedorIdentidade.login(loginDTO.getEmail(), loginDTO.getSenha());
    }
}
