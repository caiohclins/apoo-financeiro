package br.com.ufrpe.apoo.financeiro.controladores;

import br.com.ufrpe.apoo.financeiro.dominio.Tag;
import br.com.ufrpe.apoo.financeiro.repositorio.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping
    public List<Tag> listarTags() {
        return tagRepository.findAll();
    }

    @PostMapping
    public Tag criarTag(@RequestBody Tag tag) {
        return tagRepository.save(tag);
    }

    @GetMapping("/{id}")
    public Tag buscarTag(@PathVariable Long id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tag não encontrada"));
    }

    @DeleteMapping("/{id}")
    public void deletarTag(@PathVariable Long id) {
        tagRepository.deleteById(id);
    }
}
