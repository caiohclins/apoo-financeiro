package br.com.ufrpe.apoo.financeiro.controladores;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public List<LancamentoResponseDTO> buscarLancamentos(
            JwtAuthenticationToken token) {
        return lancamentoService.buscarLancamentos(token.getToken().getSubject());
    }

    @PostMapping("/lancamentos")
    public LancamentoResponseDTO criarLancamento(
            @RequestBody LancamentoRequestDTO lancamentoRequest,
            JwtAuthenticationToken token) {
        return lancamentoService.criarLancamento(lancamentoRequest, token.getToken().getSubject());
    }

    @PutMapping("/lancamentos/{id}")
    public LancamentoResponseDTO atualizarLancamento(@PathVariable Long id,
            @RequestBody LancamentoRequestDTO lancamentoRequest,
            JwtAuthenticationToken token) {
        return lancamentoService.atualizarLancamento(id, lancamentoRequest, token.getToken().getSubject());
    }

    @GetMapping("/lancamentos/{id}")
    public LancamentoResponseDTO buscarLancamentoPorId(@PathVariable Long id,
            JwtAuthenticationToken token) {
        return lancamentoService.buscarLancamentoPorId(id, token.getToken().getSubject());
    }

    @DeleteMapping("/lancamentos/{id}")
    public void excluirLancamento(@PathVariable Long id, JwtAuthenticationToken token) {
        lancamentoService.excluirLancamento(id, token.getToken().getSubject());
    }

    @GetMapping("/lancamentos/cartao/{cartaoId}")
    public List<LancamentoResponseDTO> buscarLancamentosPorCartao(
            @PathVariable Long cartaoId,
            @org.springframework.web.bind.annotation.RequestParam int mes,
            @org.springframework.web.bind.annotation.RequestParam int ano) {
        return lancamentoService.buscarLancamentosPorCartao(cartaoId, mes, ano);
    }
}
