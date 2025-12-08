package br.com.ufrpe.apoo.financeiro.servico;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ufrpe.apoo.financeiro.dominio.Lancamento;
import br.com.ufrpe.apoo.financeiro.dominio.Tag;
import br.com.ufrpe.apoo.financeiro.dto.LancamentoMapper;
import br.com.ufrpe.apoo.financeiro.dto.LancamentoRequestDTO;
import br.com.ufrpe.apoo.financeiro.dto.LancamentoResponseDTO;
import br.com.ufrpe.apoo.financeiro.excecao.AcessoNegadoException;
import br.com.ufrpe.apoo.financeiro.excecao.RecursoNaoEncontradoException;
import br.com.ufrpe.apoo.financeiro.repositorio.LancamentoRepository;
import br.com.ufrpe.apoo.financeiro.repositorio.TagRepository;

@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepository;
    private final TagRepository tagRepository;
    private final LancamentoMapper lancamentoMapper;

    public LancamentoService(LancamentoRepository lancamentoRepository, TagRepository tagRepository,
            LancamentoMapper lancamentoMapper) {
        this.lancamentoRepository = lancamentoRepository;
        this.tagRepository = tagRepository;
        this.lancamentoMapper = lancamentoMapper;
    }

    public List<LancamentoResponseDTO> listarLancamentos(String usuarioId) {
        return lancamentoRepository.findByUsuarioId(usuarioId).stream()
                .map(lancamentoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public LancamentoResponseDTO criarLancamento(LancamentoRequestDTO dto, String usuarioId) {
        Lancamento lancamento = lancamentoMapper.toEntity(dto);
        lancamento.setUsuarioId(usuarioId);

        if (dto.tagIds() != null && !dto.tagIds().isEmpty()) {
            List<Tag> tags = tagRepository.findAllById(dto.tagIds());
            lancamento.setTags(tags);
        }

        Lancamento salvo = lancamentoRepository.save(lancamento);
        return lancamentoMapper.toDTO(salvo);
    }

    public LancamentoResponseDTO buscarLancamento(Long id, String usuarioId) {
        Lancamento lancamento = lancamentoRepository.findById(id).orElse(null);
        if (lancamento != null && !lancamento.getUsuarioId().equals(usuarioId)) {
            throw new AcessoNegadoException("Acesso negado");
        }
        if (lancamento == null) {
            throw new RecursoNaoEncontradoException("Lançamento não encontrado");
        }
        return lancamentoMapper.toDTO(lancamento);
    }

    public LancamentoResponseDTO atualizarLancamento(Long id, LancamentoRequestDTO dto, String usuarioId) {
        Lancamento lancamento = lancamentoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Lançamento não encontrado"));

        if (!lancamento.getUsuarioId().equals(usuarioId)) {
            throw new AcessoNegadoException("Acesso negado");
        }

        lancamento.setNome(dto.nome());
        lancamento.setDescricao(dto.descricao());
        lancamento.setValor(dto.valor());
        lancamento.setDataPagamento(dto.dataPagamento());
        lancamento.setNumeroParcelas(dto.numeroParcelas());
        lancamento.setRecorrente(dto.recorrente());
        lancamento.setTipo(dto.tipo());
        lancamento.setCartaoId(dto.cartaoId());

        if (dto.tagIds() != null) {
            List<Tag> tags = tagRepository.findAllById(dto.tagIds());
            lancamento.setTags(tags);
        } else {
            lancamento.setTags(null);
        }

        lancamentoRepository.save(lancamento);
        return lancamentoMapper.toDTO(lancamento);
    }

    public void deletarLancamento(Long id, String usuarioId) {
        Lancamento lancamento = lancamentoRepository.findById(id).orElse(null);
        if (lancamento != null) {
            if (!lancamento.getUsuarioId().equals(usuarioId)) {
                throw new AcessoNegadoException("Acesso negado");
            }
            lancamentoRepository.deleteById(id);
        }
    }

    public List<LancamentoResponseDTO> listarPorCartao(Long cartaoId, int mes, int ano) {
        java.time.LocalDate inicio = java.time.LocalDate.of(ano, mes, 1);
        java.time.LocalDate fim = inicio.withDayOfMonth(inicio.lengthOfMonth());

        return lancamentoRepository.findByCartaoIdAndDataPagamentoBetween(cartaoId, inicio, fim).stream()
                .map(lancamentoMapper::toDTO)
                .collect(Collectors.toList());
    }
}
