package br.com.ufrpe.apoo.financeiro.gestaoacesso.model.service;

import br.com.ufrpe.apoo.financeiro.gestaoacesso.model.entity.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FachadaAutenticacao {

    private final IProvedorIdentidade provedorIdentidade;
    private final ServicoUsuarios servicoUsuarios;

    public Usuario criarConta(String email, String password) {
        String idIdentidade = provedorIdentidade.criarConta(email, password);

        Usuario usuario = new Usuario();
        usuario.setIdIdentidade(idIdentidade);
        usuario.setNome(email);
        usuario.setEmail(email);

        return servicoUsuarios.salvarUsuario(usuario);
    }

    public Map<String, Object> solicitarLogin(String email, String password) {
        return provedorIdentidade.autenticar(email, password);
    }
}