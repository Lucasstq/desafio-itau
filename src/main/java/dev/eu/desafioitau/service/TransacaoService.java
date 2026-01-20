package dev.eu.desafioitau.service;

import dev.eu.desafioitau.dto.request.TransacaoRequest;
import dev.eu.desafioitau.entities.Transacao;
import dev.eu.desafioitau.repository.TransacaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Service
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;

    public TransacaoService(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    /*
    metodo para adicionar transacoes
    */
    public void adicionarTransacao(TransacaoRequest request) {
        validarTransacoes(request);
        transacaoRepository.salvar(new Transacao(request.valor(), request.dataHora()));
    }

    /*
    metodo para deletar transacoes
     */
    public void deletarTransacao() {
        transacaoRepository.deletar();
    }

    /*
    metodo privado para validar transacoes
     */
    private void validarTransacoes(TransacaoRequest request) {

        if (request.valor() == null || request.dataHora() == null) {
            throw new IllegalArgumentException("Requisição de transação não pode ser nula.");
        }

        if (request.valor().compareTo(new BigDecimal("0")) < 0) {
            throw new IllegalArgumentException("Valor da transação deve ser maior que zero.");
        }

        if (request.dataHora().isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("Data e hora da transação não podem ser futuras.");
        }

    }


}
