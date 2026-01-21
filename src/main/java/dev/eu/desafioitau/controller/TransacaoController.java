package dev.eu.desafioitau.controller;

import dev.eu.desafioitau.docs.TransacaoControllerDocs;
import dev.eu.desafioitau.dto.request.TransacaoRequest;
import dev.eu.desafioitau.exceptions.BadRequestException;
import dev.eu.desafioitau.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transacao")
@RequiredArgsConstructor
public class TransacaoController implements TransacaoControllerDocs {

    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity adicionar(@RequestBody TransacaoRequest request) {
        try {
            transacaoService.adicionarTransacao(request);
            log.info("Transação adicionada com sucesso {}", request.valor());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (IllegalArgumentException e) {
            log.error("Erro em uma ou mais validações: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).build();
        } catch (BadRequestException e) {
            log.error("Formatação da request incorreta: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping()
    public ResponseEntity deletar() {
        transacaoService.deletarTransacao();
        log.info("Todas as transações foram deletadas com sucesso");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
