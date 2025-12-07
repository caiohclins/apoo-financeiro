package br.com.ufrpe.apoo.financeiro.controladores;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.financeiro.dto.LancamentoRequestDTO;
import br.com.ufrpe.apoo.financeiro.dto.LancamentoResponseDTO;
import br.com.ufrpe.apoo.financeiro.servico.LancamentoService;

@RestController
public class FinanceiroController {

    private final LancamentoService lancamentoService;

    public FinanceiroController(LancamentoService lancamentoService) {
        this.lancamentoService = lancamentoService;
    }

    @GetMapping("/lancamentos")
    public List<LancamentoResponseDTO> listarLancamentos(
            JwtAuthenticationToken token) {
        return lancamentoService.listarLancamentos(token.getToken().getSubject());
    }

    @PostMapping("/lancamentos")
    public LancamentoResponseDTO criarLancamento(
            @RequestBody LancamentoRequestDTO lancamentoRequest,
            JwtAuthenticationToken token) {
        return lancamentoService.criarLancamento(lancamentoRequest, token.getToken().getSubject());
    }

    @GetMapping("/lancamentos/{id}")
    public LancamentoResponseDTO buscarLancamento(@PathVariable Long id,
            JwtAuthenticationToken token) {
        return lancamentoService.buscarLancamento(id, token.getToken().getSubject());
    }

    @DeleteMapping("/lancamentos/{id}")
    public void deletarLancamento(@PathVariable Long id, JwtAuthenticationToken token) {
        lancamentoService.deletarLancamento(id, token.getToken().getSubject());
    }
}
