package br.com.ufrpe.apoo.credito.dto;

public record CartaoResponseDTO(
                Long id,
                String nome,
                Double limite,
                String usuarioId,
                int diaVencimentoFatura,
                int diaFechamentoFatura) {
}
