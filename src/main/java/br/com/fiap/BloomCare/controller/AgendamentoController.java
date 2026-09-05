package br.com.fiap.BloomCare.controller;

import br.com.fiap.BloomCare.dto.AgendamentoDto;
import br.com.fiap.BloomCare.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity<List<AgendamentoDto>> getAll(){
        List<AgendamentoDto> agendamentoDtos = agendamentoService.findAllAgendamentos();
        return ResponseEntity.ok(agendamentoDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgendamentoDto> getById(@PathVariable Long id){
        AgendamentoDto agendamentoDto = agendamentoService.findAgendamentoById(id);
        return ResponseEntity.ok(agendamentoDto);
    }

    @PostMapping
    public ResponseEntity<AgendamentoDto> createAgendamento(@RequestBody
                                                            @Valid AgendamentoDto agendamentoDto){
        agendamentoDto = agendamentoService.saveAgendamento(agendamentoDto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(agendamentoDto.getId())
                .toUri();
        return ResponseEntity.created(uri).body(agendamentoDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AgendamentoDto> updateAgendamento(@PathVariable Long id,
                                                            @Valid @RequestBody AgendamentoDto agendamentoDto){
        agendamentoDto = agendamentoService.updateAgendamento(id, agendamentoDto);
        return ResponseEntity.ok(agendamentoDto);
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<AgendamentoDto> cancelAgendamento(@PathVariable Long id){
        agendamentoService.cancelAgendamento(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgendamento(@PathVariable Long id){
        agendamentoService.deleteAgendamento(id);
        return ResponseEntity.noContent().build();
    }






}
