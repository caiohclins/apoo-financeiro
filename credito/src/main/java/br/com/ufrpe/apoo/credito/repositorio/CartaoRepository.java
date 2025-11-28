package br.com.ufrpe.apoo.credito.repositorio;

import br.com.ufrpe.apoo.credito.dominio.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    java.util.List<Cartao> findByUsuarioId(String usuarioId);
}
