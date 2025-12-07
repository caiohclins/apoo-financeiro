package br.com.ufrpe.apoo.financeiro.dto;

import br.com.ufrpe.apoo.financeiro.dominio.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {

    public Tag toEntity(TagRequestDTO dto) {
        Tag tag = new Tag();
        tag.setNome(dto.nome());
        tag.setCor(dto.cor());
        return tag;
    }

    public TagResponseDTO toDTO(Tag entity) {
        return new TagResponseDTO(entity.getId(), entity.getNome(), entity.getCor());
    }
}
