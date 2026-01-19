package dev.eu.desafioitau.service;

import dev.eu.desafioitau.dto.response.EstatisticaResponse;
import dev.eu.desafioitau.entities.Transacao;
import dev.eu.desafioitau.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class EstatisticaService {

    private final TransacaoRepository transacaoRepository;

    public EstatisticaService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    /*
     metodo para calcular estatisticas dos ultimos 60 segundos
     */
    public EstatisticaResponse calcularEstatisticas() {

        //intervalo: últimos 60 segundos (de agora - 60s até agora)
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime inicio = agora.minusSeconds(60);

        //incluir transações cujo timestamp esteja entre [inicio, limite]
        List<Transacao> transacaoNoUltimoMinuto = transacaoRepository.todasTransacoes()
                .stream()
                .filter(t -> !t.getDataHora().isBefore(inicio) &&
                        !t.getDataHora().isAfter(agora))
                .toList();

        if (!transacaoNoUltimoMinuto.isEmpty()) {
            long count = transacaoNoUltimoMinuto.size();

            BigDecimal soma = transacaoNoUltimoMinuto.stream()
                    .map(Transacao::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal maxima = transacaoNoUltimoMinuto.stream()
                    .map(Transacao::getValor)
                    .max(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);

            BigDecimal minima = transacaoNoUltimoMinuto.stream()
                    .map(Transacao::getValor)
                    .min(Comparator.naturalOrder())
                    .orElse(BigDecimal.ZERO);

            BigDecimal media = soma.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

            return EstatisticaResponse.builder()
                    .count(count)
                    .sum(soma)
                    .avg(media)
                    .max(maxima)
                    .min(minima)
                    .build();
        }
        return EstatisticaResponse.builder()
                .count(0L)
                .sum(BigDecimal.ZERO)
                .avg(BigDecimal.ZERO)
                .max(BigDecimal.ZERO)
                .min(BigDecimal.ZERO)
                .build();

    }


}
