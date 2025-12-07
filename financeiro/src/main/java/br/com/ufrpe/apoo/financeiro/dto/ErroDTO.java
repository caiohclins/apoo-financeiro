package br.com.ufrpe.apoo.financeiro.dto;

import java.time.LocalDateTime;

public record ErroDTO(LocalDateTime timestamp, int status, String error, String message, String path) {
}
