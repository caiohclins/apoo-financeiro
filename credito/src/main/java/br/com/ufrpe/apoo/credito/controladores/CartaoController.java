package br.com.ufrpe.apoo.credito.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.credito.dominio.Cartao;
import br.com.ufrpe.apoo.credito.dominio.Fatura;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class CartaoController {

    private final br.com.ufrpe.apoo.credito.repositorio.CartaoRepository cartaoRepository;
    private final br.com.ufrpe.apoo.credito.repositorio.FaturaRepository faturaRepository;

    public CartaoController(br.com.ufrpe.apoo.credito.repositorio.CartaoRepository cartaoRepository,
            br.com.ufrpe.apoo.credito.repositorio.FaturaRepository faturaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.faturaRepository = faturaRepository;
    }

    @GetMapping("/cartoes")
    public List<Cartao> listarCartoes(JwtAuthenticationToken token) {
        return cartaoRepository.findByUsuarioId(token.getToken().getSubject());
    }

    @PostMapping("/cartoes")
    public Cartao criarCartao(@RequestBody Cartao cartao, JwtAuthenticationToken token) {
        cartao.setUsuarioId(token.getToken().getSubject());
        return cartaoRepository.save(cartao);
    }

    @GetMapping("/cartoes/{id}/faturas")
    public List<Fatura> listarFaturas(@PathVariable Long id, JwtAuthenticationToken token) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        if (!cartao.getUsuarioId().equals(token.getToken().getSubject())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        return faturaRepository.findByCartaoId(id);
    }

    @PostMapping("/cartoes/{id}/faturas")
    public Fatura criarFatura(@PathVariable Long id, @RequestBody Fatura fatura, JwtAuthenticationToken token) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cartão não encontrado"));
        if (!cartao.getUsuarioId().equals(token.getToken().getSubject())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        fatura.setCartao(cartao);
        return faturaRepository.save(fatura);
    }

    @GetMapping("/cartoes/{id}")
    public Cartao buscarCartao(@PathVariable Long id, JwtAuthenticationToken token) {
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        if (cartao != null && !cartao.getUsuarioId().equals(token.getToken().getSubject())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        return cartao;
    }

    @DeleteMapping("/cartoes/{id}")
    public void deletarCartao(@PathVariable Long id, JwtAuthenticationToken token) {
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        if (cartao != null) {
            if (!cartao.getUsuarioId().equals(token.getToken().getSubject())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
            }
            cartaoRepository.deleteById(id);
        }
    }
}
