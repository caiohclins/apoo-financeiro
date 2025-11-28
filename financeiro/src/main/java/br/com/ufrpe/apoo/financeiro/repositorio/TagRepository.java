package br.com.ufrpe.apoo.financeiro.repositorio;

import br.com.ufrpe.apoo.financeiro.dominio.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
