package br.com.ufrpe.apoo.financeiro.dto;

import java.time.LocalDate;
import java.util.List;

public record LancamentoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double valor,
        LocalDate dataPagamento,
        int numeroParcelas,
        boolean recorrente,
        String tipo,
        String usuarioId,
        Long cartaoId,
        List<TagResponseDTO> tags) {
}
