package com.lanchonete.cardapio.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * O pagamento em si e processado automaticamente dentro de OrderService
 * ao finalizar o pedido (POST /api/orders). Este controller expõe
 * apenas os metodos de pagamento aceitos, para o front montar a tela de checkout.
 */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @GetMapping("/methods")
    public ResponseEntity<List<Map<String, String>>> metodosDisponiveis() {
        return ResponseEntity.ok(List.of(
                Map.of("codigo", "PIX", "descricao", "Pix"),
                Map.of("codigo", "CARTAO_CREDITO", "descricao", "Cartao de credito"),
                Map.of("codigo", "CARTAO_DEBITO", "descricao", "Cartao de debito"),
                Map.of("codigo", "DINHEIRO", "descricao", "Dinheiro na entrega")
        ));
    }
}
