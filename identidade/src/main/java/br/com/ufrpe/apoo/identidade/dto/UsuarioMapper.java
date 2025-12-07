package br.com.ufrpe.apoo.identidade.dto;

import br.com.ufrpe.apoo.identidade.dominio.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        return usuario;
    }

    public UsuarioDTO toDTO(Usuario entity) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        return dto;
    }
}
