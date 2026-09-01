package br.com.fiap.BloomCare.dto;

import br.com.fiap.BloomCare.entities.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AgendamentoDto {
    private Long id;

    @NotNull(message = "O Id da Nutriz é obrigatório")
    private Long nutrizId;

    @NotNull(message = "O ponto de coleta é obrigatório")
    private Long pontoColetaId;

    @NotNull(message = "A data e hora são obrigatórias")
    private LocalDateTime dataHora;

    @NotNull(message = "O tipo de coleta é obrigatório")
    private TipoColeta tipoColeta;

    private AgendamentoStatus status;

    public AgendamentoDto(Agendamento agendamento) {
        id = agendamento.getId();
        nutrizId = agendamento.getNutriz().getId();
        pontoColetaId = agendamento.getPontoColeta().getId();
        dataHora = agendamento.getDataHora();
        tipoColeta = agendamento.getTipoColeta();
        status = agendamento.getStatus();
    }

    // Conversão inversa
    public Agendamento toEntity(Nutriz nutriz, PontoColeta pontoColeta) {
        Agendamento agendamento = new Agendamento();
        agendamento.setId(id);
        agendamento.setNutriz(nutriz);
        agendamento.setPontoColeta(pontoColeta);
        agendamento.setDataHora(dataHora);
        agendamento.setTipoColeta(tipoColeta);
        agendamento.setStatus(status);
        return agendamento;
    }


}
