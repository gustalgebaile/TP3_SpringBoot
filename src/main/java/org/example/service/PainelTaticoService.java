package org.example.service;

import org.example.model.MvPainelTaticoMissao;
import org.example.repository.MvPainelTaticoMissaoRepository;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PainelTaticoService {

    private final MvPainelTaticoMissaoRepository repository;

    public PainelTaticoService(MvPainelTaticoMissaoRepository repository) {
        this.repository = repository;
    }

    public List<MvPainelTaticoMissao> obterTopMissoesRecentes() {
        // Calcula a data limite de 15 dias atrás
        OffsetDateTime dataLimite = OffsetDateTime.now().minusDays(15);

        return repository.findTop10ByUltimaAtualizacaoAfterOrderByIndiceProntidaoDesc(dataLimite);
    }
}