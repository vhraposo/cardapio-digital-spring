package com.lanchonete.cardapio.controller;

import com.lanchonete.cardapio.dto.ProductRequest;
import com.lanchonete.cardapio.entity.Product;
import com.lanchonete.cardapio.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * GET publico (visitante + user + admin veem o cardapio).
 * POST/PUT/DELETE restritos a ADMIN - ver SecurityConfig.
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> listar() {
        return ResponseEntity.ok(productService.listarDisponiveis());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Product> criar(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.criar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> atualizar(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        productService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
