package org.example.controller;

import org.example.dto.ProdutoDTO;
import org.example.service.ProdutoService;
import org.example.dto.AgregacaoDTO;
import org.example.dto.PrecoMedioDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("/busca/nome")
    public ResponseEntity<List<ProdutoDTO>> buscarPorNome(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(service.buscarPorNome(termo));
    }

    @GetMapping("/busca/descricao")
    public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(service.buscarPorDescricao(termo));
    }

    @GetMapping("/busca/frase")
    public ResponseEntity<List<ProdutoDTO>> buscarPorFraseExata(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(service.buscarPorFraseExata(termo));
    }

    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<ProdutoDTO>> buscarFuzzy(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(service.buscarFuzzy(termo));
    }

    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<ProdutoDTO>> buscarEmMultiplosCampos(@RequestParam String termo) throws IOException {
        return ResponseEntity.ok(service.buscarEmMultiplosCampos(termo));
    }

    @GetMapping("/busca/com-filtro")
    public ResponseEntity<List<ProdutoDTO>> buscarComFiltroCategoria(
            @RequestParam String termo, @RequestParam String categoria) throws IOException {
        return ResponseEntity.ok(service.buscarComFiltroCategoria(termo, categoria));
    }

    @GetMapping("/busca/faixa-preco")
    public ResponseEntity<List<ProdutoDTO>> buscarPorFaixaDePreco(
            @RequestParam Double min, @RequestParam Double max) throws IOException {
        return ResponseEntity.ok(service.buscarPorFaixaDePreco(min, max));
    }

    @GetMapping("/busca/avancada")
    public ResponseEntity<List<ProdutoDTO>> buscarAvancada(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Double min,
            @RequestParam Double max) throws IOException {
        return ResponseEntity.ok(service.buscarBuscaAvancada(categoria, raridade, min, max));
    }

    @GetMapping("/agregacoes/por-categoria")
    public ResponseEntity<List<AgregacaoDTO>> agregarPorCategoria() throws IOException {
        return ResponseEntity.ok(service.agregarPorCategoria());
    }

    @GetMapping("/agregacoes/por-raridade")
    public ResponseEntity<List<AgregacaoDTO>> agregarPorRaridade() throws IOException {
        return ResponseEntity.ok(service.agregarPorRaridade());
    }

    @GetMapping("/agregacoes/preco-medio")
    public ResponseEntity<PrecoMedioDTO> precoMedio() throws IOException {
        return ResponseEntity.ok(service.obterPrecoMedio());
    }

    @GetMapping("/agregacoes/faixas-preco")
    public ResponseEntity<List<AgregacaoDTO>> faixasDePreco() throws IOException {
        return ResponseEntity.ok(service.obterFaixasDePreco());
    }
}

