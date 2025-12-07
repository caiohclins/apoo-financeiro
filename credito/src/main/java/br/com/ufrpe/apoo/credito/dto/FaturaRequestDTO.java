package br.com.ufrpe.apoo.credito.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FaturaRequestDTO(
        LocalDate dataVencimento,
        BigDecimal valorTotal,
        boolean fechada,
        Long cartaoId) {
}
