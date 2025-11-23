package br.com.ufrpe.apoo.financeiro.gestaoacesso.model.service;

import java.util.Map;

public interface IProvedorIdentidade {

    String criarConta(String email, String password);
    Map<String, Object> autenticar(String email, String password);
}