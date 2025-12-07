package br.com.ufrpe.apoo.financeiro.servico;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.ufrpe.apoo.financeiro.dominio.Tag;
import br.com.ufrpe.apoo.financeiro.dto.TagMapper;
import br.com.ufrpe.apoo.financeiro.dto.TagRequestDTO;
import br.com.ufrpe.apoo.financeiro.dto.TagResponseDTO;
import br.com.ufrpe.apoo.financeiro.excecao.RecursoNaoEncontradoException;
import br.com.ufrpe.apoo.financeiro.repositorio.TagRepository;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<TagResponseDTO> listarTags() {
        return tagRepository.findAll().stream()
                .map(tagMapper::toDTO)
                .collect(Collectors.toList());
    }

    public TagResponseDTO criarTag(TagRequestDTO dto) {
        Tag tag = tagMapper.toEntity(dto);
        Tag salvo = tagRepository.save(tag);
        return tagMapper.toDTO(salvo);
    }

    public TagResponseDTO buscarTag(Long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Tag não encontrada"));
        return tagMapper.toDTO(tag);
    }

    public void deletarTag(Long id) {
        tagRepository.deleteById(id);
    }
}
