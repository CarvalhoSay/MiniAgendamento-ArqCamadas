package dev.saymon.miniagendamento.controller;

import dev.saymon.miniagendamento.dto.AgendamentoCreateRequest;
import dev.saymon.miniagendamento.dto.AgendamentoResponse;
import dev.saymon.miniagendamento.dto.AgendamentoUpdateRequest;
import dev.saymon.miniagendamento.model.Agendamento;
import dev.saymon.miniagendamento.service.AgendamentoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    private final AgendamentoService service;


    public AgendamentoController(AgendamentoService service) {
        this.service = service;
    }

    @PostMapping
    public AgendamentoResponse criar(@Valid @RequestBody AgendamentoCreateRequest request) {
        return service.criarAgendamento(request);
    }

    @PutMapping("/{id}/atualizar")
    public AgendamentoResponse atualizar(@PathVariable Long id, @Valid @RequestBody AgendamentoUpdateRequest request){
        return service.atualizar(id, request);
    }

    @PutMapping("/{id}/cancelar")
    public AgendamentoResponse cancelar(@PathVariable Long id){
        return service.cancelar(id);
    }

    @PutMapping("/{id}/concluir")
    public AgendamentoResponse concluir(@PathVariable Long id){
        return service.concluir(id);
    }

    @GetMapping("/{id}")
    public AgendamentoResponse buscarPorId(@PathVariable Long id){
        return service.buscarPorId(id);
    }
}
