package dev.eu.desafioitau.controller;

import dev.eu.desafioitau.docs.EstatisticaControllerDocs;
import dev.eu.desafioitau.dto.response.EstatisticaResponse;
import dev.eu.desafioitau.service.EstatisticaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticaController implements EstatisticaControllerDocs {


    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<EstatisticaResponse> estatisticas() {
        log.info("Requisição para obter estatísticas das transações dos últimos 60 segundos");
        return ResponseEntity.status(HttpStatus.OK)
                .body(estatisticaService.calcularEstatisticas());
    }
}
