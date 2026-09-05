package br.com.fiap.BloomCare.controller;

import br.com.fiap.BloomCare.dto.TriagemDto;
import br.com.fiap.BloomCare.repository.TriagemRepository;
import br.com.fiap.BloomCare.service.TriagemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/v1/triagens")
public class TriagemController {

    @Autowired
    private TriagemService triagemService;

    @GetMapping
    public ResponseEntity<List<TriagemDto>> getAll() {
        List<TriagemDto> triagemDtos = triagemService.findAllTriagens();
        return ResponseEntity.ok(triagemDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriagemDto> getById(@PathVariable Long id) {
        TriagemDto triagemDto = triagemService.findTriagemById(id);
        return ResponseEntity.ok(triagemDto);
    }

    @PostMapping
    public ResponseEntity<TriagemDto> createTriagem(@RequestBody
                                                    @Valid TriagemDto triagemDto){

        triagemDto = triagemService.saveTriagem(triagemDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(triagemDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(triagemDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TriagemDto> updateTriagem(@PathVariable Long id,
                                                    @Valid @RequestBody TriagemDto triagemDto){
        triagemDto = triagemService.updateTriagem(id, triagemDto);
        return ResponseEntity.ok(triagemDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTriagem(@PathVariable Long id){
        triagemService.deteleTriagem(id);
        return ResponseEntity.noContent().build();
    }
}









