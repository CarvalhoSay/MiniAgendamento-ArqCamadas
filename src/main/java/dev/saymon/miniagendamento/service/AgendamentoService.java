package dev.saymon.miniagendamento.service;

import dev.saymon.miniagendamento.dto.AgendamentoCreateRequest;
import dev.saymon.miniagendamento.dto.AgendamentoResponse;
import dev.saymon.miniagendamento.dto.AgendamentoUpdateRequest;
import dev.saymon.miniagendamento.mapper.AgendamentoMapper;
import dev.saymon.miniagendamento.model.Agendamento;
import dev.saymon.miniagendamento.model.enums.StatusAgendamento;
import dev.saymon.miniagendamento.repositories.AgendamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AgendamentoService {

    private final AgendamentoRepository repo;

    public AgendamentoService(AgendamentoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public AgendamentoResponse criarAgendamento(@Valid AgendamentoCreateRequest request){

        validarIntervalor(request.dataInicio(), request.dataFim());
        checarConflito(request.usuario(), request.dataInicio(), request.dataFim(), null);

        Agendamento entity = AgendamentoMapper.toEntity(request);
        repo.save(entity);
        return AgendamentoMapper.toResponse(entity);
    }

    @Transactional
    public AgendamentoResponse atualizar(Long id,@Valid AgendamentoUpdateRequest request){
        Agendamento entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));
        AgendamentoMapper.merge(entity,request);

        validarIntervalor(request.dataInicio(), request.dataFim());
        checarConflito(entity.getUsuario(), request.dataInicio(), request.dataFim(), entity.getId());


        repo.save(entity);
        return AgendamentoMapper.toResponse(entity);

    }

    @Transactional
    public AgendamentoResponse cancelar(Long id){
        Agendamento entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        entity.setStatus(StatusAgendamento.CANCELADO);
        repo.save(entity);

        return AgendamentoMapper.toResponse(entity);

    }

    @Transactional
    public AgendamentoResponse concluir(Long id){
        Agendamento entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        entity.setStatus(StatusAgendamento.CONCLUIDO);
        repo.save(entity);

        return AgendamentoMapper.toResponse(entity);
    }


    public AgendamentoResponse buscarPorId(Long id){
        Agendamento entity = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Agendamento nao encontrado"));

        return AgendamentoMapper.toResponse(entity);
    }



    private void validarIntervalor(LocalDateTime inicio, LocalDateTime fim){
        if (inicio == null || fim == null || !inicio.isBefore(fim) ){
            throw new IllegalArgumentException("Intervalo Invalido: dataInicio deve ser anterior a data fim");
        }
    }

    private void checarConflito(String usuario, LocalDateTime inicio, LocalDateTime fim, Long id){
        if (repo.existsConflito(usuario, inicio, fim, id)){
            throw new IllegalArgumentException("Conflito na agenda: Já existe um agendamento neste periodo");
        }
    }
}

