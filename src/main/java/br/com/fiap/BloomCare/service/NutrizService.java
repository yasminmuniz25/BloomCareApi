package br.com.fiap.BloomCare.service;

import br.com.fiap.BloomCare.dto.NutrizDto;
import br.com.fiap.BloomCare.entities.Nutriz;
import br.com.fiap.BloomCare.exceptions.DatabaseException;
import br.com.fiap.BloomCare.exceptions.ResourceNotFoundException;
import br.com.fiap.BloomCare.repository.NutrizRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class NutrizService {

    @Autowired
    private NutrizRepository nutrizRepository;

    @Transactional(readOnly = true)
    public List<NutrizDto> findAllNutrizes(){
        List<Nutriz> nutrizes = nutrizRepository.findAll();
        return nutrizes.stream().map(NutrizDto::new).toList();
    }

    @Transactional(readOnly = true)
    public NutrizDto findNutrizById(Long id){
        Nutriz nutriz = nutrizRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Nutriz não encontrada. Id: "+ id)
        );
        return new NutrizDto(nutriz);
    }

    //criando nutriz
    @Transactional
    public NutrizDto saveNutriz(NutrizDto nutrizDto){
        if (nutrizRepository.existsByEmail(nutrizDto.getEmail()) && nutrizRepository.existsByCpf(nutrizDto.getCpf())) {
            throw new DatabaseException("E-mail e CPF já existentes");
        }
        if (nutrizRepository.existsByEmail(nutrizDto.getEmail())){
            throw new DatabaseException("E-mail já existente");
        }
        if(nutrizRepository.existsByCpf(nutrizDto.getCpf())){
            throw new DatabaseException("CPF já existente");
        }
        Nutriz nutriz = new Nutriz();
        mapperDtoToNutriz(nutrizDto, nutriz);
        nutriz = nutrizRepository.save(nutriz);
        return new NutrizDto(nutriz);
    }

    private void mapperDtoToNutriz(NutrizDto nutrizDto, Nutriz nutriz) {
        nutriz.setNome(nutrizDto.getNome());
        nutriz.setEmail(nutrizDto.getEmail());
        nutriz.setSenha(nutrizDto.getSenha());
        nutriz.setTelefone(nutrizDto.getTelefone());
        nutriz.setDtNascimento(nutrizDto.getDtNascimento());
        nutriz.setCpf(nutrizDto.getCpf());
        nutriz.setDtCadastro(LocalDate.now());
    }

    @Transactional
    public NutrizDto updateNutriz(Long id, NutrizDto nutrizDto){
        try{
            Nutriz nutriz = nutrizRepository.getReferenceById(id);
            mapperDtoToNutriz(nutrizDto, nutriz);
            nutriz = nutrizRepository.save(nutriz);
            return new NutrizDto(nutriz);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }
    }

    @Transactional
    public void deleteNutrizById(Long id){
        if(!nutrizRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: "+ id);
        }
        nutrizRepository.deleteById(id);
    }
}
