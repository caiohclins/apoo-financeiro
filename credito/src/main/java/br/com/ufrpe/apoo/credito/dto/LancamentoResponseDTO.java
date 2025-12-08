package br.com.ufrpe.apoo.credito.dto;

import java.time.LocalDate;

public record LancamentoResponseDTO(
                Long id,
                String nome,
                Double valor,
                LocalDate dataPagamento,
                int numeroParcelas,
                boolean recorrente,
                String tipo) {
}
