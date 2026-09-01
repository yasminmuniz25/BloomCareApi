package br.com.fiap.BloomCare.dto;

import br.com.fiap.BloomCare.entities.Nutriz;
import br.com.fiap.BloomCare.entities.Triagem;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TriagemDto {

    private Long id;

    @NotNull(message = "O Id da Nutriz é obrigatório")
    private Long nutrizId;

    @NotNull(message = "A data da triagem é obrigatória")
    @Future(message = "A data da triagem deve ser futura")
    private LocalDate dataTriagem;

    @NotNull(message = "O resultado da triagem é obrigatório.")
    private Boolean aptaDoacao;

    public TriagemDto(Triagem triagem){
        id = triagem.getId();
        nutrizId=triagem.getNutriz().getId();
        dataTriagem = triagem.getDataTriagem();
        aptaDoacao = triagem.getAptaDoacao();
    }

    public Triagem toEntity(Nutriz nutriz){
        Triagem triagem = new Triagem();
        triagem.setId(id);
        triagem.setNutriz(nutriz);
        triagem.setDataTriagem(dataTriagem);
        triagem.setAptaDoacao(aptaDoacao);
        return triagem;
    }
}
