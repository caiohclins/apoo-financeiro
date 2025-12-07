package br.com.ufrpe.apoo.credito.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.ufrpe.apoo.credito.dominio.Fatura;

@Repository
public interface FaturaRepository extends JpaRepository<Fatura, Long> {
    List<Fatura> findByCartaoId(Long cartaoId);
}
