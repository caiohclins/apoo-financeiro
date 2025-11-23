package br.com.ufrpe.apoo.financeiro.gestaoacesso.model.service;

import br.com.ufrpe.apoo.financeiro.gestaoacesso.model.entity.Usuario;
import br.com.ufrpe.apoo.financeiro.gestaoacesso.model.repository.RepositorioUsuarios;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicoUsuarios {

    private final RepositorioUsuarios repositorioUsuarios;

    @Transactional
    public Usuario salvarUsuario(Usuario usuario) {
        return repositorioUsuarios.save(usuario);
    }

    @Transactional(readOnly = true)
    public Usuario buscarDadosUsuario(String idIdentidade) {
        return repositorioUsuarios.findByIdIdentidade(idIdentidade)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return repositorioUsuarios.findAll();
    }
}