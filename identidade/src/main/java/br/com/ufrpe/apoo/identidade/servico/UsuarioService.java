package br.com.ufrpe.apoo.identidade.servico;

import br.com.ufrpe.apoo.identidade.adapter.IProvedorIdentidade;
import br.com.ufrpe.apoo.identidade.dominio.Usuario;
import br.com.ufrpe.apoo.identidade.dto.LoginDTO;
import br.com.ufrpe.apoo.identidade.dto.UsuarioDTO;
import br.com.ufrpe.apoo.identidade.repositorio.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsuarioService {

    private final IProvedorIdentidade provedorIdentidade;
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(IProvedorIdentidade provedorIdentidade, UsuarioRepository usuarioRepository) {
        this.provedorIdentidade = provedorIdentidade;
        this.usuarioRepository = usuarioRepository;
    }

    public String criarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setEmail(usuarioDTO.getEmail());

        String keycloakId = provedorIdentidade.criarUsuario(usuario, usuarioDTO.getSenha());
        usuario.setKeycloakId(keycloakId);
        usuarioRepository.save(usuario);
        return keycloakId;
    }

    public Map<String, Object> login(LoginDTO loginDTO) {
        return provedorIdentidade.login(loginDTO.getEmail(), loginDTO.getSenha());
    }
}
