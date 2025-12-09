package br.com.ufrpe.apoo.credito.dto;

public record CartaoRequestDTO(
        String nome,
        Double limite,
        int diaVencimentoFatura,
        int diaFechamentoFatura) {
}
