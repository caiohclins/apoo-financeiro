package br.com.ufrpe.apoo.credito.dto;

public record CartaoResponseDTO(
                Long id,
                String nome,
                Double limite,
                String idIdentidade,
                int diaVencimentoFatura,
                int diaFechamentoFatura) {
}
