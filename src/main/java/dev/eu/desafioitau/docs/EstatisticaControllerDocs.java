package dev.eu.desafioitau.docs;

import dev.eu.desafioitau.dto.response.EstatisticaResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Estatística Controller", description = "Endpoints para obter estatísticas das transações")
public interface EstatisticaControllerDocs {

    @Operation(
            summary = "Obter estatísticas das transações",
            description = "Retorna as estatísticas das transações dos últimos 60 segundos."
    )
    @ApiResponse(responseCode = "200", description = "Estatísticas obtidas com sucesso")
    @ApiResponse(responseCode = "200", description = "Nenhuma transação encontrada nos últimos 60 segundos, retornando estatísticas zeradas")
    ResponseEntity<EstatisticaResponse> estatisticas();


}
