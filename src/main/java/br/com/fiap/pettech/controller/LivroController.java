package br.com.fiap.pettech.controller;

import br.com.fiap.pettech.dto.LivroDTO;
import br.com.fiap.pettech.dto.ProdutoDTO;
import br.com.fiap.pettech.service.LivroService;
import br.com.fiap.pettech.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/livros")
public class LivroController {
    @Autowired
    private LivroService service;

    @GetMapping
    public ResponseEntity<Collection<LivroDTO>> findAll(){
        var livros = service.findAll();
        return ResponseEntity.ok(livros);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTO> findById(@PathVariable Long id) {
        var livro = service.findById(id);
        return ResponseEntity.ok(livro);
    }

    @PostMapping
    public ResponseEntity<LivroDTO> save(@RequestBody LivroDTO livroDTO){
        livroDTO = service.save(livroDTO);
        return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(livroDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTO> update(
            @PathVariable Long id,
            @RequestBody LivroDTO livroDTO) {

        livroDTO = service.update(id,livroDTO);

        return ResponseEntity.ok(livroDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
