package br.com.fiap.BloomCare.controller;

import br.com.fiap.BloomCare.dto.DoacaoDto;
import br.com.fiap.BloomCare.service.AgendamentoService;
import br.com.fiap.BloomCare.service.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<DoacaoDto>> getAll(){
        List<DoacaoDto> doacoesDto = doacaoService.findAllDoacoes();
        return ResponseEntity.ok(doacoesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoacaoDto> getDoacoesById(@PathVariable Long id){
        DoacaoDto doacaoDto = doacaoService.findDoacaoById(id);
        return ResponseEntity.ok(doacaoDto);
    }

    @PostMapping
    public ResponseEntity<DoacaoDto> createDoacao(@Valid @RequestBody DoacaoDto dto){
        dto = doacaoService.saveDoacao(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(dto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoacaoDto> updateDoacao(@PathVariable Long id,
                                                  @Valid @RequestBody DoacaoDto dto){
        dto = doacaoService.updateDoacao(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoacao(@PathVariable Long id){
        doacaoService.deleteDoacaoById(id);
        return ResponseEntity.noContent().build();
    }
}
