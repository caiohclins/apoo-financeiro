package br.com.ufrpe.apoo.credito.dto;

import java.time.LocalDate;
import java.util.List;

public record FaturaDTO(
                Long cartaoId,
                String nomeCartao,
                LocalDate dataVencimento,
                Double valorTotal,
                LocalDate dataFechamento,
                List<LancamentoResponseDTO> lancamentos) {
}
