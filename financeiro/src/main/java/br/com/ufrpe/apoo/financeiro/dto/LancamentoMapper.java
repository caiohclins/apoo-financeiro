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
        lancamento.setNome(dto.nome());
        lancamento.setDescricao(dto.descricao());
        lancamento.setValor(dto.valor());
        lancamento.setDataPagamento(dto.dataPagamento());
        lancamento.setNumeroParcelas(dto.numeroParcelas());
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
                entity.getNome(),
                entity.getDescricao(),
                entity.getValor(),
                entity.getDataPagamento(),
                entity.getNumeroParcelas(),
                entity.isRecorrente(),
                entity.getTipo(),
                entity.getUsuarioId(),
                entity.getCartaoId(),
                tags);
    }
}
