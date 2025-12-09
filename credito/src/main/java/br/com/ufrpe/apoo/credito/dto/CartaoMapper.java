package br.com.ufrpe.apoo.credito.dto;

import org.springframework.stereotype.Component;

import br.com.ufrpe.apoo.credito.dominio.Cartao;

@Component
public class CartaoMapper {

    public Cartao toEntity(CartaoRequestDTO dto) {
        Cartao cartao = new Cartao();
        cartao.setNome(dto.nome());
        cartao.setLimite(dto.limite());
        cartao.setDiaVencimentoFatura(dto.diaVencimentoFatura());
        cartao.setDiaFechamentoFatura(dto.diaFechamentoFatura());
        return cartao;
    }

    public CartaoResponseDTO toDTO(Cartao entity) {
        return new CartaoResponseDTO(
                entity.getId(),
                entity.getNome(),
                entity.getLimite(),
                entity.getUsuarioId(),
                entity.getDiaVencimentoFatura(),
                entity.getDiaFechamentoFatura());
    }
}
