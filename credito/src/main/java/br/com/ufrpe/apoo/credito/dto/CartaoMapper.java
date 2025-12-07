package br.com.ufrpe.apoo.credito.dto;

import org.springframework.stereotype.Component;

import br.com.ufrpe.apoo.credito.dominio.Cartao;

@Component
public class CartaoMapper {

    public Cartao toEntity(CartaoRequestDTO dto) {
        Cartao cartao = new Cartao();
        cartao.setNome(dto.nome());
        cartao.setNumero(dto.numero());
        cartao.setLimite(dto.limite());
        cartao.setDiaVencimentoFatura(dto.diaVencimentoFatura());
        cartao.setMelhorDiaCompra(dto.melhorDiaCompra());
        return cartao;
    }

    public CartaoResponseDTO toDTO(Cartao entity) {
        return new CartaoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getNumero(),
                entity.getLimite(),
                entity.getUsuarioId(),
                entity.getDiaVencimentoFatura(),
                entity.getMelhorDiaCompra());
    }
}
