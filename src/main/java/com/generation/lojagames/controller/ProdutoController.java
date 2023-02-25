package com.generation.lojagames.controller;

import com.generation.lojagames.model.Produto;
import com.generation.lojagames.repository.CategoriaRepository;
import com.generation.lojagames.repository.ProdutoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Produto>> listar(){
        return ResponseEntity.ok(produtoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id){
        return produtoRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<Produto>> buscarPorNome(@PathVariable String nome){
        return ResponseEntity.ok(produtoRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @GetMapping("/preco_maior/{preco}")
    public ResponseEntity<List<Produto>> buscarPorPrecoMaior(@PathVariable BigDecimal preco){
        return ResponseEntity.ok(produtoRepository.findByPrecoGreaterThanEqualOrderByPreco(preco));
    }

    @GetMapping("/preco_menor/{preco}")
    public ResponseEntity<List<Produto>> buscarPorPrecoMenor(@PathVariable BigDecimal preco){
        return ResponseEntity.ok(produtoRepository.findByPrecoLessThanEqualOrderByPrecoAsc(preco));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrar(@Valid @RequestBody Produto produto){
        return categoriaRepository.findById(produto.getCategoria().getId())
                .map(resposta -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(produtoRepository.save(produto)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping
    public ResponseEntity<Produto> atualizar(@Valid @RequestBody Produto produto){
        if(produtoRepository.existsById(produto.getId())){
            return categoriaRepository.findById(produto.getCategoria().getId())
                    .map(resposta -> ResponseEntity.ok(produtoRepository.save(produto)))
                    .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        return produtoRepository.findById(id)
                .map(resposta -> {
                    produtoRepository.deleteById(id);
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
