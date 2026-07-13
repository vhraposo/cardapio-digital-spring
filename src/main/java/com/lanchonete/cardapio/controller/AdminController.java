package com.lanchonete.cardapio.controller;

import com.lanchonete.cardapio.dto.OrderStatusUpdateRequest;
import com.lanchonete.cardapio.entity.Order;
import com.lanchonete.cardapio.entity.Product;
import com.lanchonete.cardapio.service.OrderService;
import com.lanchonete.cardapio.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rotas exclusivas de ADMIN - ver SecurityConfig (hasRole("ADMIN")).
 * Aqui o admin ve TODOS os pedidos de TODOS os usuarios e pode atualizar o status.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> listarTodosPedidos() {
        return ResponseEntity.ok(orderService.listarTodosPedidos());
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> buscarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.buscarPorId(id));
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<Order> atualizarStatus(@PathVariable Long id, @Valid @RequestBody OrderStatusUpdateRequest request) {
        return ResponseEntity.ok(orderService.atualizarStatus(id, request.status()));
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> listarTodosProdutos() {
        return ResponseEntity.ok(productService.listarTodos());
    }
}
