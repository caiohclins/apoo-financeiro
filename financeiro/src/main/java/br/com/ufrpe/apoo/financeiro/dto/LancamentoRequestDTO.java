package br.com.ufrpe.apoo.financeiro.dto;

import java.time.LocalDate;
import java.util.List;

public record LancamentoRequestDTO(
        String nome,
        String descricao,
        Double valor,
        LocalDate dataPagamento,
        int numeroParcelas,
        boolean recorrente,
        String tipo,
        Long cartaoId,
        List<Long> tagIds) {
}
