package br.com.fiap.BloomCare.repository;

import br.com.fiap.BloomCare.entities.Agendamento;
import br.com.fiap.BloomCare.entities.AgendamentoStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    List<Agendamento> findByNutrizId(Long nutrizId);

    // Buscar agendamentos por status
    List<Agendamento> findByStatus(AgendamentoStatus status);

    // Buscar agendamentos futuros
    List<Agendamento> findByDataHoraAfter(LocalDateTime dataHora);

}
