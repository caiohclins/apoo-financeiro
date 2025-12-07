package br.com.ufrpe.apoo.credito.dto;

public record CartaoResponseDTO(
        Long id,
        String nome,
        String numero,
        Double limite,
        String usuarioId,
        int diaVencimentoFatura,
        int melhorDiaCompra) {
}
