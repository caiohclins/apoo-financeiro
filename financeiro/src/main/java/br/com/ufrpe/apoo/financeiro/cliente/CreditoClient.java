package br.com.ufrpe.apoo.financeiro.cliente;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import br.com.ufrpe.apoo.financeiro.dto.CartaoResponseDTO;

@FeignClient(name = "credito", url = "${application.credito.url}")
public interface CreditoClient {
    @GetMapping("/cartoes")
    List<CartaoResponseDTO> listarCartoes();
}
