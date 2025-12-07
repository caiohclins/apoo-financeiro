package br.com.ufrpe.apoo.credito.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaResponseDTO(
        Long id,
        LocalDate dataVencimento,
        BigDecimal valorTotal,
        boolean fechada,
        Long cartaoId) {
}
