package dev.eu.desafioitau.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record EstatisticaResponse(
        Long count,
        BigDecimal sum,
        BigDecimal avg,
        BigDecimal max,
        BigDecimal min
) {
}
