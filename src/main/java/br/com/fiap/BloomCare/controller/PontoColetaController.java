package br.com.fiap.BloomCare.controller;

import br.com.fiap.BloomCare.dto.PontoColetaDto;
import br.com.fiap.BloomCare.service.PontoColetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pontos-coleta")
public class PontoColetaController {

    @Autowired
    private PontoColetaService pontoColetaService;

    @GetMapping
    public ResponseEntity<List<PontoColetaDto>> getAll(){
        List<PontoColetaDto> pontoColetaDtos = pontoColetaService.findAllPontosColeta();
        return ResponseEntity.ok(pontoColetaDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PontoColetaDto> getById(@PathVariable Long id){
        PontoColetaDto pontoColetaDto = pontoColetaService.findPontoColetaById(id);
        return ResponseEntity.ok(pontoColetaDto);
    }

    @PostMapping
    public ResponseEntity<PontoColetaDto> createPontoColeta(@RequestBody
                                                            @Valid PontoColetaDto pontoColetaDto){
        pontoColetaDto = pontoColetaService.savePontoColeta(pontoColetaDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(pontoColetaDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(pontoColetaDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PontoColetaDto> updatePontoColeta(@PathVariable Long id,
                                                    @Valid @RequestBody PontoColetaDto pontoColetaDto) {
        return ResponseEntity.ok(pontoColetaService.updatePontoColeta(id, pontoColetaDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePontoColeta(@PathVariable Long id){
        pontoColetaService.deletePontoColeta(id);
        return ResponseEntity.noContent().build();
    }
}
