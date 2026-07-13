package com.lanchonete.cardapio.dto;

import com.lanchonete.cardapio.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequest(
        @NotNull OrderStatus status
) {}
