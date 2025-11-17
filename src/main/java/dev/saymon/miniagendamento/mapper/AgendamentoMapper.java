package dev.saymon.miniagendamento.mapper;

import dev.saymon.miniagendamento.dto.AgendamentoCreateRequest;
import dev.saymon.miniagendamento.dto.AgendamentoResponse;
import dev.saymon.miniagendamento.dto.AgendamentoUpdateRequest;
import dev.saymon.miniagendamento.model.Agendamento;
import dev.saymon.miniagendamento.model.enums.StatusAgendamento;

import java.time.LocalDateTime;

public class AgendamentoMapper {

    public static Agendamento toEntity (AgendamentoCreateRequest request){
        return Agendamento.builder()
                .titulo(request.titulo())
                .descricao(request.descricao())
                .dataInicio(request.dataInicio())
                .dataFim(request.dataFim())
                .usuario(request.usuario())
                .status(StatusAgendamento.AGENDADO)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();
    }

    public static void merge(Agendamento entity, AgendamentoUpdateRequest request){
        if(request.titulo() != null) {
            entity.setTitulo(request.titulo());
        }

        if(request.descricao() != null) {
            entity.setTitulo(request.descricao());
        }

        if(request.dataInicio() != null) {
            entity.setDataInicio(request.dataInicio());
        }

        if(request.dataFim() != null) {
            entity.setDataFim(request.dataFim());
        }

    }

    public static AgendamentoResponse toResponse (Agendamento a){
        return new AgendamentoResponse(
                a.getId(),
                a.getTitulo(),
                a.getDescricao(),
                a.getDataInicio(),
                a.getDataFim(),
                a.getStatus(),
                a.getUsuario(),
                a.getCriadoEm(),
                a.getAtualizadoEm()
        );
    }

}
