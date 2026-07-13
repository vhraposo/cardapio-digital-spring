package com.lanchonete.cardapio.controller;

import com.lanchonete.cardapio.dto.OrderRequest;
import com.lanchonete.cardapio.entity.Order;
import com.lanchonete.cardapio.entity.User;
import com.lanchonete.cardapio.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Todas as rotas aqui exigem usuario autenticado (USER ou ADMIN) - ver SecurityConfig.
 * Um visitante (sem token) recebe 401/403 e o front deve redirecionar para o login.
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> criar(@AuthenticationPrincipal User user, @Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(orderService.criarPedido(user, request));
    }

    @GetMapping
    public ResponseEntity<List<Order>> meusPedidos(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.listarPedidosDoUsuario(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> buscarPorId(@AuthenticationPrincipal User user, @PathVariable Long id) {
        return ResponseEntity.ok(orderService.buscarPorIdDoUsuario(id, user));
    }
}
