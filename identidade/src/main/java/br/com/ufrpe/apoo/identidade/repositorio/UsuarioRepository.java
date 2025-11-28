package br.com.ufrpe.apoo.identidade.repositorio;

import br.com.ufrpe.apoo.identidade.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
