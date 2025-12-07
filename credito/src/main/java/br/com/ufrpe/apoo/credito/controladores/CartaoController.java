package br.com.ufrpe.apoo.credito.controladores;

import java.util.List;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.credito.dto.CartaoRequestDTO;
import br.com.ufrpe.apoo.credito.dto.CartaoResponseDTO;
import br.com.ufrpe.apoo.credito.dto.FaturaRequestDTO;
import br.com.ufrpe.apoo.credito.dto.FaturaResponseDTO;

@RestController
public class CartaoController {

    private final br.com.ufrpe.apoo.credito.servico.CartaoService cartaoService;

    public CartaoController(br.com.ufrpe.apoo.credito.servico.CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @GetMapping("/cartoes")
    public List<CartaoResponseDTO> listarCartoes(JwtAuthenticationToken token) {
        return cartaoService.listarCartoes(token.getToken().getSubject());
    }

    @PostMapping("/cartoes")
    public CartaoResponseDTO criarCartao(
            @RequestBody CartaoRequestDTO cartaoRequest, JwtAuthenticationToken token) {
        return cartaoService.criarCartao(cartaoRequest, token.getToken().getSubject());
    }

    @GetMapping("/cartoes/{id}/faturas")
    public List<FaturaResponseDTO> listarFaturas(@PathVariable Long id,
            JwtAuthenticationToken token) {
        return cartaoService.listarFaturas(id, token.getToken().getSubject());
    }

    @PostMapping("/cartoes/{id}/faturas")
    public FaturaResponseDTO criarFatura(@PathVariable Long id,
            @RequestBody FaturaRequestDTO faturaRequest, JwtAuthenticationToken token) {
        return cartaoService.criarFatura(id, faturaRequest, token.getToken().getSubject());
    }

    @GetMapping("/cartoes/{id}")
    public CartaoResponseDTO buscarCartao(@PathVariable Long id,
            JwtAuthenticationToken token) {
        return cartaoService.buscarCartao(id, token.getToken().getSubject());
    }

    @DeleteMapping("/cartoes/{id}")
    public void deletarCartao(@PathVariable Long id, JwtAuthenticationToken token) {
        cartaoService.deletarCartao(id, token.getToken().getSubject());
    }
}
