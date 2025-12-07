package br.com.ufrpe.apoo.financeiro.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;
import br.com.ufrpe.apoo.financeiro.dominio.Tag;

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class FinanceiroController {

    private final br.com.ufrpe.apoo.financeiro.repositorio.LancamentoRepository lancamentoRepository;
    private final br.com.ufrpe.apoo.financeiro.repositorio.TagRepository tagRepository;

    public FinanceiroController(br.com.ufrpe.apoo.financeiro.repositorio.LancamentoRepository lancamentoRepository,
            br.com.ufrpe.apoo.financeiro.repositorio.TagRepository tagRepository) {
        this.lancamentoRepository = lancamentoRepository;
        this.tagRepository = tagRepository;
    }

    @GetMapping("/lancamentos")
    public List<Lancamento> listarLancamentos(JwtAuthenticationToken token) {
        return lancamentoRepository.findByUsuarioId(token.getToken().getSubject());
    }

    @PostMapping("/lancamentos")
    public Lancamento criarLancamento(@RequestBody Lancamento lancamento, JwtAuthenticationToken token) {
        lancamento.setUsuarioId(token.getToken().getSubject());

        if (lancamento.getTagIds() != null && !lancamento.getTagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(lancamento.getTagIds());
            lancamento.setTags(tags);
        }

        return lancamentoRepository.save(lancamento);
    }

    @GetMapping("/lancamentos/{id}")
    public Lancamento buscarLancamento(@PathVariable Long id, JwtAuthenticationToken token) {
        Lancamento lancamento = lancamentoRepository.findById(id).orElse(null);
        if (lancamento != null && !lancamento.getUsuarioId().equals(token.getToken().getSubject())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
        }
        return lancamento;
    }

    @DeleteMapping("/lancamentos/{id}")
    public void deletarLancamento(@PathVariable Long id, JwtAuthenticationToken token) {
        Lancamento lancamento = lancamentoRepository.findById(id).orElse(null);
        if (lancamento != null) {
            if (!lancamento.getUsuarioId().equals(token.getToken().getSubject())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado");
            }
            lancamentoRepository.deleteById(id);
        }
    }
}
