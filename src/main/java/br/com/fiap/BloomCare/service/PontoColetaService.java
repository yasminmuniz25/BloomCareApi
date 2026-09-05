package br.com.fiap.BloomCare.service;

import br.com.fiap.BloomCare.dto.PontoColetaDto;
import br.com.fiap.BloomCare.entities.PontoColeta;
import br.com.fiap.BloomCare.exceptions.ResourceNotFoundException;
import br.com.fiap.BloomCare.repository.PontoColetaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PontoColetaService {

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    @Transactional(readOnly = true)
    public List<PontoColetaDto> findAllPontosColeta() {
        return pontoColetaRepository.findAll().stream().map(PontoColetaDto::new).toList();
    }

    @Transactional(readOnly = true)
    public PontoColetaDto findPontoColetaById(Long id) {
        PontoColeta pontoColeta = pontoColetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado. ID: " + id));
        return new PontoColetaDto(pontoColeta);
    }

    @Transactional
    public PontoColetaDto savePontoColeta(PontoColetaDto pontoColetaDto) {
        PontoColeta ponto = pontoColetaDto.toEntity();
        ponto = pontoColetaRepository.save(ponto);
        return new PontoColetaDto(ponto);
    }

    @Transactional
    public PontoColetaDto updatePontoColeta(Long id, PontoColetaDto pontoColetaDto) {
        try {
            PontoColeta pontoColeta = pontoColetaRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Ponto coleta não encontrado. ID: " + id));
            mapperDtoToPontoColeta(pontoColetaDto, pontoColeta);
            pontoColeta = pontoColetaRepository.save(pontoColeta);
            return new PontoColetaDto(pontoColeta);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Erro ao atualizar Ponto de coleta.");
        }
    }

    @Transactional
    public void deletePontoColeta(Long id) {
        if (!pontoColetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ponto de coleta não encontrado. ID: " + id);
        }
        pontoColetaRepository.deleteById(id);
    }

    private void mapperDtoToPontoColeta(PontoColetaDto pontoColetaDto, PontoColeta pontoColeta) {
        pontoColeta.setTipoColeta(pontoColetaDto.getTipoColeta());
        pontoColeta.setEndereco(pontoColetaDto.getEndereco());
        pontoColeta.setCidade(pontoColetaDto.getCidade());
        pontoColeta.setEstado(pontoColetaDto.getEstado());
        pontoColeta.setTelefone(pontoColetaDto.getTelefone());
    }


}
