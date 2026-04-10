package org.example.controller;

import org.example.model.entities.PainelTatico;
import org.example.service.PainelTaticoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class PainelTaticoController {

    private final PainelTaticoService service;

    public PainelTaticoController(PainelTaticoService service) {
        this.service = service;
    }

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTatico>> listarTopMissoesRecentes() {
        List<PainelTatico> missoes = service.obterTopMissoesRecentes();
        return ResponseEntity.ok(missoes);
    }
}