package dev.saymon.miniagendamento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record AgendamentoUpdateRequest(
        @NotBlank @Size(max = 120) String titulo,
        @Size(max = 4000) String descricao,
        LocalDateTime dataInicio,
        LocalDateTime dataFim) {

}
