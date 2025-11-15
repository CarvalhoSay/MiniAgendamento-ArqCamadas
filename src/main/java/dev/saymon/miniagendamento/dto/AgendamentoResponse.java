package dev.saymon.miniagendamento.dto;

import dev.saymon.miniagendamento.model.enums.StatusAgendamento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AgendamentoResponse(

        Long id,
        String titulo,
        String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim,
        StatusAgendamento status,
        String usuario,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm) {
}
