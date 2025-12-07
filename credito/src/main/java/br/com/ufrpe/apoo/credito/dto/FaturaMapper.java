package br.com.ufrpe.apoo.credito.dto;

import br.com.ufrpe.apoo.credito.dominio.Fatura;
import org.springframework.stereotype.Component;

@Component
public class FaturaMapper {

    public Fatura toEntity(FaturaRequestDTO dto) {
        Fatura fatura = new Fatura();
        fatura.setDataVencimento(dto.dataVencimento());
        fatura.setValorTotal(dto.valorTotal());
        fatura.setFechada(dto.fechada());
        return fatura;
    }

    public FaturaResponseDTO toDTO(Fatura entity) {
        return new FaturaResponseDTO(
                entity.getId(),
                entity.getDataVencimento(),
                entity.getValorTotal(),
                entity.isFechada(),
                entity.getCartao() != null ? entity.getCartao().getId() : null);
    }
}
