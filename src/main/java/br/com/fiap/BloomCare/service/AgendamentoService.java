package br.com.fiap.BloomCare.service;


import br.com.fiap.BloomCare.dto.AgendamentoDto;
import br.com.fiap.BloomCare.entities.Agendamento;
import br.com.fiap.BloomCare.entities.AgendamentoStatus;
import br.com.fiap.BloomCare.entities.Nutriz;
import br.com.fiap.BloomCare.entities.PontoColeta;
import br.com.fiap.BloomCare.exceptions.DatabaseException;
import br.com.fiap.BloomCare.exceptions.ResourceNotFoundException;
import br.com.fiap.BloomCare.repository.AgendamentoRepository;
import br.com.fiap.BloomCare.repository.NutrizRepository;
import br.com.fiap.BloomCare.repository.PontoColetaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private NutrizRepository nutrizRepository;

    @Autowired
    private PontoColetaRepository pontoColetaRepository;

    @Transactional(readOnly = true)
    public List<AgendamentoDto> findAllAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();
        return agendamentos.stream().map(AgendamentoDto::new).toList();
    }

    @Transactional(readOnly = true)
    public AgendamentoDto findAgendamentoById(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Agendamento não encontrado. Id: " + id)
        );
        return new AgendamentoDto(agendamento);
    }

    @Transactional
    public AgendamentoDto saveAgendamento(AgendamentoDto agendamentoDto) {
        Nutriz nutriz = nutrizRepository.findById(agendamentoDto.getNutrizId())
                .orElseThrow(() -> new ResourceNotFoundException("Nutriz não encontrada. ID: " + agendamentoDto.getNutrizId()));

        PontoColeta pontoColeta = pontoColetaRepository.findById(agendamentoDto.getPontoColetaId())
                .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado. ID: " + agendamentoDto.getPontoColetaId()));
        //validação para o tipo de coleta

        if (agendamentoDto.getTipoColeta() == null) {
            throw new DatabaseException("Tipo de coleta inválido. Use apenas DOMICILIAR ou PONTO_COLETA.");
        }

        Agendamento agendamento = new Agendamento();
        mapperDtoToAgendamento(agendamentoDto, agendamento, nutriz, pontoColeta);
        agendamento.setStatus(AgendamentoStatus.AGENDADO);

        agendamento = agendamentoRepository.save(agendamento);
        return new AgendamentoDto(agendamento);
    }
    private void mapperDtoToAgendamento(AgendamentoDto agendamentoDto, Agendamento agendamento, Nutriz nutriz, PontoColeta pontoColeta) {
        agendamento.setNutriz(nutriz);
        agendamento.setPontoColeta(pontoColeta);
        agendamento.setDataHora(agendamentoDto.getDataHora());
        agendamento.setTipoColeta(agendamentoDto.getTipoColeta());
    }

    @Transactional
    public AgendamentoDto updateAgendamento(Long id, AgendamentoDto agendamentoDto){
        try{
            Agendamento agendamento = agendamentoRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("Agendamento não encontrado. ID: "+ id));
            Nutriz nutriz = nutrizRepository.findById(agendamentoDto.getNutrizId())
                    .orElseThrow(()-> new ResourceNotFoundException("Nutriz não encontrada. ID: "+ agendamentoDto.getNutrizId()));
            PontoColeta pontoColeta = pontoColetaRepository.findById(agendamentoDto.getPontoColetaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ponto de coleta não encontrado. ID: " + agendamentoDto.getPontoColetaId()));

            if(agendamentoDto.getTipoColeta() == null){
                throw new DatabaseException("Tipo de coleta inválido. Use apenas DOMICILIAR ou PONTO_COLETA");
            }
            mapperDtoToAgendamento(agendamentoDto, agendamento, nutriz, pontoColeta);
            return new AgendamentoDto(agendamento);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Erro ao atualizar agendamento");
        }
    }

    @Transactional
    public void cancelAgendamento(Long id){
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agendamento não encontrado. ID: "+ id));
        agendamento.setStatus(AgendamentoStatus.CANCELADO);
        agendamentoRepository.save(agendamento);
    }

    @Transactional
    public void deleteAgendamento(Long id){
        if(!agendamentoRepository.existsById(id)){
            throw new ResourceNotFoundException("Agendamento não encontrado. ID: "+ id);
        }
        agendamentoRepository.deleteById(id);
    }



}
