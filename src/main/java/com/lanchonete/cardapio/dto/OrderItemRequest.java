package com.lanchonete.cardapio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequest(
        @NotNull Long productId,
        @NotNull @Min(1) Integer quantidade,
        String observacao
) {}
