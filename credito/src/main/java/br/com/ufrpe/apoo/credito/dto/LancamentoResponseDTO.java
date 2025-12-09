package br.com.ufrpe.apoo.credito.dto;

import java.time.LocalDate;

public record LancamentoResponseDTO(
                Long id,
                String descricao,
                Double valor,
                LocalDate dataLancamento,
                int quantidadeParcelas,
                boolean recorrente,
                String tipo) {
}
