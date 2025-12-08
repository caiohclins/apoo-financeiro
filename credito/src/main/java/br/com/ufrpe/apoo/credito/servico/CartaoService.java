package br.com.ufrpe.apoo.credito.servico;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ufrpe.apoo.credito.dominio.Cartao;
import br.com.ufrpe.apoo.credito.dto.CartaoMapper;
import br.com.ufrpe.apoo.credito.dto.CartaoRequestDTO;
import br.com.ufrpe.apoo.credito.dto.CartaoResponseDTO;
import br.com.ufrpe.apoo.credito.excecao.AcessoNegadoException;
import br.com.ufrpe.apoo.credito.excecao.RecursoNaoEncontradoException;
import br.com.ufrpe.apoo.credito.repositorio.CartaoRepository;

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final CartaoMapper cartaoMapper;

    public CartaoService(CartaoRepository cartaoRepository, CartaoMapper cartaoMapper) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoMapper = cartaoMapper;
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

    public CartaoResponseDTO atualizarCartao(Long id, CartaoRequestDTO dto, String usuarioId) {
        Cartao cartao = cartaoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Cartão não encontrado"));

        if (!cartao.getUsuarioId().equals(usuarioId)) {
            throw new AcessoNegadoException("Acesso negado");
        }

        cartao.setNome(dto.nome());
        cartao.setNumero(dto.numero());
        cartao.setLimite(dto.limite());
        cartao.setDiaVencimentoFatura(dto.diaVencimentoFatura());
        cartao.setMelhorDiaCompra(dto.melhorDiaCompra());

        cartaoRepository.save(cartao);
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
}
