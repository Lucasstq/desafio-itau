package dev.eu.desafioitau.controller;

import dev.eu.desafioitau.dto.request.TransacaoRequest;
import dev.eu.desafioitau.entities.Transacao;
import dev.eu.desafioitau.exceptions.BadRequestException;
import dev.eu.desafioitau.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity adicionar(@RequestBody TransacaoRequest request) {
        try {
            transacaoService.adicionarTransacao(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletar(@PathVariable Long id) {
        transacaoService.deletarTransacao(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
