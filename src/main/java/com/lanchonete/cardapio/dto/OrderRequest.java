package com.lanchonete.cardapio.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record OrderRequest(
        @NotEmpty(message = "O carrinho nao pode estar vazio") @Valid List<OrderItemRequest> itens,
        @NotBlank(message = "Endereco de entrega e obrigatorio") String enderecoEntrega,
        String observacoes,
        @NotBlank String paymentMethod
) {}
