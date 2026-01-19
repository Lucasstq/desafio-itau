package dev.eu.desafioitau.dto.request;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoRequest(
        BigDecimal valor,
        OffsetDateTime dataHora
) {
}
