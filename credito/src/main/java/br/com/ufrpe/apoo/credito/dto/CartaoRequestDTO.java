package br.com.ufrpe.apoo.credito.dto;

public record CartaoRequestDTO(
                String nome,
                String numero,
                Double limite,
                int diaVencimentoFatura,
                int melhorDiaCompra) {
}
