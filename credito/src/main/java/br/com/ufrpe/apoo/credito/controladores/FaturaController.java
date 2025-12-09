package br.com.ufrpe.apoo.credito.controladores;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.credito.dto.FaturaDTO;
import br.com.ufrpe.apoo.credito.servico.FaturaService;

@RestController
@RequestMapping("/faturas")
public class FaturaController {

    private final FaturaService faturaService;

    public FaturaController(FaturaService faturaService) {
        this.faturaService = faturaService;
    }

    @GetMapping("/{cartaoId}")
    public FaturaDTO gerarFaturaCartao(
            @PathVariable Long cartaoId,
            @RequestParam int mes,
            @RequestParam int ano,
            JwtAuthenticationToken token) {
        return faturaService.gerarFaturaCartao(cartaoId, mes, ano, token.getToken().getSubject());
    }

    @GetMapping
    public List<FaturaDTO> listarFaturas(
            @RequestParam int mes,
            @RequestParam int ano,
            JwtAuthenticationToken token) {
        return faturaService.listarFaturas(mes, ano, token.getToken().getSubject());
    }
}
