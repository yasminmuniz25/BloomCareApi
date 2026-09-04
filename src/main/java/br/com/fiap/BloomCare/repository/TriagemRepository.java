package br.com.fiap.BloomCare.repository;

import br.com.fiap.BloomCare.entities.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TriagemRepository extends JpaRepository<Triagem, Long> {
    Optional<Triagem> findByNutrizId(Long nutrizId);
}
