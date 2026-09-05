package br.com.fiap.BloomCare.controller;

import br.com.fiap.BloomCare.dto.NutrizDto;
import br.com.fiap.BloomCare.service.NutrizService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/nutrizes")
public class NutrizController {

    @Autowired
    private NutrizService nutrizService;

    @GetMapping
    public ResponseEntity<List<NutrizDto>> getAll(){
        List<NutrizDto> nutrizDtos = nutrizService.findAllNutrizes();
        return ResponseEntity.ok(nutrizDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutrizDto> getById(@PathVariable Long id){
        NutrizDto nutrizDto = nutrizService.findNutrizById(id);
        return ResponseEntity.ok(nutrizDto);
    }

    @PostMapping
    private ResponseEntity<NutrizDto> createNutriz(@RequestBody
                                                   @Valid NutrizDto nutrizDto){
        nutrizDto = nutrizService.saveNutriz(nutrizDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(nutrizDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(nutrizDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutrizDto> updateNutriz(@PathVariable Long id,
                                                  @Valid
                                                  @RequestBody NutrizDto nutrizDto){
        nutrizDto = nutrizService.updateNutriz(id, nutrizDto);
        return ResponseEntity.ok(nutrizDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNutriz(@PathVariable Long id){
        nutrizService.deleteNutrizById(id);
        return ResponseEntity.noContent().build();
    }
}
