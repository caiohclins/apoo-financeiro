package br.com.ufrpe.apoo.financeiro.controladores;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ufrpe.apoo.financeiro.dto.TagRequestDTO;
import br.com.ufrpe.apoo.financeiro.dto.TagResponseDTO;
import br.com.ufrpe.apoo.financeiro.servico.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public List<TagResponseDTO> listarTags() {
        return tagService.listarTags();
    }

    @PostMapping
    public TagResponseDTO criarTag(@RequestBody TagRequestDTO tagRequest) {
        return tagService.criarTag(tagRequest);
    }

    @PutMapping("/{id}")
    public TagResponseDTO atualizarTag(@PathVariable Long id, @RequestBody TagRequestDTO tagRequest) {
        return tagService.atualizarTag(id, tagRequest);
    }

    @GetMapping("/{id}")
    public TagResponseDTO buscarTag(@PathVariable Long id) {
        return tagService.buscarTag(id);
    }

    @DeleteMapping("/{id}")
    public void deletarTag(@PathVariable Long id) {
        tagService.deletarTag(id);
    }
}
