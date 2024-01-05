package br.com.fiap.pettech.repository;

import br.com.fiap.pettech.entities.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
}
