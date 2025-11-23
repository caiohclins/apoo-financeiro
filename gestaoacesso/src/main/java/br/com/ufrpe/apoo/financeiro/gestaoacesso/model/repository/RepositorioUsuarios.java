package br.com.ufrpe.apoo.financeiro.gestaoacesso.model.repository;

import br.com.ufrpe.apoo.financeiro.gestaoacesso.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuarios extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByIdIdentidade(String idIdentidade);
    Optional<Usuario> findByEmail(String email);
}