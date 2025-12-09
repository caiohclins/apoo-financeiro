package br.com.ufrpe.apoo.credito.repositorio;

import br.com.ufrpe.apoo.credito.dominio.Cartao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
    List<Cartao> findByIdIdentidade(String idIdentidade);
}
