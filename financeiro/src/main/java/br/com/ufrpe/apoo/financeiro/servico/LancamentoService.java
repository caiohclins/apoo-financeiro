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

    public void deletarLancamento(Long id, String usuarioId) {
        Lancamento lancamento = lancamentoRepository.findById(id).orElse(null);
        if (lancamento != null) {
            if (!lancamento.getUsuarioId().equals(usuarioId)) {
                throw new AcessoNegadoException("Acesso negado");
            }
            lancamentoRepository.deleteById(id);
        }
    }
}
