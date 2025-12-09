package br.com.ufrpe.apoo.financeiro.dto;

import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LancamentoMapper {

    private final TagMapper tagMapper;

    public LancamentoMapper(TagMapper tagMapper) {
        this.tagMapper = tagMapper;
    }

    public Lancamento toEntity(LancamentoRequestDTO dto) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDescricao(dto.descricao());
        lancamento.setValor(dto.valor());
        lancamento.setDataLancamento(dto.dataLancamento());
        lancamento.setQuantidadeParcelas(dto.quantidadeParcelas());
        lancamento.setRecorrente(dto.recorrente());
        lancamento.setTipo(dto.tipo());
        lancamento.setCartaoId(dto.cartaoId());
        return lancamento;
    }

    public LancamentoResponseDTO toDTO(Lancamento entity) {
        List<TagResponseDTO> tags = entity.getTags() != null
                ? entity.getTags().stream().map(tagMapper::toDTO).collect(Collectors.toList())
                : List.of();

        return new LancamentoResponseDTO(
                entity.getId(),
                entity.getDescricao(),
                entity.getValor(),
                entity.getDataLancamento(),
                entity.getQuantidadeParcelas(),
                entity.isRecorrente(),
                entity.getTipo(),
                entity.getIdIdentidade(),
                entity.getCartaoId(),
                tags);
    }
}
