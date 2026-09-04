package br.com.fiap.BloomCare.service;

import br.com.fiap.BloomCare.dto.DoacaoDto;
import br.com.fiap.BloomCare.entities.Agendamento;
import br.com.fiap.BloomCare.entities.AgendamentoStatus;
import br.com.fiap.BloomCare.entities.Doacao;
import br.com.fiap.BloomCare.entities.Triagem;
import br.com.fiap.BloomCare.exceptions.DatabaseException;
import br.com.fiap.BloomCare.exceptions.ResourceNotFoundException;
import br.com.fiap.BloomCare.repository.AgendamentoRepository;
import br.com.fiap.BloomCare.repository.DoacaoRepository;
import br.com.fiap.BloomCare.repository.TriagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private TriagemRepository triagemRepository;

    @Transactional(readOnly = true)
    public List<DoacaoDto> findAllDoacoes(){
        List<Doacao> doacaos = doacaoRepository.findAll();
        return doacaos.stream().map(DoacaoDto::new).toList();
    }

    @Transactional(readOnly = true)
    public DoacaoDto findDoacaoById(Long id){
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Doação não encontrada. ID: "+ id));
        return new DoacaoDto(doacao);
    }

    @Transactional
    public DoacaoDto saveDoacao(DoacaoDto dto){
        Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                .orElseThrow(()-> new ResourceNotFoundException("Agendamento não encontrado. ID: "+ dto.getAgendamentoId()));

        //validação: agendamento cancelado
        if (agendamento.getStatus() == AgendamentoStatus.CANCELADO){
            throw new DatabaseException("Não é possível registar doação para agendamento cancelado.");
        }

        //validação: nutriz não apta
        Triagem triagem = triagemRepository.findByNutrizId(agendamento.getNutriz().getId())
                .orElseThrow(()-> new ResourceNotFoundException("Triagem não encontrada para nutriz ID: "+ agendamento.getNutriz().getId()));
        if (!triagem.getAptaDoacao()) {
            throw new DatabaseException("Nutriz não está apta para doação.");
        }


        Doacao doacao = dto.toEntity(agendamento);
        doacao= doacaoRepository.save(doacao);
        return new DoacaoDto(doacao);
    }

    @Transactional
    public DoacaoDto updateDoacao(Long id, DoacaoDto dto){
        Doacao doacao = doacaoRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Doação não encontrada. ID: "+ id));
        Agendamento agendamento = agendamentoRepository.findById(dto.getAgendamentoId())
                .orElseThrow(()-> new ResourceNotFoundException("Agendamento não encontrado. ID: "+ dto.getAgendamentoId()));

        doacao.setDataDoacao(dto.getDataDoacao());
        doacao.setQuantidadeMl(dto.getQuantidadeMl());
        doacao.setAgendamento(agendamento);

        doacao= doacaoRepository.save(doacao);
        return new DoacaoDto(doacao);
    }

    @Transactional
    public void deleteDoacaoById(Long id){
        if (!doacaoRepository.existsById(id)){
            throw new ResourceNotFoundException("Doação não encontrada. ID: "+ id);
        }
        doacaoRepository.deleteById(id);
    }


}
