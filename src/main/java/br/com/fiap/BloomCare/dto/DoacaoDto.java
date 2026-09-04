package br.com.fiap.BloomCare.dto;

import br.com.fiap.BloomCare.entities.Agendamento;
import br.com.fiap.BloomCare.entities.Doacao;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DoacaoDto {
    private Long id;

    @NotNull(message = "O campo data da doacao é obrigatório")
    private LocalDate dataDoacao;

    @NotNull(message = "O campo quantidade de ML é obrigatório")
    private Double quantidadeMl;

    @NotNull(message = "O campo agendamento é obrigatório")
    private Long agendamentoId;

    public DoacaoDto(Doacao doacao){
        id = doacao.getId();
        dataDoacao = doacao.getDataDoacao();
        quantidadeMl = doacao.getQuantidadeMl();
        agendamentoId= doacao.getAgendamento().getId();
    }


    public Doacao toEntity(Agendamento agendamento){
        Doacao entity = new Doacao();
        entity.setId(id);
        entity.setDataDoacao(dataDoacao);
        entity.setQuantidadeMl(quantidadeMl);
        entity.setAgendamento(agendamento);
        return entity;
    }
}

