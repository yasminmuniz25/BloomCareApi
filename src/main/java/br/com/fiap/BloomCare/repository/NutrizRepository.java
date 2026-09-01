package br.com.fiap.BloomCare.repository;

import br.com.fiap.BloomCare.entities.Nutriz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrizRepository extends JpaRepository<Nutriz, Long> {
    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
}
