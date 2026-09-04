package br.com.fiap.BloomCare.repository;

import br.com.fiap.BloomCare.entities.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}
