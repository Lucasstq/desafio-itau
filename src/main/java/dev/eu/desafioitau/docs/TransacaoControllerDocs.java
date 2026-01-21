package dev.eu.desafioitau.docs;

import dev.eu.desafioitau.dto.request.TransacaoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Transação Controller", description = "Endpoints para gerenciar transações")
public interface TransacaoControllerDocs {

    @Operation(
            summary = "Adicionar uma nova transação",
            description = "Adiciona uma nova transação com base nos dados fornecidos no corpo da requisição."
    )
    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Requisição inválida")
    @ApiResponse(responseCode = "422", description = "Erro de validação nos dados da transação")
    @PostMapping
    ResponseEntity adicionar(@RequestBody TransacaoRequest request);


    @Operation(
            summary = "Deletar todas as transações",
            description = "Deleta todas as transações armazenadas no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Todas as transações foram deletadas com sucesso")
    @DeleteMapping()
    ResponseEntity deletar();
}
