package br.com.fiap.pettech.service;

import br.com.fiap.pettech.ControllerNotFoundException;
import br.com.fiap.pettech.dto.LivroDTO;
import br.com.fiap.pettech.dto.ProdutoDTO;
import br.com.fiap.pettech.entities.Livro;
import br.com.fiap.pettech.entities.Produto;
import br.com.fiap.pettech.repository.LivroRepository;
import br.com.fiap.pettech.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LivroService {
    @Autowired
    private LivroRepository repo;

    public Collection<LivroDTO> findAll() {
        var livros = repo.findAll();
        return livros
                .stream()
                .map(this::toLivroDTO)        //Transforma cada produto em ProdutoDTO
                .collect(Collectors.toList());  //Devolve a Lista
    }

    public LivroDTO findById(Long id) {
        var livro =
                repo.findById(id).orElseThrow(
                        () -> new ControllerNotFoundException("Livro não Encontrado !!!!")
                );
        return toLivroDTO(livro);
    }

    public LivroDTO save(LivroDTO livroDTO) {
        Livro livro = toLivro(livroDTO);
        livro = repo.save(livro);
        return toLivroDTO(livro);
    }

    public LivroDTO update(Long id, LivroDTO livroDTO) {
        try {
            Livro buscaLivro = repo.getReferenceById(id);
            buscaLivro.setNome(livroDTO.nome());
            buscaLivro = repo.save(buscaLivro);

            return toLivroDTO(buscaLivro);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Livro não Encontrado !!!!!!!!");
        }
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    private LivroDTO toLivroDTO(Livro livro) {
        return new LivroDTO(
                livro.getId(),
                livro.getNome()
        );
    }

    private Livro toLivro(LivroDTO livroDTO) {
        return new Livro(
                livroDTO.id(),
                livroDTO.nome()
        );
    }
}
