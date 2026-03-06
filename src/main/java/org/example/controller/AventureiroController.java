package org.example.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.example.dto.*;
import org.example.enums.ClasseAventureiro;
import org.example.service.AventureiroService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/aventureiros")
@Validated
public class AventureiroController {

    private final AventureiroService service;

    // Removido o Mapper daqui
    public AventureiroController(AventureiroService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AventureiroDetalheDTO> registrar(@Valid @RequestBody AventureiroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrar(dto));
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> listar(
            @RequestParam(required = false) ClasseAventureiro classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") @Min(value = 0, message = "A página não pode ser negativa") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "O tamanho da página deve ser no mínimo 1")
            @Max(value = 50, message = "O tamanho máximo da página é 50") int size) {

        PageResult<AventureiroResumoDTO> result = service.listarPaginado(classe, ativo, nivelMinimo, page, size);

        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(result.total()));
        headers.add("X-Page", String.valueOf(result.page()));
        headers.add("X-Size", String.valueOf(result.size()));
        headers.add("X-Total-Pages", String.valueOf(result.totalPages()));

        return ResponseEntity.ok().headers(headers).body(result.content());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroDetalheDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarDetalhesPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroDetalheDTO> atualizar(@PathVariable Long id, @Valid @RequestBody AventureiroRequestDTO dto) {
        return ResponseEntity.ok(service.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> expulsar(@PathVariable Long id) {
        service.alterarStatus(id, false);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/recrutar")
    public ResponseEntity<Void> recrutar(@PathVariable Long id) {
        service.alterarStatus(id, true);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<AventureiroDetalheDTO> definirCompanheiro(@PathVariable Long id, @Valid @RequestBody CompanheiroDTO dto) {
        return ResponseEntity.ok(service.definirCompanheiro(id, dto));
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        service.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }
}
