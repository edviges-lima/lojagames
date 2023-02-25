package com.generation.lojagames.controller;

import com.generation.lojagames.model.Categoria;
import com.generation.lojagames.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar(){

        //return ResponseEntity.ok(categoriaRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id){
        return categoriaRepository.findById(id)
                .map(resposta -> ResponseEntity.ok(resposta))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/buscar/{nome}")
    public ResponseEntity<List<Categoria>> buscarPorNome(@PathVariable String nome){
        return ResponseEntity.status(HttpStatus.OK)
                .body(categoriaRepository.findAllByNomeContainingIgnoreCase(nome));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Categoria> cadastrar(@Valid @RequestBody Categoria categoria){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(categoriaRepository.save(categoria));
    }

    @PutMapping
    public ResponseEntity<Categoria> atualizar(@Valid @RequestBody Categoria categoria){
        return categoriaRepository.findById(categoria.getId())
                .map(resposta -> ResponseEntity.ok(categoriaRepository.save(categoria)))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@PathVariable Long id){
        return categoriaRepository.findById(id)
                .map(resposta -> {categoriaRepository.deleteById(id);
                return ResponseEntity.noContent().build();})
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
