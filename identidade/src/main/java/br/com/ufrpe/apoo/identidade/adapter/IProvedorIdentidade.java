package br.com.ufrpe.apoo.identidade.adapter;

import java.util.Map;

import br.com.ufrpe.apoo.identidade.dominio.Usuario;

public interface IProvedorIdentidade {
    String criarUsuario(Usuario usuario, String senha);

    Map<String, Object> login(String email, String senha);
}
