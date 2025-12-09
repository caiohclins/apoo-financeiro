package br.com.ufrpe.apoo.financeiro.repositorio;

import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
    List<Lancamento> findByTipo(String tipo);

    List<Lancamento> findByIdIdentidade(String idIdentidade);

    List<Lancamento> findByCartaoIdAndDataPagamentoBetween(Long cartaoId, java.time.LocalDate inicio,
            java.time.LocalDate fim);
}
