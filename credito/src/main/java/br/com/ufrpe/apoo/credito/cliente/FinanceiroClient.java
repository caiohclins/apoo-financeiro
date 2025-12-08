package br.com.ufrpe.apoo.credito.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.ufrpe.apoo.credito.dto.LancamentoResponseDTO;

@FeignClient(name = "financeiro", url = "${application.financeiro.url}")
public interface FinanceiroClient {

    @GetMapping("/lancamentos/cartao/{cartaoId}")
    List<LancamentoResponseDTO> listarLancamentosPorCartao(
            @PathVariable("cartaoId") Long cartaoId,
            @RequestParam("mes") int mes,
            @RequestParam("ano") int ano);
}
