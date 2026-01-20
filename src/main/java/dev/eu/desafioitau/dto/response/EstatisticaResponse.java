package dev.eu.desafioitau.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EstatisticaResponse(
        Long count,
        double sum,
        double avg,
        double max,
        double min
) {
}
