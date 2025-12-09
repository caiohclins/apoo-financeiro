package br.com.ufrpe.apoo.financeiro.dto;

import java.util.Map;

public record DashboardDTO(
        Double totalReceitas,
        Double totalDespesas,
        Map<String, Double> despesasPorTag,
        Map<String, Double> despesasPorCartao) {
}
