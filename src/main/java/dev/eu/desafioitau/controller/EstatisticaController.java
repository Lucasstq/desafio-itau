package dev.eu.desafioitau.controller;

import dev.eu.desafioitau.dto.response.EstatisticaResponse;
import dev.eu.desafioitau.service.EstatisticaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {


    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public ResponseEntity<EstatisticaResponse> estatisticas() {
        return ResponseEntity.ok(estatisticaService.calcularEstatisticas());
    }
}
