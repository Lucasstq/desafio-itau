package dev.eu.desafioitau.service;

import dev.eu.desafioitau.dto.response.EstatisticaResponse;
import dev.eu.desafioitau.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

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
        final var transacaoNoUltimoMinuto = transacaoRepository.todasTransacoes()
                .stream()
                .filter(t -> !t.getDataHora().isBefore(inicio) &&
                        !t.getDataHora().isAfter(agora))
                .mapToDouble(t -> t.getValor().doubleValue())
                .summaryStatistics();

        if(transacaoNoUltimoMinuto.getCount() > 0){
            return EstatisticaResponse.builder()
                    .count(transacaoNoUltimoMinuto.getCount())
                    .sum(transacaoNoUltimoMinuto.getSum())
                    .max(transacaoNoUltimoMinuto.getMax())
                    .min(transacaoNoUltimoMinuto.getMin())
                    .avg(transacaoNoUltimoMinuto.getAverage())
                    .build();
        }
        return EstatisticaResponse.builder()
                .count(0L)
                .sum(0.0)
                .avg(0.0)
                .max(0.0)
                .min(0.0)
                .build();

    }


}
