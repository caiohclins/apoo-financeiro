package br.com.ufrpe.apoo.financeiro.dto;

import java.time.LocalDate;
import java.util.List;

public record LancamentoRequestDTO(
                String descricao,
                Double valor,
                LocalDate dataLancamento,
                int quantidadeParcelas,
                boolean recorrente,
                String tipo,
                Long cartaoId,
                List<Long> tagIds) {
}
