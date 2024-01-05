package br.com.fiap.pettech.service;

import br.com.fiap.pettech.ControllerNotFoundException;
import br.com.fiap.pettech.dto.ProdutoDTO;
import br.com.fiap.pettech.entities.Produto;
import br.com.fiap.pettech.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;

    public Collection<ProdutoDTO> findAll() {
        var produtos = repo.findAll();
        return produtos
                .stream()
                .map(this::toProdutoDTO)        //Transforma cada produto em ProdutoDTO
                .collect(Collectors.toList());  //Devolve a Lista
    }

    public ProdutoDTO findById(UUID id) {
        var produto =
                repo.findById(id).orElseThrow(
                        () -> new ControllerNotFoundException("Produto não Encontrado !!!!")
                );
        return toProdutoDTO(produto);
    }

    public ProdutoDTO save(ProdutoDTO produtoDTO) {
        Produto produto = toProduto(produtoDTO);
        produto = repo.save(produto);
        return toProdutoDTO(produto);
    }

    public ProdutoDTO update(UUID id, ProdutoDTO produtoDTO) {
        try {
            Produto buscaProduto = repo.getReferenceById(id);
            buscaProduto.setNome(produtoDTO.nome());
            buscaProduto.setDescricao(produtoDTO.descricao());
            buscaProduto.setUrlImagem(produtoDTO.urlImagem());
            buscaProduto.setPreco(produtoDTO.preco());
            buscaProduto = repo.save(buscaProduto);

            return toProdutoDTO(buscaProduto);
        } catch (EntityNotFoundException e) {
            throw new ControllerNotFoundException("Produto não Encontrado !!!!!!!!");
        }
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }

    private ProdutoDTO toProdutoDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getUrlImagem()
        );
    }

    private Produto toProduto(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.id(),
                produtoDTO.nome(),
                produtoDTO.descricao(),
                produtoDTO.preco(),
                produtoDTO.urlImagem()
        );
    }
}
