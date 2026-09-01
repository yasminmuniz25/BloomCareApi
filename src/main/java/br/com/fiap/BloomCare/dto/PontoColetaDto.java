package br.com.fiap.BloomCare.dto;

import br.com.fiap.BloomCare.entities.PontoColeta;
import br.com.fiap.BloomCare.entities.TipoColeta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PontoColetaDto {

    private Long id;

    @NotNull(message = "O tipo de coleta é obrigatório")
    private TipoColeta tipoColeta;

    @NotBlank(message = "O campo endereço é obrigatório")
    private String endereco;

    @NotBlank(message = "O campo cidade é obrigatório")
    private String cidade;

    @NotBlank(message = "O campo estado é obrigatório")
    private String estado;

    @NotBlank(message = "O campo telefone é obrigatório")
    private String telefone;


    //construtor a partir do entity
    public PontoColetaDto(PontoColeta pontoColeta){
        id = pontoColeta.getId();
        tipoColeta = pontoColeta.getTipoColeta();
        endereco = pontoColeta.getEndereco();
        cidade = pontoColeta.getCidade();
        estado = pontoColeta.getEstado();
        telefone = pontoColeta.getTelefone();
    }

    //conversão para entity
    public PontoColeta toEntity(){
        PontoColeta ponto = new PontoColeta();
        ponto.setId(id);
        ponto.setTipoColeta(tipoColeta);
        ponto.setEndereco(endereco);
        ponto.setCidade(cidade);
        ponto.setEstado(estado);
        ponto.setTelefone(telefone);
        return ponto;
    }

}
