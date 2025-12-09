package br.com.ufrpe.apoo.financeiro.dto;

import java.time.LocalDate;
import java.util.List;

public record LancamentoResponseDTO(
                Long id,
                String descricao,
                Double valor,
                LocalDate dataLancamento,
                int quantidadeParcelas,
                boolean recorrente,
                String tipo,
                String usuarioId,
                Long cartaoId,
                List<TagResponseDTO> tags) {
}
