package dev.eu.desafioitau.repository;

import dev.eu.desafioitau.entities.Transacao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class TransacaoRepository {

    private HashMap<Long, Transacao> armazenar = new HashMap<>();
    private Long currentId = 1L;

    public void salvar(Transacao transacao) {
        if (transacao.getId() == null) {
            transacao.setId(currentId++);
        }
        armazenar.put(transacao.getId(), transacao);
    }

    public List<Transacao> todasTransacoes() {
        return new ArrayList<>(armazenar.values());
    }

    public Optional<Transacao> buscarPorId(Long id) {
        return Optional.ofNullable(armazenar.get(id));
    }

    public void deletar() {
        armazenar.clear();
    }


}
