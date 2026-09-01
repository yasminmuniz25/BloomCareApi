package br.com.fiap.BloomCare.service;

import br.com.fiap.BloomCare.dto.TriagemDto;
import br.com.fiap.BloomCare.entities.Nutriz;
import br.com.fiap.BloomCare.entities.Triagem;
import br.com.fiap.BloomCare.exceptions.DatabaseException;
import br.com.fiap.BloomCare.exceptions.ResourceNotFoundException;
import br.com.fiap.BloomCare.repository.NutrizRepository;
import br.com.fiap.BloomCare.repository.TriagemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TriagemService {

    @Autowired
    private TriagemRepository triagemRepository;

    @Autowired
    private NutrizRepository nutrizRepository;

    @Transactional(readOnly = true)
    public List<TriagemDto> findAllTriagens() {
        List<Triagem> triagens = triagemRepository.findAll();
        return triagens.stream().map(TriagemDto::new).toList();
    }

    @Transactional(readOnly = true)
    public TriagemDto findTriagemById(Long id) {
        Triagem triagem = triagemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Triagem não encontrada. ID: " + id));
        return new TriagemDto(triagem);
    }


    @Transactional
    public TriagemDto saveTriagem(TriagemDto triagemDto) {
        Nutriz nutriz = nutrizRepository.findById(triagemDto.getNutrizId())
                .orElseThrow(() -> new ResourceNotFoundException("Nutriz não encontrada. Id:" + triagemDto.getNutrizId()));

        Triagem triagem = new Triagem();
        mapperDtoToTriagem(triagemDto, triagem, nutriz);

        triagem = triagemRepository.save(triagem);
        return new TriagemDto(triagem);
    }

    @Transactional
    public TriagemDto updateTriagem(Long id, TriagemDto triagemDto) {
        try {
            Triagem triagem = triagemRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Triagem não encontrada. ID: " + id));
            Nutriz nutriz = nutrizRepository.findById(triagemDto.getNutrizId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nutriz não encontrada. ID: " + triagemDto.getNutrizId()));
            mapperDtoToTriagem(triagemDto, triagem, nutriz);
            triagem = triagemRepository.save(triagem);
            return new TriagemDto(triagem);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Erro ao atualizar triagem");
        }catch (Exception e){
            throw new DatabaseException("Erro inesperado ao atualizar triagem: "+e.getMessage());
        }
    }

    @Transactional
    public void deteleTriagem(Long id){
        if (!triagemRepository.existsById(id)){
            throw new ResourceNotFoundException("Triagem não encontrada. ID: "+ id);
        }
        triagemRepository.deleteById(id);
    }

    private void mapperDtoToTriagem(TriagemDto triagemDto, Triagem triagem, Nutriz nutriz) {
        triagem.setNutriz(nutriz);
        triagem.setDataTriagem(triagemDto.getDataTriagem());
        triagem.setAptaDoacao(triagemDto.getAptaDoacao());
    }

}
