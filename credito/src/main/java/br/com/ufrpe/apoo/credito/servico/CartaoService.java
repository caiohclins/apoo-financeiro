package br.com.ufrpe.apoo.credito.servico;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ufrpe.apoo.credito.dominio.Cartao;
import br.com.ufrpe.apoo.credito.dominio.Fatura;
import br.com.ufrpe.apoo.credito.dto.CartaoMapper;
import br.com.ufrpe.apoo.credito.dto.CartaoRequestDTO;
import br.com.ufrpe.apoo.credito.dto.CartaoResponseDTO;
import br.com.ufrpe.apoo.credito.dto.FaturaMapper;
import br.com.ufrpe.apoo.credito.dto.FaturaRequestDTO;
import br.com.ufrpe.apoo.credito.dto.FaturaResponseDTO;
import br.com.ufrpe.apoo.credito.excecao.AcessoNegadoException;
import br.com.ufrpe.apoo.credito.excecao.RecursoNaoEncontradoException;
import br.com.ufrpe.apoo.credito.repositorio.CartaoRepository;
import br.com.ufrpe.apoo.credito.repositorio.FaturaRepository;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final FaturaRepository faturaRepository;
    private final CartaoMapper cartaoMapper;
    private final FaturaMapper faturaMapper;

    public CartaoService(CartaoRepository cartaoRepository, FaturaRepository faturaRepository,
            CartaoMapper cartaoMapper, FaturaMapper faturaMapper) {
        this.cartaoRepository = cartaoRepository;
        this.faturaRepository = faturaRepository;
        this.cartaoMapper = cartaoMapper;
        this.faturaMapper = faturaMapper;
    }

    public List<CartaoResponseDTO> listarCartoes(String usuarioId) {
        return cartaoRepository.findByUsuarioId(usuarioId).stream()
                .map(cartaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CartaoResponseDTO criarCartao(CartaoRequestDTO dto, String usuarioId) {
        Cartao cartao = cartaoMapper.toEntity(dto);
        cartao.setUsuarioId(usuarioId);
        return cartaoMapper.toDTO(cartaoRepository.save(cartao));
    }

    public CartaoResponseDTO buscarCartao(Long id, String usuarioId) {
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        if (cartao != null && !cartao.getUsuarioId().equals(usuarioId)) {
            throw new AcessoNegadoException("Acesso negado");
        }
        if (cartao == null) {
            throw new RecursoNaoEncontradoException("Cartão não encontrado");
        }
        return cartaoMapper.toDTO(cartao);
    }

    public void deletarCartao(Long id, String usuarioId) {
        Cartao cartao = cartaoRepository.findById(id).orElse(null);
        if (cartao != null) {
            if (!cartao.getUsuarioId().equals(usuarioId)) {
                throw new AcessoNegadoException("Acesso negado");
            }
            cartaoRepository.deleteById(id);
        }
    }

    public List<FaturaResponseDTO> listarFaturas(Long cartaoId, String usuarioId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));
        if (!cartao.getUsuarioId().equals(usuarioId)) {
            throw new AcessoNegadoException("Acesso negado");
        }
        return faturaRepository.findByCartaoId(cartaoId).stream()
                .map(faturaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FaturaResponseDTO criarFatura(Long cartaoId, FaturaRequestDTO dto, String usuarioId) {
        Cartao cartao = cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));
        if (!cartao.getUsuarioId().equals(usuarioId)) {
            throw new AcessoNegadoException("Acesso negado");
        }
        Fatura fatura = faturaMapper.toEntity(dto);
        fatura.setCartao(cartao);
        return faturaMapper.toDTO(faturaRepository.save(fatura));
    }
}
